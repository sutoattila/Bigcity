package buildPanel;

import javax.swing.JPanel;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.ImageIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author mihalkonorbi
 */
public class BuildButton extends JButton {
    protected JPanel btnPanel;
    protected String text;
    protected int price;

    /**
     * Constructor
     * @param url - The url of the image
     * @param name - Name of the building
     * @param price - Price of the building
     */
    public BuildButton(String url, String name, int price) {
        super();
        this.text = name;
        this.price = price;
        this.setLayout(new BorderLayout());
        this.setBackground(Color.gray);
        btnPanel = new JPanel();
        btnPanel.setLayout(new BorderLayout());
        btnPanel.setBackground(Color.gray);
        btnPanel.setOpaque(false);
        JLabel priceLabel = new JLabel(price + "$");
        priceLabel.setForeground(Color.green);
        btnPanel.add(BorderLayout.LINE_START, new JLabel(name+" "));
        btnPanel.add(BorderLayout.LINE_END, priceLabel);

        JLabel tmp = new JLabel(new ImageIcon(url));
        tmp.setBorder(new CompoundBorder(tmp.getBorder(), 
        new EmptyBorder(0,0,0,10)));
        this.add(BorderLayout.LINE_START, tmp);
        this.add(BorderLayout.CENTER, btnPanel);
        this.addActionListener(new changeSelected());
    }

    /**
     * Sets this instance to be the selected
     */
    private class changeSelected implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent ae){
            BuildPanel.selectButton((BuildButton)ae.getSource());
        }
    }

    /**
     * Drops the selections from this instance
     */
    public static class deleteSelected implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent ae){
            BuildPanel.deleteSelection();
        }
    }
}