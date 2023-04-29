package rightPanel.buildingStatPanel;

import bigcity.EducationLevel;
import bigcity.HighSchool;
import bigcity.Industry;
import bigcity.Person;
import bigcity.Police;
import bigcity.PrivateZone;
import bigcity.Residence;
import bigcity.Road;
import bigcity.Stadium;
import bigcity.Workplace;
import bigcity.Zone;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import model.CursorSignal;
import model.Engine;
import rightPanel.XButton;
import rightPanel.personsPanel.PersonsPanel;
import view.BigCityJframe;
import grid.Grid;

public class BuildingStatPanel extends JPanel {

    protected PersonsPanel pPanel;
    protected BuildingStat bStat;
    protected boolean hasCitizens;
    protected BigCityJframe bigCityJFrame;
    protected Zone zone;
    
    protected Grid grid;
    
    public BuildingStatPanel(Zone zone, BigCityJframe bigCityJFrame, Grid grid) {
        this.grid=grid;
        JLabel name = new JLabel();
        if (zone instanceof PrivateZone) {
            if (zone instanceof Residence) {
                name.setText("Lakóingatlan");
            } else if (zone instanceof Industry) {
                name.setText("Ipari épület");
            } else {
                name.setText("Szolgáltatás");
            }
        } else {
            if (zone instanceof Road) {
                name.setText("Út");
            } else if (zone instanceof Stadium) {
                name.setText("Stadion");
            } else if (zone instanceof Police) {
                name.setText("Rendőrség");
            } else if (zone instanceof HighSchool) {
                name.setText("Középiskola");
            } else {
                name.setText("Egyetem");
            }
        }

        setBackground(Color.GREEN.brighter());
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        JPanel exitPanel = new JPanel();
        exitPanel.setLayout(new BoxLayout(exitPanel, BoxLayout.X_AXIS));
        exitPanel.setBackground(Color.GREEN.brighter());
        exitPanel.add(Box.createRigidArea(new Dimension(20, 0)));
        exitPanel.add(name);
        exitPanel.add(Box.createHorizontalGlue());
        exitPanel.add(new XButton((ActionEvent e) -> {
            bigCityJFrame.changeRightPanelToBuildPanel();
            bigCityJFrame.repaint();
        },grid));
        add(exitPanel);

        this.bigCityJFrame = bigCityJFrame;

        JButton addPerson = new JButton("Ember hozzáadása");
        addPerson.addActionListener((ActionEvent e) -> {
            if (zone instanceof Residence tmp) {
                tmp.addPerson(new Person("Norbi", 22, 89,
                        true, EducationLevel.UNIVERSITY,
                        tmp, null));
                pPanel.updatePeople();
                bStat.updateStats();
                revalidate();
                repaint();
                bigCityJFrame.repaint();
            } else if (zone instanceof Workplace tmp) {
                tmp.addPerson(new Person("Anna", 21, 88,
                        false, EducationLevel.UNIVERSITY,
                        tmp, null));
                pPanel.updatePeople();
                bStat.updateStats();
                revalidate();
                repaint();
                bigCityJFrame.repaint();
            }
        });
        if (zone instanceof PrivateZone) {
            JPanel asd = new JPanel();
            asd.add(addPerson);
            //add(addPerson);
            add(asd);
        }

        this.zone = zone;
        if (zone instanceof Residence) {
            Residence tmp = (Residence) zone;
            pPanel = new PersonsPanel(tmp.getResidents());
            hasCitizens = true;
        } else if (zone instanceof Workplace) {
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

    private void addElements() {
        add(Box.createRigidArea(new Dimension(0, 20)));
        if (hasCitizens) {
            JScrollPane personsScroll = new JScrollPane(pPanel);

            //-------- Ha nem rakom ki akkor túlcsordul a grafika, megkérdezni
            //                                          mit lehet kedzeni vele
            personsScroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
            //---------

            add(personsScroll);
            add(Box.createRigidArea(new Dimension(0, 20)));
        }
        add(bStat);
        add(Box.createRigidArea(new Dimension(0, 20)));
        JPanel upgradePanel = new JPanel();
        JButton upgradeButton = new JButton("-");
        if (zone instanceof PrivateZone tmp) {
            if (tmp.getLevel() < 3) {
                upgradeButton.setText("upgrade");
            }
            upgradeButton.addActionListener((ActionEvent e) -> {
                if (tmp.getLevel() < 3) {
                    int price;
                    if (tmp instanceof Residence) {
                        price = tmp.getLevel() == 1
                                ? CursorSignal.RESIDENCE.getPriceL2()
                                : CursorSignal.RESIDENCE.getPriceL3();
                    } else if (tmp instanceof Industry) {
                        price = tmp.getLevel() == 1
                                ? CursorSignal.INDUSTRY.getPriceL2()
                                : CursorSignal.INDUSTRY.getPriceL3();
                    } else {
                        price = tmp.getLevel() == 1
                                ? CursorSignal.SERVICE.getPriceL2()
                                : CursorSignal.SERVICE.getPriceL3();
                    }
                    tmp.upgrade();
                    bigCityJFrame.addMoney(-price);
                }
                if (tmp.getLevel() == 3) {
                    upgradeButton.setText("-");
                }
                bStat.updateStats();
                
                bigCityJFrame.getEngine().moveEveryOne();
                bigCityJFrame.repaint();
            });
        }
        upgradeButton.setBackground(Color.CYAN);

        JButton destroyButton = new JButton("destroy");
        destroyButton.addActionListener((ActionEvent e) -> {
            bigCityJFrame.getEngine().destroyZone(
                    zone.getTopLeftY() / bigCityJFrame.getFieldSize(),
                    zone.getTopLeftX() / bigCityJFrame.getFieldSize(),
                    bigCityJFrame.getFieldSize(),
                    false);
            Engine.setCursorSignal(CursorSignal.SELECT);
            bigCityJFrame.changeRightPanelToBuildPanel();
            bigCityJFrame.repaint();
            grid.removeTheSelectionOfTheSelectedZone();
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

    public Zone getZone() {
        return zone;
    }

    public PersonsPanel getpPanel() {
        return pPanel;
    }

    public BuildingStat getbStat() {
        return bStat;
    }

}
