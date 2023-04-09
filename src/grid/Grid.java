package grid;

import bigcity.Industry;
import bigcity.PrivateZone;
import bigcity.Residence;
import bigcity.Zone;
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
import res.Assets;
import view.BigCityJframe;

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

    private BigCityJframe bigCityJFrame;

    public Grid(int fieldSize, int width, int height, Engine engine,
            BigCityJframe bifCityJFrame) {
        this.fieldSize = fieldSize;
        this.width = width;
        this.height = height;
        this.engine = engine;
        this.bigCityJFrame = bifCityJFrame;

        mousePositionX = -1;
        mousePositionY = -1;

        setPreferredSize(new Dimension(width * fieldSize, height * fieldSize));
        setBackground(new Color(34, 177, 77));

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if (engine.getCursorSignal() == CursorSignal.SELECT) {
                    //TODO: stadium, industry, police -> color fields around
                    int row = mousePositionY / fieldSize;
                    int column = mousePositionX / fieldSize;
                    Zone target = engine.getCell(row, column);
                    if (null != target) {
                        bigCityJFrame.changeRightPanelToStatPanel(target);
                    }
                } else if (engine.getCursorSignal() == CursorSignal.DESTROY) {
                    if (engine.destroyZone(mousePositionY / fieldSize,
                            mousePositionX / fieldSize, fieldSize)) {
                        repaint();
                    }
                } else {
                    if (engine.build(mousePositionY / fieldSize,
                            mousePositionX / fieldSize, fieldSize)) {
                        repaint();
                    }
                }
                bigCityJFrame.refreshMoney();
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
                    oldColumnIndex = newColumnIndex;
                    oldRowIndex = newRowIndex;
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
                oldRowIndex = e.getY() / fieldSize;
                oldColumnIndex = e.getX() / fieldSize;
                repaint();
            }
        });

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;

        for (int row = 0; row < height; row++) {
            for (int column = 0; column < width; column++) {
                if (null != engine.getCell(row, column)) {
                    //TODO: Different images for each zone types. 
                    //(level, saturation)
                    changeImageAccordingSaturationAndLevel(
                            engine.getCell(row, column));

                    g2.drawImage(engine.getCell(row, column).getImg(),
                            engine.getCell(row, column).getTopLeftX(),
                            engine.getCell(row, column).getTopLeftY(),
                            fieldSize * engine.getCell(row, column).
                                    getCursorSignal().getWidth(),
                            fieldSize * engine.getCell(row, column).
                                    getCursorSignal().getHeight(),
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

            //Hover.
            if (engine.getCursorSignal() == CursorSignal.DESTROY) {
                //Transparent orange for hover.
                g2.setColor(new Color(1f, 0.647f, 0f, 0.8f));
                paintArea(rowStart, rowEnd, columnStart, columnEnd, g2);
            } else if (engine.getCursorSignal() == CursorSignal.SELECT) {
                ////Transparent yellow-gold for hover.
                g2.setColor(new Color(0.98f, 0.843f, 0f, 0.8f));
                paintArea(rowStart, rowEnd, columnStart, columnEnd, g2);
            } else {
                //Zone type selected to build.
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

    private void changeImageAccordingSaturationAndLevel(Zone zone) {
        if (zone instanceof PrivateZone) {
            int level = zone.getLevel();
            int capacity;
            int size;
            if (zone instanceof Residence) {
                capacity = ((Residence) zone).getCapacity();
                size = ((Residence) zone).getSize();
                if (1 == level) {
                    if (0 == size) {
                        zone.setImg(Assets.copperR);
                    } else if (0 < size && size < capacity / 2) {
                        zone.setImg(Assets.house);
                    } else {//capacity / 2 <= size
                        zone.setImg(Assets.houses);
                    }
                } else if (2 == level) {
                    if (0 == size) {
                        zone.setImg(Assets.silverR);
                    } else if (0 < size && size < capacity / 2) {
                        zone.setImg(Assets.bigHouse);
                    } else {//capacity / 2 <= size
                        zone.setImg(Assets.bigHouses);
                    }
                } else if (3 == level) {
                    if (0 == size) {
                        zone.setImg(Assets.goldR);
                    } else if (0 < size && size < capacity / 2) {
                        zone.setImg(Assets.panel);
                    } else {//capacity / 2 <= size
                        zone.setImg(Assets.panels);
                    }
                }
            } else if (zone instanceof Industry) {
                capacity = ((Industry) zone).getCapacity();
                size = ((Industry) zone).getSize();
                if (1 == level) {
                    if (0 == size) {
                        zone.setImg(Assets.copperI);
                    } else if (0 < size && size < capacity / 2) {
                        zone.setImg(Assets.ranch);
                    } else {//capacity / 2 <= size
                        zone.setImg(Assets.ranches);
                    }
                } else if (2 == level) {
                    if (0 == size) {
                        zone.setImg(Assets.silverI);
                    } else if (0 < size && size < capacity / 2) {
                        zone.setImg(Assets.mine);
                    } else {//capacity / 2 <= size
                        zone.setImg(Assets.mines);
                    }
                } else if (3 == level) {
                    if (0 == size) {
                        zone.setImg(Assets.goldI);
                    } else if (0 < size && size < capacity / 2) {
                        zone.setImg(Assets.factory);
                    } else {//capacity / 2 <= size
                        zone.setImg(Assets.factories);
                    }
                }
            } else {//Service
                capacity = ((PrivateZone) zone).getCapacity();
                size = ((PrivateZone) zone).getSize();
                if (1 == level) {
                    if (0 == size) {
                        zone.setImg(Assets.copperS);
                    } else if (0 < size && size < capacity / 2) {
                        zone.setImg(Assets.shop);
                    } else {//capacity / 2 <= size
                        zone.setImg(Assets.shops);
                    }
                } else if (2 == level) {
                    if (0 == size) {
                        zone.setImg(Assets.silverS);
                    } else if (0 < size && size < capacity / 2) {
                        zone.setImg(Assets.bigShop);
                    } else {//capacity / 2 <= size
                        zone.setImg(Assets.bigShops);
                    }
                } else if (3 == level) {
                    if (0 == size) {
                        zone.setImg(Assets.goldS);
                    } else if (0 < size && size < capacity / 2) {
                        zone.setImg(Assets.mall);
                    } else {//capacity / 2 <= size
                        zone.setImg(Assets.malls);
                    }
                }
            }
        }
    }

    public int getFieldSize() {
        return fieldSize;
    }

    /**
     * Created to test the draw performance. Every field will have an image.
     */
    public void fillGrid() {
        engine.setCursorSignal(CursorSignal.ROAD);
        for (int row = 0; row < height; row++) {
            for (int column = 0; column < width; column++) {
                engine.build(row, column, fieldSize);
            }
        }
        repaint();
    }

    /**
     * Paints fields with transparent colours. If the mouse is above a zone all
     * of its fields get coloured.
     *
     * @param rowStart is the top Y coordinate of the zone
     * @param rowEnd is the bottom Y coordinate of the zone
     * @param columnStart is the left X coordinate of the zone
     * @param columnEnd is the right X coordinate of the zone
     * @param g2 is used for to draw on it
     */
    private void paintArea(int rowStart, int rowEnd,
            int columnStart, int columnEnd, Graphics2D g2) {
        Zone target = engine.getCell(rowStart, columnStart);
        if (null != target) {
            rowEnd = target.getTopLeftY() / fieldSize
                    + target.getCursorSignal().getHeight() - 1;
            rowStart = target.getTopLeftY() / fieldSize;
            columnEnd = target.getTopLeftX() / fieldSize
                    + target.getCursorSignal().getWidth() - 1;
            columnStart = target.getTopLeftX() / fieldSize;
        }
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
