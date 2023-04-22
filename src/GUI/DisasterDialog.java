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

public class DisasterDialog extends JDialog {
    
    public DisasterDialog(JLabel label) {
        setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
        setLocationRelativeTo(null);
        
        JButton ok = new JButton(
            new AbstractAction("OK") {
                public void actionPerformed(ActionEvent e)
                {
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
        setLocationRelativeTo(null);
        this.setVisible(true);
    }
}
