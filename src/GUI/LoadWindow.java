package GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import javax.swing.AbstractAction;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import view.BigCityJframe;

public class LoadWindow extends JFrame{
    protected JList<String> savedGames;
    protected MainMenu menu;
    protected JScrollPane listScroller;
    
    protected LoadWindow(MainMenu menu) {
        this.menu = menu;
        menu.setVisible(false);
        this.setUndecorated(true);
        //this.setDefaultCloseOperation(HIDE_ON_CLOSE);
        this.setLayout(new BorderLayout());
        JPanel tmp = new JPanel();
        JLabel listTitle = new JLabel("Város neve:");
        tmp.add(listTitle);
        tmp.setPreferredSize(new Dimension(400, 20));
        tmp.setBackground(Color.white);
        this.add("North", tmp);
        refreshOptions();
        this.add("South", new JButton(
            new AbstractAction("Indítás") {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!savedGames.isSelectionEmpty()) {
                    BigCityJframe.loadGame(savedGames.getSelectedValue());
                    LoadWindow.this.setVisible(false);
                } else {
                    //TODO pop-up ami írja hogy nincs semmi kiválasztva
                    System.out.println("Nincs kiválasztva semmi.");
                }
            }
        }
        ));
        this.add("West", new JButton(
            new AbstractAction("Vissza") {
            @Override
            public void actionPerformed(ActionEvent e) {
                LoadWindow.this.setVisible(false);
                menu.setVisible(true);
            }
        }));
        this.pack();
    }
    
    public void showLoadWindow(MainMenu menu) {
        setLocationRelativeTo(null);
        refreshOptions();
        menu.setVisible(false);
        setVisible(true);
    }
    
    protected final void refreshOptions() {
        try {
            if(null != listScroller)
                remove(listScroller);
            File thisGame = new File("savedGames/savedGames.txt");
            thisGame.getParentFile().mkdirs();
            thisGame.createNewFile();
            DefaultListModel<String> options = new DefaultListModel<>();
            Files.lines(Path.of("savedGames","savedGames.txt"))
                .forEach(n -> options.addElement(n));
            this.savedGames = new JList<>(options);
            listScroller = new JScrollPane(savedGames);
            listScroller.setPreferredSize(new Dimension(400, 200));
            add("Center",listScroller);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
