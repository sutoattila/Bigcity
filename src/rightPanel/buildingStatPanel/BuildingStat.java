package rightPanel.buildingStatPanel;

import bigcity.PrivateZone;
import bigcity.Zone;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import roundPanel.RoundedBorder;

public class BuildingStat extends JPanel {
    private Zone building;
    private final JLabel level;
    private JLabel size;
    private JLabel capacity;
    
    /**
     * Constructor
     * @param building - Zone, the zone about which the statistics are created
     */
    public BuildingStat(Zone building) {
        super();
        setBackground(Color.WHITE);
        setBorder(new RoundedBorder(Color.WHITE, 4, 8));
        this.building = building;
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        if(building instanceof PrivateZone) {
            PrivateZone tmp = (PrivateZone) building;
            
            JLabel capacityText = new JLabel("Kapacitás: ");
            capacity = new JLabel(tmp.getCapacity()+ " fő");
            JPanel capacityPanel = new JPanel();
            capacityPanel.setBackground(Color.WHITE);
            capacityPanel.add(capacityText);
            capacityPanel.add(capacity);
            
            JLabel sizeText = new JLabel("Telítettség: ");
            size = new JLabel(tmp.getSize() + " fő");
            JPanel sizePanel = new JPanel();
            sizePanel.setBackground(Color.WHITE);
            sizePanel.add(sizeText);
            sizePanel.add(size);
            
            this.add(capacityPanel);
            this.add(sizePanel);
            
            level = new JLabel("3/"+building.getLevel());
        } else {
            level = new JLabel("1/"+building.getLevel());
        }
        JLabel levelText = new JLabel("Épület szintje: ");
        JPanel levelPanel = new JPanel();
        levelPanel.add(levelText);
        levelPanel.add(level);
        levelPanel.setBackground(Color.WHITE);
        this.add(levelPanel);
        //setPreferredSize(new Dimension(150, 100));
        setMaximumSize(new Dimension(150, 1000));
    }
    
    /**
     * Update the statistic of the zone
     */
    public void updateStats() {
        if(building instanceof PrivateZone){
            level.setText("3/"+building.getLevel());
        } else {
            level.setText("1/"+building.getLevel());
        }
        if(building instanceof PrivateZone tmp) {
            size.setText(tmp.getSize() + " fő");
            capacity.setText(tmp.getCapacity()+ " fő");
        }
    }
}
