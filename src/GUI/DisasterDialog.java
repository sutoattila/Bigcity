package GUI;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.AbstractAction;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import model.Engine;
import res.ResourceLoader;
import rightPanel.buildingStatPanel.BuildingStatPanel;

public class DisasterDialog extends JDialog {
    private Engine e;
    
    /**
     * Constructor
     * @param label - JLabel, this contains the visible text
     * @param e     - Engine, owner of this dialog
     */
    public DisasterDialog(JLabel label, Engine e, int row, int col) {
        this.e = e;
        setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
        setLocationRelativeTo(null);
        
        JButton ok = new JButton(
            new AbstractAction("OK") {
                @Override
                public void actionPerformed(ActionEvent ae)
                {                   
                    DisasterDialog.this.setVisible(false);

                    Timer timer = new Timer();
                    TimerTask task = new TimerTask() {
                    @Override
                    public void run() {
                        e.removeImg(row,col);
                        e.getBigCityJframe().refreshGrid();
                    }
                    };

                    timer.schedule(task, 2000); // Schedule the task to run after 2 seconds
                    e.startTime();
                }
            }
        );
        
        setLayout(new BorderLayout());
        JPanel textPanel = new JPanel();
        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.X_AXIS));
        textPanel.add(Box.createRigidArea(new Dimension(10,0)));
        textPanel.add("North", label);
        textPanel.add(Box.createRigidArea(new Dimension(10,0)));
        add(textPanel);
        add("South",ok);
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
}
