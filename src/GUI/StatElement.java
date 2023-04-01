package GUI;

import java.awt.*;
import javax.swing.*;

public class StatElement extends JPanel {
    int x;
    int y;
    int width;
    int height;
    Color textColor;
    
   public StatElement(String filename,String text) {
      Dimension d = new Dimension(120,40);
      height=d.height;
      width=d.width;
      setPreferredSize(d);
      setBackground(Color.GREEN.darker());
      JLabel txt = new JLabel(text);
      JLabel image = new JLabel();
      ImageIcon iconLogo = new ImageIcon(filename);
      image.setIcon(iconLogo);
      txt.setForeground(textColor);
      setLayout(new FlowLayout());
      add(image);
      add(txt);
      setVisible(true);
   }
   
   public void setTextColor(Color c){
      textColor=c;
   }
   
   public void paintComponent(Graphics g) {
    super.paintComponent(g);
      Graphics2D g2d = (Graphics2D) g;
      g2d.setColor(Color.BLACK);
      System.out.println(width+" "+height);
      g2d.fillRoundRect(0, 0, width,height,40,40); // to draw a rounded rectangle.
   }
}
