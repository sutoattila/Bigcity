package rightPanel.buildingStatPanel;

import bigcity.Residence;
import bigcity.Workplace;
import bigcity.Zone;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import model.CursorSignal;
import model.Engine;
import rightPanel.personsPanel.PersonsPanel;
import view.BigCityJframe;

public class BuildingStatPanel extends JPanel {
    protected PersonsPanel pPanel;
    protected BuildingStat bStat;
    protected boolean hasCitizens;
    protected BigCityJframe bigCityJFrame;
    
    private Component separator;

    public BuildingStatPanel(Zone zone, BigCityJframe bigCityJFrame) {
        this.bigCityJFrame = bigCityJFrame;
        if(zone instanceof Residence) {
            Residence tmp = (Residence) zone;
            pPanel = new PersonsPanel(tmp.getResidents());
            hasCitizens = true;
        } else if(zone instanceof Workplace) {
            Workplace tmp = (Workplace) zone;
            pPanel = new PersonsPanel(tmp.getWorkers());
            hasCitizens = true;
        } else {
            hasCitizens = false;
        }
        bStat = new BuildingStat(zone);
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
        
        JPanel upgradePanel = new JPanel();
        JButton upgradeButton = new JButton("upgrade");
        JButton destroyButton = new JButton("destroy");
        destroyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                bigCityJFrame.changeRightPanelToBuildPanel();
                Engine.setCursorSignal(CursorSignal.DESTROY);
            }
        });
        
        upgradePanel.setLayout(new BoxLayout(upgradePanel, BoxLayout.X_AXIS));
        upgradePanel.add(Box.createHorizontalGlue());
        upgradePanel.add(upgradeButton);
        upgradePanel.add(Box.createHorizontalGlue());
        upgradePanel.add(destroyButton);
        upgradePanel.add(Box.createHorizontalGlue());
        
        this.add(upgradePanel);
        
    }
    
}
