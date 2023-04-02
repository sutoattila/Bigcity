package rightPanel.buildingStatPanel;

import bigcity.Zone;
import java.awt.Color;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import roundPanel.RoundedBorder;

public class BuildingStat extends JPanel {
    // TODO hatékonyságnövelés: Mind a BuildStatPanel, mind a PersonPanel egyke
    //      osztály és csak beállítom hogy éppen mit mutatson
    
    private Zone building;
    
    public BuildingStat(Zone building) {
        super();
        setBackground(Color.WHITE);
        setBorder(new RoundedBorder(Color.WHITE, 4, 8));
        this.building = building;
        JLabel levelText = new JLabel("Épület szintje: ");
        JLabel level = new JLabel(String.valueOf(building.getLevel()));
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        JPanel levelPanel = new JPanel();
        levelPanel.add(levelText);
        levelPanel.add(level);
        levelPanel.setBackground(Color.WHITE);
        this.add(levelPanel);
    }
}
