package view;

import model.Zone;
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
        //*
        int fieldSize = 100;
        int width = 5;
        int height = 5;
         //*/
        /*
        int fieldSize = 20;
        int width = 40;
        int height = 30;
        */
        assets = new Assets();

        engine = new Engine(width, height);

        topPanel = new JPanel();
        //The width calculated explicitly.
        //The -1 indicates that we don't have control over it.
        topPanel.setPreferredSize(
                new Dimension(-1, 50));
        topPanel.setBackground(Color.LIGHT_GRAY);

        add(topPanel, BorderLayout.NORTH);

        rightPanel = new JPanel();
        //The height calculated explicitly.
        //The -1 indicates that we don't have control over it.
        rightPanel.setPreferredSize(
                new Dimension(100, -1));
        rightPanel.setBackground(Color.DARK_GRAY);

        police = new JButton("Police 1x1");
        police.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                engine.setCursorSignal(CursorSignal.POLICE);
                engine.setImg(Assets.police);
            }
        });
        rightPanel.add(police);
        university = new JButton("University 2x2");
        university.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                engine.setCursorSignal(CursorSignal.UNIVERSITY);
                engine.setImg(Assets.university);
            }
        });
        rightPanel.add(university);
        service = new JButton("Service 1x1");
        service.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                engine.setCursorSignal(CursorSignal.SERVICE);
                engine.setImg(Assets.copperS);
            }
        });
        rightPanel.add(service);
        industry = new JButton("Industry 1x1");
        industry.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                engine.setCursorSignal(CursorSignal.INDUSTRY);
                engine.setImg(Assets.copperI);
            }
        });
        rightPanel.add(industry);
        road = new JButton("Road 1x1");
        road.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                engine.setCursorSignal(CursorSignal.ROAD);
                engine.setImg(Assets.roadNS);
            }
        });
        rightPanel.add(road);
        stadium = new JButton("Stadium 2x2");
        stadium.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                engine.setCursorSignal(CursorSignal.STADIUM);
                engine.setImg(Assets.stadium);
            }
        });
        rightPanel.add(stadium);
        highScool = new JButton("High School 1x2");
        highScool.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                engine.setCursorSignal(CursorSignal.HIGH_SCHOOL);
                engine.setImg(Assets.highSchool);
            }
        });
        rightPanel.add(highScool);
        residence = new JButton("Residence 1x1");
        residence.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                engine.setCursorSignal(CursorSignal.RESIDENCE);
                engine.setImg(Assets.copperR);
            }
        });
        rightPanel.add(residence);

        //TODO (Not complete.)
        selectMode = new JButton("select mode");
        selectMode.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                engine.setCursorSignal(CursorSignal.SELECT);
            }
        });
        rightPanel.add(selectMode);

        destroyZone = new JButton("destroyZone");
        destroyZone.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                engine.setCursorSignal(CursorSignal.DESTROY);
            }
        });
        rightPanel.add(destroyZone);

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
        rightPanel.add(upgrade);
         */
        fillGrid = new JButton("fill grid");
        fillGrid.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                grid.fillGrid();
            }
        });
        rightPanel.add(fillGrid);

        add(rightPanel, BorderLayout.EAST);

        grid = new Grid(fieldSize, width, height, engine);
        add(grid, BorderLayout.WEST);

        pack();
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
    }
}
