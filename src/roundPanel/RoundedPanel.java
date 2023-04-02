package roundPanel;

import java.awt.Color;
import javax.swing.JPanel;

public class RoundedPanel extends JPanel {
    public RoundedPanel(Color color) {
        super();
        setBackground(color);
        setBorder(new RoundedBorder(color, 2, 10));
    }
}
