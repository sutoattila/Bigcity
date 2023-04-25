package view;

import GUI.SettingsDialog;
import GUI.StatElement;
import bigcity.Zone;
import grid.Grid;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import javax.swing.AbstractAction;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.Timer;
import static javax.swing.WindowConstants.EXIT_ON_CLOSE;
import model.CursorSignal;
import model.Disaster;
import model.Engine;
import res.Assets;
import rightPanel.BuildPanel;
import rightPanel.BuildButton;
import rightPanel.buildingStatPanel.BuildingStatPanel;

public class BigCityJframe extends JFrame {

    public static SimpleDateFormat dateFormater
            = new SimpleDateFormat("yyyy-MM-dd");

    Engine engine;

    JButton destroyZone;

    Timer timer;
    Date date;
    boolean isStopped;
    SettingsDialog settings;

    JPanel topPanel;
    BuildPanel buildPanel;
    BuildingStatPanel statPanel;
    Grid grid;
    private final int fieldSize;

    StatElement calendar;//Attila volt
    StatElement money;
    StatElement happy;

    public BigCityJframe() {
        super("BigCity");

        this.timer = new Timer(3000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!isStopped) {
                    dayPassed();
                }
            }
        });
        timer.start();
        isStopped = false;
        
        //Attila menu 
        JMenuBar menuBar = new JMenuBar();
        JMenu gameMenu = new JMenu("Játék");
        JMenuItem saveJMenuItem = new JMenuItem(
                new AbstractAction("Mentés") {
            @Override
            public void actionPerformed(ActionEvent e) {
                //TODO
            }
        });
        JMenuItem disasterJMenuItem = new JMenuItem(
                new AbstractAction("Katasztrófa kérése") {
            @Override
            public void actionPerformed(ActionEvent e) {
                engine.makeDisaster();
            }
        });
        JMenuItem settingsJMenuItem = new JMenuItem(
                new AbstractAction("Beállítások") {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(null == settings) {
                    settings = new SettingsDialog(BigCityJframe.this);
                }
                settings.setActive();
            }
        });
        JMenuItem exitJMenuItem = new JMenuItem(
                new AbstractAction("Kilépés") {
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
                if (isStopped) {
                    isStopped = false;
                }
                timer.setDelay(3000);
            }
        });
        JMenuItem speed3 = new JMenuItem(new AbstractAction("x3") {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (isStopped) {
                    isStopped = false;
                }
                timer.setDelay(1000);
            }
        });
        JMenuItem speed5 = new JMenuItem(new AbstractAction("x5") {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (isStopped) {
                    isStopped = false;
                }
                timer.setDelay(600);
            }
        });
        JMenuItem startStop = new JMenuItem(
                new AbstractAction("Megállít/Elindít") {
            @Override
            public void actionPerformed(ActionEvent e) {
                isStopped = !isStopped;
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

        this.fieldSize = 100;
        int width = 5;
        int height = 5;

        new Assets();

        engine = new Engine(width, height, this.fieldSize, this);

        topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topPanel.setPreferredSize(new Dimension(-1, 50));
        topPanel.setBackground(Color.GREEN.darker().darker());

        happy = new StatElement("view/happiness.png", "100%");
        happy.setTextColor(Color.MAGENTA.darker());

        calendar = new StatElement("view/calendar.png", "2023-03-19");
        this.date = new Date(0);
        calendar.setText(dateFormater.format(date));

        money = new StatElement("view/money.png", String.format("%,d", engine.getMoney()) + "$");
        money.setTextColor(Color.YELLOW.darker());

        topPanel.add(money);
        topPanel.add(happy);
        topPanel.add(calendar);

        BuildButton lakohely = new BuildButton("buildPanel/images/house.png", "Lakóhely", CursorSignal.RESIDENCE.getPriceL1());
        BuildButton ipariTerulet = new BuildButton("buildPanel/images/factory.png", "Ipari terület", CursorSignal.INDUSTRY.getPriceL1());
        BuildButton szolgaltatas = new BuildButton("buildPanel/images/store.png", "Szolgáltatás", CursorSignal.SERVICE.getPriceL1());
        BuildButton ut = new BuildButton("buildPanel/images/road.png", "Út", CursorSignal.ROAD.getPriceL1());
        BuildButton rendorseg = new BuildButton("buildPanel/images/police.png", "Rendőrség", CursorSignal.POLICE.getPriceL1());
        BuildButton stadion = new BuildButton("buildPanel/images/stadium.png", "Stadion", CursorSignal.STADIUM.getPriceL1());
        BuildButton iskola = new BuildButton("buildPanel/images/school.png", "Iskola", CursorSignal.HIGH_SCHOOL.getPriceL1());
        BuildButton egyetem = new BuildButton("buildPanel/images/university.png", "Egyetem", CursorSignal.UNIVERSITY.getPriceL1());

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
        // Keep it for test. It's faster (not faster performance but easier to 
        // use) to destroy a zone with this. It makes easier to destroy multiple
        // zones.
        destroyZone = new JButton("destroyZone");
        destroyZone.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                engine.setCursorSignal(CursorSignal.DESTROY);
            }
        });
        buildPanel.add(destroyZone);
        // ---------------------------------------------------------------------

        grid = new Grid(fieldSize, width, height, engine, this);

        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.add(topPanel);
        leftPanel.add(grid);
        add(leftPanel, BorderLayout.WEST);
        add(buildPanel, BorderLayout.EAST);

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
        //The XButton in StatPanel will call this function.
        remove(statPanel);
        statPanel = null;
        add(buildPanel, BorderLayout.EAST);
        pack();
        setLocationRelativeTo(null);

    }

    public void repaintStatPanelAndGrid() {
        if (null != statPanel) {
            //remove(statPanel);
            //statPanel = new BuildingStatPanel(statPanel.getZone(), this);
            //add(statPanel, BorderLayout.EAST);
            //pack();
            if (null != statPanel.getpPanel()) {
                statPanel.getpPanel().updatePeople();
            }
            if (null != statPanel.getbStat()) {
                statPanel.getbStat().updateStats();
            }
        }
        grid.repaint();
    }

    public void dayPassed() {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.DATE, 1);
        date = c.getTime();
        refreshDate();
        refreshMoney();

        // ITT HÍVJUK MEG A NAPONTA ÚJRASZÁMOLANDÓÓ FÜGGVÉNYEKET ==> 
        //      (elköltöznek-e, költözik-e be valaki stb)
        engine.dayPassed();
    }

    public void refreshMoney() {
        money.setText(String.format("%,d", engine.getMoney()) + "$");
    }

    public void refreshDate() {
        calendar.setText(dateFormater.format(date));
    }
    
    public void setHappiness(double happiness) {
        happy.setText(Math.round(happiness)+"%");
    }
    
    public void refreshGrid() {
        grid.repaint();
    }

    public void addMoney(int money) {
        engine.addMoney(money);
        refreshMoney();
    }

    public Engine getEngine() {
        return engine;
    }

    public int getFieldSize() {
        return fieldSize;
    }

    public StatElement getHappy() {
        return happy;
    }

    public int getCurrentTax() {
        return engine.getTaxPercentage();
    }
    
    public void setTax(int tax) {
        engine.setTaxPercentage(tax);
    }
}
