package grid;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import javax.swing.JPanel;
import model.CursorSignal;
import model.Engine;
import model.Zone;

public class Grid extends JPanel {

    //The size of a field in px. (square)
    /*
    The pixels start with the index 0. If the whole map is just one field 
    with a size of 50 pixels, then the range is 0-49.
     */
    private int fieldSize;
    //The number of columns.
    private int width;
    //The number of rows.
    private int height;

    private int mousePositionX;
    private int mousePositionY;

    //The last field's indexes. The cursor is on this field.
    private int oldRowIndex;
    private int oldColumnIndex;

    private Engine engine;

    public Grid(int fieldSize, int width, int height, Engine engine) {
        this.fieldSize = fieldSize;
        this.width = width;
        this.height = height;
        this.engine = engine;

        mousePositionX = -1;
        mousePositionY = -1;

        setPreferredSize(new Dimension(width * fieldSize, height * fieldSize));
        //setBackground(Color.GREEN);
        setBackground(new Color(34, 177, 77));

        addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if (engine.getCursorSignal() == CursorSignal.SELECT) {
                    //TODO: New stat JPanel on the right.
                } else if (engine.getCursorSignal() == CursorSignal.DESTROY) {
                    if (engine.destroyZone(mousePositionY / fieldSize,
                            mousePositionX / fieldSize, fieldSize)) {
                        repaint();
                    }
                } else {
                    //Build zone.
                    if (engine.build(mousePositionY / fieldSize,
                            mousePositionX / fieldSize, fieldSize)) {
                        repaint();
                    }
                }

            }

        });

        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                super.mouseMoved(e);
                mousePositionX = e.getX();
                mousePositionY = e.getY();

                int newColumnIndex = mousePositionX / fieldSize;
                int newRowIndex = mousePositionY / fieldSize;
                //Repaint only if the cursor moves to another field.
                if (newColumnIndex != oldColumnIndex
                        || newRowIndex != oldRowIndex) {
                    //System.out.println("old column: " + oldColumnIndex + ", old row: " + oldRowIndex);
                    oldColumnIndex = newColumnIndex;
                    oldRowIndex = newRowIndex;
                    //System.out.println("new column: " + newColumnIndex + ", new row: " + newRowIndex);
                    //System.out.println("-----------------------");
                    repaint();
                }
            }
        });

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
                //No hover shown.
                mousePositionX = -1;
                mousePositionY = -1;
                repaint();
                oldColumnIndex = -1;
                oldRowIndex = -1;
            }
        });

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                //System.out.println(e.getX());
                //System.out.println(e.getY());
                oldRowIndex = e.getY() / fieldSize;
                oldColumnIndex = e.getX() / fieldSize;

                repaint();
            }
        });

    }

    //Testing the paintComponent's performance. Call counter.
    //private static int paintCount = 0;
    @Override
    protected void paintComponent(Graphics g) {
        //++paintCount;
        //System.out.println("paint count: " + paintCount);

        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;

        for (int row = 0; row < height; row++) {
            for (int column = 0; column < width; column++) {
                if (null != engine.getCell(row, column)) {
                    //TODO: Different images for each zone types. 
                    //Different level and residential(according saturation)
                    //images. Different road images according other sorrounding
                    //roads. Roads alone start with the most simple image 
                    //(e.g., from South to North), not with the one with all
                    //four direction(this should be the last option).
                    g2.drawImage(engine.getCell(row, column).getImg(),
                            engine.getCell(row, column).getTopLeftX(),
                            engine.getCell(row, column).getTopLeftY(),
                            fieldSize * engine.getCell(row, column).getWidth(),
                            fieldSize * engine.getCell(row, column).getHeigth(),
                            null);
                }
            }
        }
        if (mousePositionX != -1 && mousePositionY != -1) {
            int rowStart = mousePositionY / fieldSize;
            int rowEnd = mousePositionY / fieldSize
                    + engine.getCursorSignal().getHeight() - 1;
            int columnStart = mousePositionX / fieldSize;
            int columnEnd = mousePositionX / fieldSize
                    + engine.getCursorSignal().getWidth() - 1;

            if (engine.getCursorSignal() == CursorSignal.DESTROY) {
                //Destroy hover.
                //Transparent orange for hover.
                g2.setColor(new Color(1f, 0.647f, 0f, 0.8f));
                Zone target = engine.getCell(rowStart, columnStart);

                if (null != target) {
                    rowEnd = target.getTopLeftY() / fieldSize
                            + target.getHeigth() - 1;
                    rowStart = target.getTopLeftY() / fieldSize;
                    columnEnd = target.getTopLeftX() / fieldSize
                            + target.getWidth() - 1;
                    columnStart = target.getTopLeftX() / fieldSize;
                    paintArea(rowStart, rowEnd, columnStart, columnEnd, g2);
                } else {
                    paintArea(rowStart, rowEnd, columnStart, columnEnd, g2);
                }
            } else if (engine.getCursorSignal() == CursorSignal.SELECT) {
                //Select hover.
                //TODO: By default it's 1x1. If it's covering a zone, than the hover
                //should cover the whole zone. On click new stat JPanel appear on 
                //the rigth.
                ////Transparent yellow-gold for hover.
                g2.setColor(new Color(0.98f, 0.843f, 0f, 0.8f));
                paintArea(rowStart, rowEnd, columnStart, columnEnd, g2);
            } else {
                //Build hover.
                //Zone type selected and not built yet.
                //Transparent blue for hover.
                g2.setColor(new Color(0f, 0f, 1f, 0.125f));

                //Can't build on the position of the mouse. Change color to red.
                if (false == engine.areaInsideGridAndFree(rowStart, rowEnd,
                        columnStart, columnEnd)) {
                    //Transparent red for hover.
                    g2.setColor(new Color(1f, 0f, 0f, 0.6f));
                }
                //Draw hover rectangle.
                paintArea(rowStart, rowEnd, columnStart, columnEnd, g2);
            }
        }
    }

    public int getFieldSize() {
        return fieldSize;
    }

    //Created to test the draw performance.
    public void fillGrid() {
        engine.setCursorSignal(CursorSignal.ROAD);
        for (int row = 0; row < height; row++) {
            for (int column = 0; column < width; column++) {
                engine.build(row, column, fieldSize);
            }
        }
        repaint();
    }

    private void paintArea(int rowStart, int rowEnd,
            int columnStart, int columnEnd, Graphics2D g2) {
        for (int row = rowStart; row <= rowEnd; row++) {
            for (int column = columnStart; column <= columnEnd; column++) {
                if (row < height && column < width) {
                    g2.fillRect(column * fieldSize,
                            row * fieldSize,
                            fieldSize,
                            fieldSize);
                }
            }
        }
    }
}
