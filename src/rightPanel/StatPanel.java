package rightPanel;

import bigcity.Zone;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import view.BigCityJframe;

public class StatPanel extends JPanel {

    public StatPanel(Zone zone, BigCityJframe bigCityJFrame) {
        setBackground(Color.cyan);
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        JButton buildPanel = new JButton("build panel");
        buildPanel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                bigCityJFrame.changeRightPanelToBuildPanel();
            }
        });
        add(buildPanel);

    }

}
