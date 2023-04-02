package rightPanel.personsPanel;

import bigcity.Person;
import java.awt.Dimension;
import java.util.List;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;

public class PersonsPanel extends JPanel{
    protected Dimension preferedDimension;
    
    public PersonsPanel(Person... persons) {
        super();
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        for (Person person : persons) {
            add(new PersonStat(person));
            add(Box.createRigidArea(new Dimension(0,5)));
        }
        preferedDimension = new Dimension(150, persons.length*37);
        setPreferredSize(preferedDimension);
        
    }
    
    public PersonsPanel(List<Person> persons) {
        super();
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        for (Person person : persons) {
            add(new PersonStat(person));
            add(Box.createRigidArea(new Dimension(0,5)));
        }
        preferedDimension = new Dimension(150, persons.size()*37);
        
        setPreferredSize(preferedDimension);
    }
    
    public Dimension getPreferedDimension(){
        Dimension tmp = new Dimension(preferedDimension);
        return tmp;
    }
    
    public void setDimension(Dimension d) {
        preferedDimension = d;
    }
}
