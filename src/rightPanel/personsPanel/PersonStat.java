package rightPanel.personsPanel;

import bigcity.Person;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import roundPanel.RoundedPanel;

public class PersonStat extends RoundedPanel {
    public Person person;
    private static BufferedImage male = scaleImage(14, 32, "src/images/man.png");
    private static BufferedImage female = scaleImage(14, 32, "src/images/woman.png");
    
    PersonStat(Person p){
        super(p.isMale() ? Color.CYAN : Color.PINK);
        person = p;
        JPanel panel = new JPanel();
        this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(person.isMale() ? Color.CYAN : Color.PINK);
        panel.add(new JLabel(person.getName()));
        panel.add(new JLabel(String.valueOf(person.getAge())));
        this.add(new JLabel(new ImageIcon(person.isMale() ? male : female)));
        this.add(Box.createRigidArea(new Dimension(10,0)));
        this.add(panel);
        this.add(Box.createRigidArea(new Dimension(20,0)));
        this.add(Box.createHorizontalGlue());
        JLabel happiness = new JLabel(person.getHappiness()+"%");
        happiness.setFont(new Font("Arial", Font.BOLD, 25));
        happiness.setForeground(Color.MAGENTA.darker());
        this.add(happiness);
        setPreferredSize(new Dimension(100, 30));
    }
    
    private static BufferedImage scaleImage(int WIDTH, int HEIGHT, String filename) {
        BufferedImage bi;
        try {
            ImageIcon ii = new ImageIcon(filename);
            bi = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2d = (Graphics2D) bi.createGraphics();
            g2d.addRenderingHints(new RenderingHints(RenderingHints.KEY_RENDERING,RenderingHints.VALUE_RENDER_QUALITY));
            g2d.drawImage(ii.getImage(), 0, 0, WIDTH, HEIGHT, null);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return bi;
    }
}
