package view;

import grid.Grid;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;

import javax.swing.JPanel;

import static javax.swing.WindowConstants.EXIT_ON_CLOSE;
import model.CursorSignal;
import model.Engine;
import res.Assets;
import buildPanel.BuildPanel;
import buildPanel.BuildButton;

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

    public BigCityJframe() {
        super("BigCity");
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

        BuildPanel buildPanel = new BuildPanel(
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
        
        /*TODO: Finish select mode first! Can be used only if an upgradable zone
        //is selected. Appears on the new stat JPanel on the right if a zone is
        //selected.
        upgrade = new JButton("upgrade");
        upgrade.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //TODO
            }
        });
         */
        
        // ---------------------------------------------------------------------
        
        add(buildPanel, BorderLayout.EAST);
        
        grid = new Grid(fieldSize, width, height, engine);
        add(grid, BorderLayout.WEST);

        pack();
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
    }
}
