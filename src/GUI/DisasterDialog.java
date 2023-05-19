package GUI;

import java.awt.BorderLayout;
import java.awt.Dimension;
import model.Coords;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.util.ArrayList;
import javax.swing.AbstractAction;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import model.CursorSignal;
import model.Engine;
import rightPanel.BuildPanel;

public class DisasterDialog extends JDialog {

    private Engine e;
    private final ArrayList<Coords> points;

    /**
     * Constructor
     *
     * @param label - JLabel, this contains the visible text
     * @param e - Engine, owner of this dialog
     * @param row
     * @param col
     * @param points
     */
    public DisasterDialog(JLabel label, Engine e, int row, int col, ArrayList<Coords> points) {
        this.points = points;
        this.e = e;
        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        setLocationRelativeTo(null);

        JButton ok = new JButton(
                new AbstractAction("OK") {
            @Override
            public void actionPerformed(ActionEvent ae) {
                DisasterDialog.this.setVisible(false);
                JPanel glassPane = new JPanel();
                glassPane.setOpaque(false);

                // Set the glass pane to intercept mouse events
                glassPane.addMouseListener(new BlockMouseListener());

                // Block mouse events for 2 seconds
                blockMouseEvents(DisasterDialog.this.e, glassPane, 2000, row, col);
                e.startTime();
            }
        }
        );

        setLayout(new BorderLayout());
        JPanel textPanel = new JPanel();
        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.X_AXIS));
        textPanel.add(Box.createRigidArea(new Dimension(10, 0)));
        textPanel.add("North", label);
        textPanel.add(Box.createRigidArea(new Dimension(10, 0)));
        add(textPanel);
        add("South", ok);
        this.pack();
        setModal(true);
    }

    /**
     * Set DisasterDialog visible
     */
    public void setActive() {
        e.stopTime();
        setLocationRelativeTo(null);
        this.setVisible(true);
    }

    private static class BlockMouseListener extends MouseAdapter {
    }

    private void blockMouseEvents(Engine engine, JPanel glassPane, int duration, int row, int col) {
        
        engine.getBigCityJframe().setGlassPane(glassPane);
        try {
            // Pause the execution for 2 seconds (2000 milliseconds)
            Thread.sleep(duration);
        } catch (InterruptedException ex) {
            // Handle the exception if the sleep is interrupted
            System.out.println("InterruptedException");
        }
        if (points != null) {
            for (Coords point : points) {
                engine.removeImg(point.getX(), point.getY());
            }
        }
        engine.removeImg(row, col);
        engine.removeImg(row, col);
        glassPane.removeMouseListener(new BlockMouseListener());
    }
}
