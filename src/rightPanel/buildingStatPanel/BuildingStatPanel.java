package rightPanel.buildingStatPanel;

import bigcity.PrivateZone;
import bigcity.Residence;
import bigcity.Workplace;
import bigcity.Zone;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
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
import rightPanel.XButton;
import rightPanel.personsPanel.PersonsPanel;
import view.BigCityJframe;

public class BuildingStatPanel extends JPanel {
    protected PersonsPanel pPanel;
    protected BuildingStat bStat;
    protected boolean hasCitizens;
    protected BigCityJframe bigCityJFrame;
    protected Zone zone;
    
    private Component separator;

    public BuildingStatPanel(Zone zone, BigCityJframe bigCityJFrame) {
        setBackground(Color.GREEN.brighter());
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        
        JPanel exitPanel = new JPanel();
        exitPanel.setLayout(new BoxLayout(exitPanel, BoxLayout.X_AXIS));
        exitPanel.setBackground(Color.GREEN.brighter());
        exitPanel.add(Box.createHorizontalGlue());
        exitPanel.add(new XButton(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                bigCityJFrame.changeRightPanelToBuildPanel();
                bigCityJFrame.repaint();
            }
        }));
        add(exitPanel);
        
        this.bigCityJFrame = bigCityJFrame;
        this.zone = zone;
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
        setPreferredSize(new Dimension(170, 100));
    }
    
    private void addElements(){
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        add(Box.createRigidArea(new Dimension(0,20)));
        if(hasCitizens){
            JScrollPane personsScroll = new JScrollPane(pPanel);
            add(personsScroll);
            
            separator = Box.createRigidArea(new Dimension(0,20));
            add(separator);
        }
        add(bStat);
        add(Box.createRigidArea(new Dimension(0,20)));
        JPanel upgradePanel = new JPanel();
        JButton upgradeButton = new JButton("upgrade");
        upgradeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(zone instanceof PrivateZone) {
                    PrivateZone tmp = (PrivateZone) zone;
                    tmp.upgrade();
                    bStat.updateStats();
                }
            }
        });
        upgradeButton.setBackground(Color.CYAN);
        
        JButton destroyButton = new JButton("destroy");
        destroyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                bigCityJFrame.changeRightPanelToBuildPanel();
                Engine.setCursorSignal(CursorSignal.DESTROY);
                bigCityJFrame.repaint();
            }
        });
        destroyButton.setBackground(Color.RED.darker());
        
        upgradePanel.setLayout(new BoxLayout(upgradePanel, BoxLayout.X_AXIS));
        upgradePanel.setBackground(Color.GREEN.brighter());
        upgradePanel.add(Box.createHorizontalGlue());
        upgradePanel.add(upgradeButton);
        upgradePanel.add(Box.createHorizontalGlue());
        upgradePanel.add(destroyButton);
        upgradePanel.add(Box.createHorizontalGlue());
        
        this.add(upgradePanel);
        
    }
    
}
