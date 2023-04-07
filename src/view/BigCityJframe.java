package view;

import bigcity.Zone;
import grid.Grid;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import javax.swing.JPanel;

import static javax.swing.WindowConstants.EXIT_ON_CLOSE;
import model.CursorSignal;
import model.Engine;
import res.Assets;
import rightPanel.BuildPanel;
import rightPanel.BuildButton;
import rightPanel.buildingStatPanel.BuildingStatPanel;

public class BigCityJframe extends JFrame {

    Engine engine;

    Grid grid;
    JPanel topPanel;
    JPanel rightPanel;

    JButton police;
    JButton stadium;
    JButton highScool;
    JButton university;

    JButton road;

    JButton residence;
    JButton service;
    JButton industry;

    JButton destroyZone;
    //Select a zone to see its own JPanel on the right of the JFrame.
    JButton selectMode;

    JButton upgrade;

    JButton fillGrid;

    Assets assets;

    BuildPanel buildPanel;
    BuildingStatPanel statPanel;

    public BigCityJframe() {
        super("BigCity");
        //Attila menu 
        JMenuBar menuBar = new JMenuBar();
        JMenu gameMenu = new JMenu("Játék");
        JMenuItem saveJMenuItem = new JMenuItem(new AbstractAction("Mentés") {
                @Override
                public void actionPerformed(ActionEvent e) {
                    //TODO
                }
        });
        JMenuItem disasterJMenuItem = new JMenuItem(new AbstractAction("Katasztrófa kérése") {
                @Override
                public void actionPerformed(ActionEvent e) {
                    //TODO
                }
        });
        JMenuItem settingsJMenuItem = new JMenuItem(new AbstractAction("Beállítások") {
                @Override
                public void actionPerformed(ActionEvent e) {
                    //TODO
                }
        });
        JMenuItem exitJMenuItem = new JMenuItem(new AbstractAction("Kilépés") {
                @Override
                public void actionPerformed(ActionEvent e) {
                    System.exit(0);
                }
        });
        gameMenu.add(saveJMenuItem);
        gameMenu.add(disasterJMenuItem);
        gameMenu.add(settingsJMenuItem);
        gameMenu.add(exitJMenuItem);
        menuBar.add(gameMenu);
        JMenu timeMenu = new JMenu("Idő");
        JMenuItem speed1 = new JMenuItem(new AbstractAction("x1") {
                @Override
                public void actionPerformed(ActionEvent e) {
                    //TODO
                }
        });
        JMenuItem speed3 = new JMenuItem(new AbstractAction("x3") {
                @Override
                public void actionPerformed(ActionEvent e) {
                    //TODO
                }
        });
        JMenuItem speed5 = new JMenuItem(new AbstractAction("x5") {
                @Override
                public void actionPerformed(ActionEvent e) {
                    //TODO
                }
        });
        JMenuItem startStop = new JMenuItem(new AbstractAction("Megállít/Elindít") {
                @Override
                public void actionPerformed(ActionEvent e) {
                    //TODO
                }
        });
        timeMenu.add(speed1);
        timeMenu.add(speed3);
        timeMenu.add(speed5);
        timeMenu.add(startStop);
        menuBar.add(timeMenu);
        this.setJMenuBar(menuBar);
        //Attila menu vége
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        int fieldSize = 100;
        int width = 5;
        int height = 5;

        assets = new Assets();

        engine = new Engine(width, height);

        topPanel = new JPanel();
        topPanel.setPreferredSize(new Dimension(-1, 50));
        topPanel.setBackground(Color.LIGHT_GRAY);
        add(topPanel, BorderLayout.NORTH);

        //TODO: nem volt kész a select gomb így azt ki kell javítani (XButton-ben)
        BuildButton lakohely = new BuildButton("buildPanel/images/house.png", "Lakóhely", 50);
        BuildButton ipariTerulet = new BuildButton("buildPanel/images/factory.png", "Ipari terület", 50);
        BuildButton szolgaltatas = new BuildButton("buildPanel/images/store.png", "Szolgáltatás", 50);
        BuildButton ut = new BuildButton("buildPanel/images/road.png", "Út", 50);
        BuildButton rendorseg = new BuildButton("buildPanel/images/police.png", "Rendőrség", 300);
        BuildButton stadion = new BuildButton("buildPanel/images/stadium.png", "Stadion", 300);
        BuildButton iskola = new BuildButton("buildPanel/images/school.png", "Iskola", 300);
        BuildButton egyetem = new BuildButton("buildPanel/images/university.png", "Egyetem", 500);

        lakohely.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                engine.setCursorSignal(CursorSignal.RESIDENCE);
                engine.setImg(Assets.copperR);
            }
        });

        ipariTerulet.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                engine.setCursorSignal(CursorSignal.INDUSTRY);
                engine.setImg(Assets.copperI);
            }
        });

        szolgaltatas.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                engine.setCursorSignal(CursorSignal.SERVICE);
                engine.setImg(Assets.copperS);
            }
        });

        ut.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                engine.setCursorSignal(CursorSignal.ROAD);
                engine.setImg(Assets.roadNS);
            }
        });

        rendorseg.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                engine.setCursorSignal(CursorSignal.POLICE);
                engine.setImg(Assets.police);
            }
        });

        stadion.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                engine.setCursorSignal(CursorSignal.STADIUM);
                engine.setImg(Assets.stadium);
            }
        });

        iskola.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                engine.setCursorSignal(CursorSignal.HIGH_SCHOOL);
                engine.setImg(Assets.highSchool);
            }
        });

        egyetem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                engine.setCursorSignal(CursorSignal.UNIVERSITY);
                engine.setImg(Assets.university);
            }
        });

        buildPanel = new BuildPanel(
                lakohely,
                ipariTerulet,
                szolgaltatas,
                ut,
                rendorseg,
                stadion,
                iskola,
                egyetem
        );

        // IDEIGLENES GOMB -----------------------------------------------------
        destroyZone = new JButton("destroyZone");
        destroyZone.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                engine.setCursorSignal(CursorSignal.DESTROY);
            }
        });
        buildPanel.add(destroyZone);

        /*TODO: Can be used only if an upgradable zone is selected. 
        //Appears on the new stat JPanel on the right if a zone is
        //selected.
        upgrade = new JButton("upgrade");
        upgrade.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //TODO
            }
        });
         */
        add(buildPanel, BorderLayout.EAST);

        grid = new Grid(fieldSize, width, height, engine, this);
        add(grid, BorderLayout.WEST);

        statPanel = null;

        pack();
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
    }

    public void changeRightPanelToStatPanel(Zone zone) {
        remove(buildPanel);
        if (null != statPanel) {
            remove(statPanel);
        }
        statPanel = new BuildingStatPanel(zone, this);
        add(statPanel, BorderLayout.EAST);
        pack();
        setLocationRelativeTo(null);
    }

    public void changeRightPanelToBuildPanel() {
        //TODO (XButton in StatPanel wiil call this function.)
        remove(statPanel);
        statPanel = null;
        add(buildPanel, BorderLayout.EAST);
        pack();
        setLocationRelativeTo(null);

    }
}
