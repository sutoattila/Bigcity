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
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
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
    protected JPanel mainContent;
    
    protected LoadWindow(MainMenu menu) {
        this.menu = menu;
        menu.setVisible(false);
        this.setUndecorated(true);
        this.setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
        this.setPreferredSize(new Dimension(630, 430));
        //this.setContentPane(new JLabel(new ImageIcon("GUI/menu.png")));
        this.mainContent = new JPanel();
        
        JPanel top = new JPanel();
        JPanel middle = new JPanel();
        JPanel bottom = new JPanel();
        
        //left.setOpaque(false);
        //center.setOpaque(true);
        //right.setOpaque(false);
        
        top.setLayout(new BoxLayout(top, BoxLayout.X_AXIS));
        middle.setLayout(new BoxLayout(middle, BoxLayout.X_AXIS));
        
        JButton backButton = new JButton(
            new AbstractAction("Vissza") {
            @Override
            public void actionPerformed(ActionEvent e) {
                LoadWindow.this.setVisible(false);
                menu.setVisible(true);
            }
        });
        backButton.setPreferredSize(new Dimension(80, 20));
        top.add(backButton);
        top.add(Box.createHorizontalGlue());
        JLabel listTitle = new JLabel("Város neve:");
        listTitle.setOpaque(true);
        listTitle.setBackground(Color.WHITE);
        listTitle.setPreferredSize(new Dimension(400, 20));
        //center.add(Box.createVerticalGlue());
        top.add(listTitle);
        top.add(Box.createHorizontalGlue());
        top.add(Box.createRigidArea(new Dimension(80,20)));
        
        refreshOptions();
        middle.add(mainContent);
        bottom.add(new JButton(
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
        //center.add(Box.createVerticalGlue());
        
        
        this.add(Box.createRigidArea(new Dimension(0,20)));
        this.add(top);
        //this.add(Box.createHorizontalGlue());
        this.add(middle);
        //this.add(Box.createHorizontalGlue());
        this.add(bottom);
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
                mainContent.remove(listScroller);
            File thisGame = new File("savedGames/savedGames.txt");
            thisGame.getParentFile().mkdirs();
            thisGame.createNewFile();
            DefaultListModel<String> options = new DefaultListModel<>();
            Files.lines(Path.of("savedGames","savedGames.txt"))
                .forEach(n -> options.addElement(n));
            this.savedGames = new JList<>(options);
            listScroller = new JScrollPane(savedGames);
            listScroller.setPreferredSize(new Dimension(400, 300));
            mainContent.add(listScroller);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
