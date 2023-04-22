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
import javax.swing.JSlider;
import javax.swing.WindowConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import view.BigCityJframe;

public class SettingsDialog extends JDialog implements ChangeListener{
    protected JPanel btnPanel;
    protected JSlider slider;
    protected JLabel value;
    protected BigCityJframe frame;
    
    public SettingsDialog(BigCityJframe frame) {
        super(frame, "Beállítások", true);
        setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
        this.frame = frame;
        slider = new JSlider(0, 100, frame.getCurrentTax());
        slider.setMajorTickSpacing(10);
        slider.setMinorTickSpacing(1);
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);
        slider.setSnapToTicks(true);
        slider.addChangeListener(this);
        
        JButton ok = new JButton(
            new AbstractAction("OK") {
                public void actionPerformed(ActionEvent e)
                {
                    frame.setTax(slider.getValue());
                    SettingsDialog.this.setVisible(false);
                }
            }
        );
        
        JButton cancel = new JButton(
            new AbstractAction("Cancel") {
                public void actionPerformed(ActionEvent e)
                {
                    SettingsDialog.this.setVisible(false);
                }
            }
        );
        
        value = new JLabel(""+frame.getCurrentTax());
        
        setLayout(new BorderLayout());
        btnPanel = new JPanel();
        btnPanel.setLayout(new BoxLayout(btnPanel, BoxLayout.X_AXIS));
        btnPanel.add(Box.createRigidArea(new Dimension(25,0)));
        btnPanel.add(ok);
        btnPanel.add(Box.createHorizontalGlue());
        btnPanel.add(value);
        btnPanel.add(Box.createHorizontalGlue());
        btnPanel.add(cancel);
        btnPanel.add(Box.createRigidArea(new Dimension(25,0)));
        add("Center", slider);
        add("South",btnPanel);
        
        setSize(new Dimension(500, 120));
    }
    
    @Override
    public void stateChanged(ChangeEvent e)
    {
        value.setText("" + slider.getValue());
    }
    
    public void setActive() {
        setLocationRelativeTo(null);
        int tmp = frame.getCurrentTax();
        value.setText(""+tmp);
        slider.setValue(tmp);
        this.setVisible(true);
    }
}
