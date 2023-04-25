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
            e.destroyZone(row, col, fieldsize,
                    true);
            if (dialog == null) {
                dialog = new DisasterDialog(new JLabel("<html>"
                        + "Egy kis meteor csapódott be a városba." + "<br>"
                        + "Néhány épület megsemmisült, többen elköltöztek." + "<br>"
                        + "A boldogsági szint kis mértékben csökkent." + "</html>"));
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
            e.destroyZone(row, col, fieldsize,
                    true);

            if (row + 1 < e.getHeight()) {
                e.destroyZone(row + 1, col, fieldsize, true);
            }

            if (col + 1 < e.getWidth()) {
                e.destroyZone(row, col + 1, fieldsize, true);
            }

            if (row > 0) {
                e.destroyZone(row - 1, col, fieldsize, true);
            }

            if (col > 0) {
                e.destroyZone(row, col - 1, fieldsize, true);
            }

            if (dialog == null) {
                dialog = new DisasterDialog(new JLabel("<html>"
                        + "Egy közepes meteor csapódott be a városba." + "<br>"
                        + "Néhány épület megsemmisült, többen elköltöztek." + "<br>"
                        + "A boldogsági szint közepes mértékben csökkent." + "</html>"));
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
            e.destroyZone(row, col, fieldsize, true);

            if (row + 1 < e.getHeight()) {
                e.destroyZone(row + 1, col, fieldsize, true);
                if (col + 1 < e.getWidth()) {
                    e.destroyZone(row + 1, col + 1, fieldsize, true);
                }
                if (col > 0) {
                    e.destroyZone(row + 1, col - 1, fieldsize, true);
                }
            }
            if (col + 1 < e.getWidth()) {
                e.destroyZone(row, col + 1, fieldsize, true);
            }

            if (row > 0) {
                e.destroyZone(row - 1, col, fieldsize, true);
                if (col + 1 < e.getWidth()) {
                    e.destroyZone(row - 1, col + 1, fieldsize, true);
                }
                if (col > 0) {
                    e.destroyZone(row - 1, col - 1, fieldsize, true);
                }
            }

            if (col > 0) {
                e.destroyZone(row, col - 1, fieldsize, true);
            }

            if (dialog == null) {
                dialog = new DisasterDialog(new JLabel("<html>"
                        + "Egy nagy meteor csapódott be a városba." + "<br>"
                        + "Néhány épület megsemmisült, többen elköltöztek." + "<br>"
                        + "A boldogsági szint nagy mértékben csökkent." + "</html>"));
            }

            decreaseHappiness(e.getResidents(), -30);
            e.refreshHappiness();
            dialog.setActive();
        }
    }
    );
    //, SMALL_TORNADO;
    //, MEDIUM_TORNADO;
    //, BIG_TORNADO;

    Consumer<Engine> action;

    private Disaster(Consumer<Engine> action) {
        this.action = action;
    }

    public void activate(Engine e) {
        action.accept(e);
    }

    private static void decreaseHappiness(List<Person> residents, int value) {
        for (Person resident : residents) {
            resident.changeHappinessBy(value);
        }
    }
}
