package GUI;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import model.Engine;

public class DisasterDialog extends JDialog {
    private Engine e;
    
    public DisasterDialog(JLabel label, Engine e) {
        this.e = e;
        setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
        setLocationRelativeTo(null);
        
        JButton ok = new JButton(
            new AbstractAction("OK") {
                @Override
                public void actionPerformed(ActionEvent ae)
                {
                    e.startTime();
                    DisasterDialog.this.setVisible(false);
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
    }
    
    public void setActive() {
        e.stopTime();
        setLocationRelativeTo(null);
        this.setVisible(true);
    }
}
