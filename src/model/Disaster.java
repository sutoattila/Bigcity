package model;

import GUI.DisasterDialog;
import bigcity.Person;
import bigcity.Zone;
import java.util.List;
import java.util.Random;
import java.util.function.Consumer;
import javax.swing.JLabel;

public enum Disaster {
    SMALL_METEOR(
            new Consumer<Engine>() {
        DisasterDialog dialog;
        Random rnd;

        @Override
        public void accept(Engine e) {
            if (rnd == null) {
                rnd = new Random();
            }
            List<Zone> buildings = e.getBuildingsList();
            if (buildings.isEmpty()) {
                return;
            }
            int fieldsize = e.getFieldsize();
            int index = rnd.nextInt(buildings.size());
            int row = buildings.get(index).getTopLeftY() / fieldsize;
            int col = buildings.get(index).getTopLeftX() / fieldsize;
            changeToBuildingPanelIfNeeded(row, col, e);
            e.destroyZone(row, col, fieldsize,
                    true);
            if (dialog == null) {
                dialog = new DisasterDialog(new JLabel("<html>"
                        + "Egy kis meteor csapódott be a városba." + "<br>"
                        + "Néhány épület megsemmisült, többen elköltöztek." + "<br>"
                        + "A boldogsági szint kis mértékben csökkent." + "</html>"),
                        e);
            }

            decreaseHappiness(e.getResidents(), -10);
            e.refreshHappiness();
            dialog.setActive();
        }
    }
    ),
    MEDIUM_METEOR(
            new Consumer<Engine>() {
        DisasterDialog dialog;
        Random rnd;

        @Override
        public void accept(Engine e) {
            if (rnd == null) {
                rnd = new Random();
            }
            List<Zone> buildings = e.getBuildingsList();
            if (buildings.isEmpty()) {
                return;
            }
            int fieldsize = e.getFieldsize();
            int index = rnd.nextInt(buildings.size());
            int row = buildings.get(index).getTopLeftY() / fieldsize;
            int col = buildings.get(index).getTopLeftX() / fieldsize;
            changeToBuildingPanelIfNeeded(row, col, e);
            e.destroyZone(row, col, fieldsize,
                    true);
            
            if (row + 1 < e.getHeight()) {
                changeToBuildingPanelIfNeeded(row + 1, col, e);
                e.destroyZone(row + 1, col, fieldsize, true);
            }

            if (col + 1 < e.getWidth()) {
                changeToBuildingPanelIfNeeded(row, col + 1, e);
                e.destroyZone(row, col + 1, fieldsize, true);
            }

            if (row > 0) {
                changeToBuildingPanelIfNeeded(row - 1, col, e);
                e.destroyZone(row - 1, col, fieldsize, true);
            }

            if (col > 0) {
                changeToBuildingPanelIfNeeded(row, col - 1, e);
                e.destroyZone(row, col - 1, fieldsize, true);
            }

            if (dialog == null) {
                dialog = new DisasterDialog(new JLabel("<html>"
                        + "Egy közepes meteor csapódott be a városba." + "<br>"
                        + "Néhány épület megsemmisült, többen elköltöztek." + "<br>"
                        + "A boldogsági szint közepes mértékben csökkent." + "</html>"),
                        e);
            }

            decreaseHappiness(e.getResidents(), -20);
            e.refreshHappiness();
            dialog.setActive();
        }
    }
    ), BIG_METEOR(
            new Consumer<Engine>() {
        DisasterDialog dialog;
        Random rnd;

        @Override
        public void accept(Engine e) {
            if (rnd == null) {
                rnd = new Random();
            }
            List<Zone> buildings = e.getBuildingsList();
            if (buildings.isEmpty()) {
                return;
            }
            int fieldsize = e.getFieldsize();
            int index = rnd.nextInt(buildings.size());
            int row = buildings.get(index).getTopLeftY() / fieldsize;
            int col = buildings.get(index).getTopLeftX() / fieldsize;
            changeToBuildingPanelIfNeeded(row, col, e);
            e.destroyZone(row, col, fieldsize, true);

            if (row + 1 < e.getHeight()) {
                changeToBuildingPanelIfNeeded(row + 1, col, e);
                e.destroyZone(row + 1, col, fieldsize, true);
                if (col + 1 < e.getWidth()) {
                    changeToBuildingPanelIfNeeded(row + 1, col + 1, e);
                    e.destroyZone(row + 1, col + 1, fieldsize, true);
                }
                if (col > 0) {
                    changeToBuildingPanelIfNeeded(row + 1, col - 1, e);
                    e.destroyZone(row + 1, col - 1, fieldsize, true);
                }
            }
            if (col + 1 < e.getWidth()) {
                changeToBuildingPanelIfNeeded(row, col + 1, e);
                e.destroyZone(row, col + 1, fieldsize, true);
            }

            if (row > 0) {
                changeToBuildingPanelIfNeeded(row - 1, col, e);
                e.destroyZone(row - 1, col, fieldsize, true);
                if (col + 1 < e.getWidth()) {
                    changeToBuildingPanelIfNeeded(row - 1, col + 1, e);
                    e.destroyZone(row - 1, col + 1, fieldsize, true);
                }
                if (col > 0) {
                    changeToBuildingPanelIfNeeded(row - 1, col - 1, e);
                    e.destroyZone(row - 1, col - 1, fieldsize, true);
                }
            }

            if (col > 0) {
                changeToBuildingPanelIfNeeded(row, col - 1, e);
                e.destroyZone(row, col - 1, fieldsize, true);
            }

            if (dialog == null) {
                dialog = new DisasterDialog(new JLabel("<html>"
                        + "Egy nagy meteor csapódott be a városba." + "<br>"
                        + "Néhány épület megsemmisült, többen elköltöztek." + "<br>"
                        + "A boldogsági szint nagy mértékben csökkent." + "</html>"),
                        e);
            }

            decreaseHappiness(e.getResidents(), -30);
            e.refreshHappiness();
            dialog.setActive();
        }
    }
    )
    , SMALL_TORNADO(
    new Consumer<Engine>() {
        DisasterDialog dialog;
        Random rnd;

        @Override
        public void accept(Engine e) {
            if (rnd == null) {
                rnd = new Random();
            }
            List<Zone> buildings = e.getBuildingsList();
            if (buildings.isEmpty()) {
                return;
            }
            int fieldsize = e.getFieldsize();
            int index = rnd.nextInt(buildings.size());
            int row = buildings.get(index).getTopLeftY() / fieldsize;
            int col = buildings.get(index).getTopLeftX() / fieldsize;
            changeToBuildingPanelIfNeeded(row, col, e);
            e.destroyZone(row, col, fieldsize, true);

            if(row+1 < e.getWidth()) {
                changeToBuildingPanelIfNeeded(row+1, col, e);
                e.destroyZone(row+1, col, fieldsize, true);
            } else {
                changeToBuildingPanelIfNeeded(row-1, col, e);
                e.destroyZone(row-1, col, fieldsize, true);
            }
            
            if (dialog == null) {
                dialog = new DisasterDialog(new JLabel("<html>"
                        + "Egy kis tornádó söpört végig a városon." + "<br>"
                        + "Néhány épület megsemmisült, többen elköltöztek." + "<br>"
                        + "A boldogsági szint kis mértékben csökkent." + "</html>"),
                        e);
            }

            decreaseHappiness(e.getResidents(), -10);
            e.refreshHappiness();
            dialog.setActive();
        }
    })
    , MEDIUM_TORNADO(
    new Consumer<Engine>() {
        DisasterDialog dialog;
        Random rnd;

        @Override
        public void accept(Engine e) {
            if (rnd == null) {
                rnd = new Random();
            }
            List<Zone> buildings = e.getBuildingsList();
            if (buildings.isEmpty()) {
                return;
            }
            int fieldsize = e.getFieldsize();
            int index = rnd.nextInt(buildings.size());
            int row = buildings.get(index).getTopLeftY() / fieldsize;
            int col = buildings.get(index).getTopLeftX() / fieldsize;
            changeToBuildingPanelIfNeeded(row, col, e);
            e.destroyZone(row, col, fieldsize, true);

            if (row+1 < e.getWidth()) {
                changeToBuildingPanelIfNeeded(row+1, col, e);
                e.destroyZone(row+1, col, fieldsize, true);
                
                if (row+2 < e.getWidth()) {
                    changeToBuildingPanelIfNeeded(row+2, col, e);
                    e.destroyZone(row+2, col, fieldsize, true);
                } else if (row > 0) {
                    changeToBuildingPanelIfNeeded(row-1, col, e);
                    e.destroyZone(row-1, col, fieldsize, true);
                }
            } else if (row > 0) {
                changeToBuildingPanelIfNeeded(row-1, col, e);
                e.destroyZone(row-1, col, fieldsize, true);
                
                if (row-1 > 0) {
                    changeToBuildingPanelIfNeeded(row-2, col, e);
                    e.destroyZone(row-2, col, fieldsize, true);
                } else if (row+1 < e.getWidth()) {
                    changeToBuildingPanelIfNeeded(row+1, col, e);
                    e.destroyZone(row+1, col, fieldsize, true);
                }
            }
            
            if (dialog == null) {
                dialog = new DisasterDialog(new JLabel("<html>"
                        + "Egy közepes tornádó söpört végig a városon." + "<br>"
                        + "Néhány épület megsemmisült, többen elköltöztek." + "<br>"
                        + "A boldogsági szint közepes mértékben csökkent." + "</html>"),
                        e);
            }

            decreaseHappiness(e.getResidents(), -20);
            e.refreshHappiness();
            dialog.setActive();
        }
    })
    , BIG_TORNADO(
    new Consumer<Engine>() {
        DisasterDialog dialog;
        Random rnd;

        @Override
        public void accept(Engine e) {
            if (rnd == null) {
                rnd = new Random();
            }
            List<Zone> buildings = e.getBuildingsList();
            if (buildings.isEmpty()) {
                return;
            }
            int fieldsize = e.getFieldsize();
            int index = rnd.nextInt(buildings.size());
            int row = buildings.get(index).getTopLeftY() / fieldsize;
            int col = buildings.get(index).getTopLeftX() / fieldsize;
            
            System.out.println(""+row);
            System.out.println(""+col);
            
            changeToBuildingPanelIfNeeded(row, col, e);
            e.destroyZone(row, col, fieldsize, true);

            if (row+1 < e.getWidth()) {
                changeToBuildingPanelIfNeeded(row+1, col, e);
                e.destroyZone(row+1, col, fieldsize, true);
                
                if (row+2 < e.getWidth()) {
                    changeToBuildingPanelIfNeeded(row+2, col, e);
                    e.destroyZone(row+2, col, fieldsize, true);
                    
                    if (row+3 < e.getWidth()) {
                        changeToBuildingPanelIfNeeded(row+3, col, e);
                        e.destroyZone(row+3, col, fieldsize, true);
                    } else if (row > 0) {
                        changeToBuildingPanelIfNeeded(row-1, col, e);
                        e.destroyZone(row-1, col, fieldsize, true);
                    }
                    
                } else if (row > 0) {
                    changeToBuildingPanelIfNeeded(row-1, col, e);
                    e.destroyZone(row-1, col, fieldsize, true);
                    
                    if (row-1 > 0) {
                        changeToBuildingPanelIfNeeded(row-2, col, e);
                        e.destroyZone(row-2, col, fieldsize, true);
                    }
                }
            } else if (row > 0) {
                changeToBuildingPanelIfNeeded(row-1, col, e);
                e.destroyZone(row-1, col, fieldsize, true);
                
                if (row-1 > 0) {
                    changeToBuildingPanelIfNeeded(row-2, col, e);
                    e.destroyZone(row-2, col, fieldsize, true);
                    
                    if (row-2 > 0) {
                        changeToBuildingPanelIfNeeded(row-3, col, e);
                        e.destroyZone(row-3, col, fieldsize, true);
                    }
                }
            }
            
            if (dialog == null) {
                dialog = new DisasterDialog(new JLabel("<html>"
                        + "Egy nagy tornádó söpört végig a városon." + "<br>"
                        + "Néhány épület megsemmisült, többen elköltöztek." + "<br>"
                        + "A boldogsági szint nagy mértékben csökkent." + "</html>"),
                        e);
            }

            decreaseHappiness(e.getResidents(), -30);
            e.refreshHappiness();
            dialog.setActive();
        }
    });;

    Consumer<Engine> action;

    private Disaster(Consumer<Engine> action) {
        this.action = action;
    }

    /**
     * Activates the action of the diasester
     * @param e - Engine, the game engine this disaster is attached to
     */
    public void activate(Engine e) {
        action.accept(e);
    }

    private static void decreaseHappiness(List<Person> residents, int value) {
        for (Person resident : residents) {
            resident.changeHappinessBy(value);
        }
    }
    
    private static void changeToBuildingPanelIfNeeded(int row, int col, Engine e) {
        if(e.isZoneSelected(row, col)) {
            e.unselectZone();
        }
    }
}
