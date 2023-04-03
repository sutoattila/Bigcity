package rightPanel;

import javax.swing.JPanel;
import javax.swing.Box;
import javax.swing.BoxLayout;
import java.awt.Color;
import java.awt.Dimension;

public class BuildPanel extends JPanel {
    public static BuildButton selected = null;
    
    /**
     * Sets the given button as selected (also changes it's color to yellow, 
     * and sets the previous selected back to default color scheme)
     * @param button
     */
    public static void selectButton(BuildButton button){
        deleteSelection();
        selected = button;
        selected.setBackground(Color.yellow);
        selected.btnPanel.setBackground(Color.yellow);
    }
    
    /**
     * Drops the selection from the selected button
     */
    public static void deleteSelection(){
        if(selected != null){
            selected.setBackground(Color.gray);
            selected.btnPanel.setBackground(Color.gray);
            selected = null;
        }
    }

    /**
     * Constructor
     * @param builds The BuildButtons you want to display
     */
    public BuildPanel(BuildButton... builds){
        super();
        XButton exit = new XButton(new BuildButton.deleteSelected());
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        JPanel exitPanel = new JPanel();
        exitPanel.setLayout(new BoxLayout(exitPanel, BoxLayout.X_AXIS));
        exitPanel.add(Box.createHorizontalGlue());
        exitPanel.add(exit);
        exitPanel.setAlignmentX(0);
        exitPanel.setBackground(Color.GREEN);
        add(exitPanel);

        JPanel buildPanel = new JPanel();
        buildPanel.setLayout(new BoxLayout(buildPanel, BoxLayout.Y_AXIS));
        buildPanel.setBackground(Color.GREEN);
        for (BuildButton buildButton : builds) {
            buildPanel.add(buildButton);
        }
        add(buildPanel);
        setPreferredSize(new Dimension(170, 100));
    }
}