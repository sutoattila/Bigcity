package rightPanel.buildingStatPanel;

import bigcity.PublicZone;
import bigcity.Residence;
import bigcity.Workplace;
import java.awt.Component;
import java.awt.Dimension;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import rightPanel.personsPanel.PersonsPanel;

public class BuildingStatPanel extends JPanel {
    protected PersonsPanel pPanel;
    protected BuildingStat bStat;
    protected boolean hasCitizens;
    
    private Component separator;

    public BuildingStatPanel(Residence zone) {
        pPanel = new PersonsPanel(zone.getResidents());
        bStat = new BuildingStat(zone);
        hasCitizens = true;
        addElements();
    }
    
    public BuildingStatPanel(Workplace zone) {
        pPanel = new PersonsPanel(zone.getWorkers());
        bStat = new BuildingStat(zone);
        hasCitizens = true;
        addElements();
    }
    
    public BuildingStatPanel(PublicZone zone) {
        bStat = new BuildingStat(zone);
        hasCitizens = false;
        addElements();
    }
    
    private void addElements(){
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        if(hasCitizens){
            JScrollPane personsScroll = new JScrollPane(pPanel);
            add(personsScroll);
            
            separator = Box.createRigidArea(new Dimension(0,20));
            add(separator);
        }
        add(bStat);
    }
    
}
