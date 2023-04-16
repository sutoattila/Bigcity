/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bigcity;

import java.util.List;

/**
 *
 * @author mihalkonorbi
 */
public abstract class Workplace extends PrivateZone {

    protected List<Person> workers;

    public List<Person> getWorkers() {
        return workers;
    }

    @Override
    public void addPerson(Person p) {
        if (capacity > getSize()) {
            workers.add(p);
        }
    }

    public void clearWorkers() {
        workers.clear();
    }
}
