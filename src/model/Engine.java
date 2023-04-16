package model;

import bigcity.EducationLevel;
import bigcity.HighSchool;
import bigcity.Industry;
import bigcity.Person;
import bigcity.Police;
import bigcity.Residence;
import bigcity.Road;
import bigcity.Service;
import bigcity.Stadium;
import bigcity.University;
import bigcity.Workplace;
import bigcity.Zone;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import res.Assets;
import view.BigCityJframe;

public class Engine {

    private Zone[][] grid;
    private ArrayList<Person> residents;

    private int width;
    private int height;

    private int money;
    private double combinedHappiness;
    private String date;
    private int timeSpeed;
    private int taxPercentage;
    private String name;

    private static CursorSignal cursorSignal = CursorSignal.SELECT;

    private BufferedImage img;

    public void setImg(BufferedImage img) {
        this.img = img;
    }

    private int fieldSize;

    private BigCityJframe bigCityJframe;

    public Engine(int width, int height, int fieldSize, BigCityJframe bigCityJframe) {
        this.width = width;
        this.height = height;
        this.fieldSize = fieldSize;
        this.money = 1000;
        this.bigCityJframe = bigCityJframe;
        grid = new Zone[height][width];
        for (int column = 0; column < width; column++) {
            for (int row = 0; row < height; row++) {
                grid[row][column] = null;
            }
        }
        residents = new ArrayList<>();
    }

    public Zone getCell(int row, int column) {
        return grid[row][column];
    }

    public static void setCursorSignal(CursorSignal signal) {
        cursorSignal = signal;
    }

    //Returns true if successfully built a zone.
    public boolean build(int rowStart, int columnStart, int fieldSize) {
        int rowEnd = rowStart + cursorSignal.getHeight() - 1;
        int columnEnd = columnStart + cursorSignal.getWidth() - 1;
        if (false == areaInsideGridAndFree(rowStart, rowEnd, columnStart, columnEnd)) {
            return false;
        }

        Zone zone = null;
        int topLeftX = columnStart * fieldSize;
        int topLeftY = rowStart * fieldSize;

        if (CursorSignal.POLICE == cursorSignal) {
            zone = new Police(topLeftX, topLeftY,
                    cursorSignal.getPriceL1());
        } else if (CursorSignal.STADIUM == cursorSignal) {
            zone = new Stadium(topLeftX, topLeftY,
                    cursorSignal.getPriceL1());
        } else if (CursorSignal.HIGH_SCHOOL == cursorSignal) {
            zone = new HighSchool(topLeftX, topLeftY,
                    cursorSignal.getPriceL1());
        } else if (CursorSignal.UNIVERSITY == cursorSignal) {
            zone = new University(topLeftX, topLeftY,
                    cursorSignal.getPriceL1());
        } else if (CursorSignal.ROAD == cursorSignal) {
            zone = new Road(topLeftX, topLeftY,
                    cursorSignal.getPriceL1());
        } else if (CursorSignal.RESIDENCE == cursorSignal) {
            zone = new Residence(topLeftX, topLeftY,
                    cursorSignal.getPriceL1());
        } else if (CursorSignal.INDUSTRY == cursorSignal) {
            zone = new Industry(topLeftX, topLeftY,
                    cursorSignal.getPriceL1());
        } else if (CursorSignal.SERVICE == cursorSignal) {
            zone = new Service(topLeftX, topLeftY,
                    cursorSignal.getPriceL1());
        }

        addMoney(-cursorSignal.getPriceL1());

        zone.setCursorSignal(cursorSignal);
        zone.setImg(img);

        for (int row = rowStart; row <= rowEnd; row++) {
            for (int column = columnStart; column <= columnEnd; column++) {
                grid[row][column] = zone;
            }
        }

        if (cursorSignal == CursorSignal.ROAD) {
            //new road
            refreshImgOfRoad(zone, rowStart, columnStart);
            //old roads
            refreshRoadImgsAround(rowStart, columnStart);
        }

        moveEveryOne();
        return true;
    }

    private void refreshRoadImgsAround(int row, int column) {
        //Up
        if (true == roadAndInsideGrid(row - 1, column)) {
            refreshImgOfRoad(grid[row - 1][column], row - 1,
                    column);
        }
        //Down
        if (true == roadAndInsideGrid(row + 1, column)) {
            refreshImgOfRoad(grid[row + 1][column], row + 1,
                    column);
        }
        //Right
        if (true == roadAndInsideGrid(row, column + 1)) {
            refreshImgOfRoad(grid[row][column + 1], row,
                    column + 1);
        }
        //Left
        if (true == roadAndInsideGrid(row, column - 1)) {
            refreshImgOfRoad(grid[row][column - 1], row,
                    column - 1);
        }
    }

    public CursorSignal getCursorSignal() {
        return cursorSignal;
    }

    //Returns true if the specified area if free and isn't outside of the grid.
    public boolean areaInsideGridAndFree(int rowStart, int rowEnd,
            int columnStart, int columnEnd) {
        for (int row = rowStart; row <= rowEnd; row++) {
            for (int column = columnStart; column <= columnEnd; column++) {
                if (row >= height || column >= width || row < 0 || column < 0) {
                    //Out of grid.
                    return false;
                }
                if (null != grid[row][column]) {
                    //At least one field isn't free.
                    return false;
                }
            }
        }
        return true;
    }

    //Returns true if successfully destroyed a zone.
    public boolean destroyZone(int argRow, int argColumn, int fieldSize) {
        Zone target = grid[argRow][argColumn];
        if (null == target) {
            return false;
        }

        int zoneLevel = 1;
        CursorSignal type;

        if (target instanceof Residence tmp) {
            zoneLevel = tmp.getLevel();
            type = CursorSignal.RESIDENCE;
        } else if (target instanceof Industry tmp) {
            zoneLevel = tmp.getLevel();
            type = CursorSignal.INDUSTRY;
        } else if (target instanceof Service tmp) {
            zoneLevel = tmp.getLevel();
            type = CursorSignal.SERVICE;
        } else if (target instanceof Road) {
            type = CursorSignal.ROAD;
        } else if (target instanceof Police) {
            type = CursorSignal.POLICE;
        } else if (target instanceof Stadium) {
            type = CursorSignal.STADIUM;
        } else if (target instanceof HighSchool) {
            type = CursorSignal.HIGH_SCHOOL;
        } else {
            type = CursorSignal.UNIVERSITY;
        }

        CursorSignal targetSignal = target.getCursorSignal();
        int rowStart = target.getTopLeftY() / fieldSize;
        int rowEnd = target.getTopLeftY() / fieldSize
                + target.getCursorSignal().getHeight() - 1;
        int columnStart = target.getTopLeftX() / fieldSize;
        int columnEnd = target.getTopLeftX() / fieldSize
                + target.getCursorSignal().getWidth() - 1;
        for (int row = rowStart; row <= rowEnd; row++) {
            for (int column = columnStart; column <= columnEnd; column++) {
                grid[row][column] = null;
            }
        }
        target.destroy();
        if (CursorSignal.ROAD == targetSignal) {
            refreshRoadImgsAround(argRow, argColumn);
        }

        int returnMoney = type.getPriceL1()
                + (zoneLevel > 1 ? type.getPriceL2() : 0)
                + (zoneLevel > 2 ? type.getPriceL3() : 0);
        addMoney(returnMoney / 2);

        moveEveryOne();
        return true;
    }

    private Boolean roadAndInsideGrid(int row, int column) {
        if (row < 0 || height <= row || column < 0 || width <= column) {
            return false;
        }
        if (null == grid[row][column]) {
            return false;
        }
        if (CursorSignal.ROAD == grid[row][column].getCursorSignal()) {
            return true;
        } else {
            return false;
        }
    }

    private void refreshImgOfRoad(Zone zone, int rowStart, int columnStart) {
        if (rowStart < 0 || height <= rowStart || columnStart < 0
                || width <= columnStart || null == zone || zone.getCursorSignal() != CursorSignal.ROAD) {
            return;
        }

        boolean roadAbove = roadAndInsideGrid(rowStart - 1, columnStart);
        boolean roadUnder = roadAndInsideGrid(rowStart + 1, columnStart);
        boolean roadOnRight = roadAndInsideGrid(rowStart, columnStart + 1);
        boolean roadOnLeft = roadAndInsideGrid(rowStart, columnStart - 1);

        int roadsAroundCount = 0;
        roadsAroundCount += roadAbove ? 1 : 0;
        roadsAroundCount += roadUnder ? 1 : 0;
        roadsAroundCount += roadOnRight ? 1 : 0;
        roadsAroundCount += roadOnLeft ? 1 : 0;
        if (0 == roadsAroundCount) {
            zone.setImg(Assets.roadNS);
            return;
        }
        if (4 == roadsAroundCount) {
            zone.setImg(Assets.roadNESW);
            return;
        }
        if (1 == roadsAroundCount) {
            if (roadAbove || roadUnder) {
                zone.setImg(Assets.roadNS);
                return;
            } else {
                zone.setImg(Assets.roadEW);
                return;
            }
        }
        if (3 == roadsAroundCount) {
            if (false == roadAbove) {
                zone.setImg(Assets.roadESW);
                return;
            }
            if (false == roadUnder) {
                zone.setImg(Assets.roadWNE);
                return;
            }
            if (false == roadOnLeft) {
                zone.setImg(Assets.roadNES);
                return;
            } else {
                //if (false == roadOnRight) {
                zone.setImg(Assets.roadSWN);
                return;
            }
        } else {
            //if (2 == roadsAroundCount)
            if (roadAbove && roadUnder) {
                zone.setImg(Assets.roadNS);
                return;
            }
            if (roadOnRight && roadOnLeft) {
                zone.setImg(Assets.roadEW);
                return;
            }
            if (roadAbove && roadOnRight) {
                zone.setImg(Assets.roadNE);
                return;
            }
            if (roadAbove && roadOnLeft) {
                zone.setImg(Assets.roadWN);
                return;
            }
            if (roadUnder && roadOnLeft) {
                zone.setImg(Assets.roadSW);
                return;
            }
            if (roadUnder && roadOnRight) {
                zone.setImg(Assets.roadES);
                return;
            }

        }
    }

    public void moveEveryOne() {

        residents.forEach(resident -> {
            resident.setHome(null);
            resident.setJob(null);
        });

        int sumResidenceCapacity = 0;
        for (int row = 0; row < height; row++) {
            for (int column = 0; column < width; column++) {
                if (null != grid[row][column]) {
                    if (grid[row][column] instanceof Residence) {
                        Residence residence = (Residence) grid[row][column];
                        sumResidenceCapacity += residence.getCapacity();
                        residence.clearResidents();
                    } else if (grid[row][column] instanceof Workplace) {
                        Workplace workplace = (Workplace) grid[row][column];
                        workplace.clearWorkers();
                    }
                }
            }
        }
        while (sumResidenceCapacity < residents.size()) {
            residents.remove(0);
        }
        // ------------------------------------------------------------------
        //Find all residences.
        //Find all industries and services connected to a residence. Store every 
        //connections. Store the distances. Sort according the distances.
        //Residences with no connection are stored separately from those with
        //connections.
        ArrayList<Residence> residences = findResidences();

        ArrayList<ResidenceWorkplaceDistance> distances = new ArrayList<>();
        ArrayList<Residence> residencesWithNoWorkplace = new ArrayList<>();

        residences.forEach(residence -> {
            HashSet<Zone> foundZones = new HashSet<>();
            foundZones.add(residence);
            LinkedList<ZoneDistancePair> zoneDistancePairs = new LinkedList<>();
            int foundWorkplacesCount = 0;
            zoneDistancePairs.addLast(new ZoneDistancePair(residence,
                    0));

            while (zoneDistancePairs.size() > 0) {
                ZoneDistancePair currentZoneDistancePair
                        = zoneDistancePairs.removeFirst();
                int row = currentZoneDistancePair.getZone().getTopLeftY()
                        / fieldSize;
                int column = currentZoneDistancePair.getZone().getTopLeftX()
                        / fieldSize;

                //up
                checkGridCellToFindConnection(row - 1, column, foundZones,
                        zoneDistancePairs, currentZoneDistancePair,
                        foundWorkplacesCount, distances, residence);
                //right
                checkGridCellToFindConnection(row, column + 1, foundZones,
                        zoneDistancePairs, currentZoneDistancePair,
                        foundWorkplacesCount, distances, residence);
                //down
                checkGridCellToFindConnection(row + 1, column, foundZones,
                        zoneDistancePairs, currentZoneDistancePair,
                        foundWorkplacesCount, distances, residence);
                //left
                checkGridCellToFindConnection(row, column - 1, foundZones,
                        zoneDistancePairs, currentZoneDistancePair,
                        foundWorkplacesCount, distances, residence);
            }

            if (0 == foundWorkplacesCount) {
                residencesWithNoWorkplace.add(residence);
            }
        });

        Collections.sort(distances);

        /*//For debug.
        System.out.println("----------------------------------------------");
        System.out.println("residences with no connections: ");
        residencesWithNoWorkplace.forEach(residence -> {
            System.out.print("row: " + residence.getTopLeftY() / fieldSize);
            System.out.println("; column: " + residence.getTopLeftX() / fieldSize);
        });
        System.out.println("residences with connections: ");
        distances.forEach(residenceWorkplaceDistance -> {
            System.out.print("residence: row: "
                    + residenceWorkplaceDistance.getResidence().getTopLeftY()
                    / fieldSize);
            System.out.println("; column: "
                    + residenceWorkplaceDistance.getResidence().getTopLeftX()
                    / fieldSize);
            System.out.print("workplace: row: "
                    + residenceWorkplaceDistance.getWorkplace().getTopLeftY()
                    / fieldSize);
            System.out.println("; column: "
                    + residenceWorkplaceDistance.getWorkplace().getTopLeftX()
                    / fieldSize);
            System.out.println("distance: " + residenceWorkplaceDistance
                    .getDistance());
        });
        System.out.println("----------------------------------------------");
         */
        //------------------------------------------------------------------
        //The new residents try to take the best places.
        residents.forEach(resident -> {
            residentTriesToMoveIn(resident, distances,
                    residencesWithNoWorkplace);
        });

    }

    private boolean residentTriesToMoveIn(Person resident,
            ArrayList<ResidenceWorkplaceDistance> distances,
            ArrayList<Residence> residencesWithNoWorkplace) {
        boolean movedIn = false;
        for (ResidenceWorkplaceDistance distance : distances) {
            Residence residence = distance.getResidence();
            Workplace workplace = distance.getWorkplace();
            int residenceSize = residence.getSize();
            int residenceCapacity = residence.getCapacity();
            int workplaceSize = workplace.getSize();
            int workplaceCapacity = workplace.getCapacity();
            if (residenceSize < residenceCapacity
                    && workplaceSize < workplaceCapacity) {
                residence.addPerson(resident);
                workplace.addPerson(resident);
                resident.setHome(residence);
                resident.setJob(workplace);
                movedIn = true;
                //Moved in.
                break;
            }
        }
        if (false == movedIn) {
            for (Residence residence : residencesWithNoWorkplace) {
                int residenceSize = residence.getSize();
                int residenceCapacity = residence.getCapacity();
                if (residenceSize < residenceCapacity) {
                    residence.addPerson(resident);
                    resident.setHome(residence);
                    movedIn = true;
                    //Moved in.
                    break;
                }
            }
        }
        //Residences with connections, but all connected workplaces are full
        //and there is still free place on the residential zone.
        if (false == movedIn) {
            for (ResidenceWorkplaceDistance distance : distances) {
                Residence residence = distance.getResidence();
                int residenceSize = residence.getSize();
                int residenceCapacity = residence.getCapacity();
                if (residenceSize < residenceCapacity) {
                    residence.addPerson(resident);
                    resident.setHome(residence);
                    movedIn = true;
                    //Moved in.
                    break;
                }
            }
        }
        return movedIn;
    }

    private int newResidentsCount() {
        int fixGroupOfPeopleCount = 10;
        float percentageOfNewResidents = 0.01f;
        int newResidentsCount = (int) Math.ceil(
                residents.size() * percentageOfNewResidents);
        if (residents.size() < fixGroupOfPeopleCount) {
            newResidentsCount += fixGroupOfPeopleCount - residents.size();
        }
        return newResidentsCount;
    }

    private ArrayList<Residence> findResidences() {
        ArrayList<Residence> residences = new ArrayList<>();
        for (int row = 0; row < height; row++) {
            for (int column = 0; column < width; column++) {
                if (null != grid[row][column]) {
                    if (grid[row][column] instanceof Residence) {
                        residences.add((Residence) grid[row][column]);
                    }
                }
            }
        }
        return residences;
    }

    private void checkGridCellToFindConnection(int row, int column,
            HashSet<Zone> foundZones,
            LinkedList<ZoneDistancePair> zoneDistancePairs,
            ZoneDistancePair currentZoneDistancePair,
            Integer foundWorkplacesCount,
            ArrayList<ResidenceWorkplaceDistance> distances,
            Residence residence) {
        Zone foundZone;
        if (0 <= row && row < height && 0 <= column && column < width) {
            if (null != grid[row][column]) {
                foundZone = grid[row][column];
                if (!foundZones.contains(foundZone)) {
                    foundZones.add(foundZone);
                    if (foundZone instanceof Road) {
                        zoneDistancePairs.addLast(
                                new ZoneDistancePair(foundZone,
                                        currentZoneDistancePair
                                                .getDistance() + 1));
                    } else if (foundZone instanceof Workplace) {
                        foundWorkplacesCount++;
                        distances.add(new ResidenceWorkplaceDistance(
                                residence, (Workplace) foundZone,
                                currentZoneDistancePair.getDistance()
                                + 1));
                    }
                }

            }
        }
    }

    public void dayPassed() {
        //TODO
        //------------------------------------------------------------------
        //~100 people move in immediately if possible. They leave only if there 
        //isn't enough residence.
        //The polpulation tries to increase by ~5% everyday.
        int newResidentsCount = newResidentsCount();
        //System.out.println("newResidentsCount: "+newResidentsCount);
        // ------------------------------------------------------------------
        //Find all residences.
        //Find all industries and services connected to a residence. Store every 
        //connections. Store the distances. Sort according the distances.
        //Residences with no connection are stored separately from those with
        //connections.
        ArrayList<Residence> residences = findResidences();

        ArrayList<ResidenceWorkplaceDistance> distances = new ArrayList<>();
        ArrayList<Residence> residencesWithNoWorkplace = new ArrayList<>();

        residences.forEach(residence -> {
            HashSet<Zone> foundZones = new HashSet<>();
            foundZones.add(residence);
            LinkedList<ZoneDistancePair> zoneDistancePairs = new LinkedList<>();
            Integer foundWorkplacesCount = 0;
            zoneDistancePairs.addLast(new ZoneDistancePair(residence,
                    0));

            while (zoneDistancePairs.size() > 0) {
                ZoneDistancePair currentZoneDistancePair
                        = zoneDistancePairs.removeFirst();
                int row = currentZoneDistancePair.getZone().getTopLeftY()
                        / fieldSize;
                int column = currentZoneDistancePair.getZone().getTopLeftX()
                        / fieldSize;
                //up
                checkGridCellToFindConnection(row - 1, column, foundZones,
                        zoneDistancePairs, currentZoneDistancePair,
                        foundWorkplacesCount, distances, residence);
                //right
                checkGridCellToFindConnection(row, column + 1, foundZones,
                        zoneDistancePairs, currentZoneDistancePair,
                        foundWorkplacesCount, distances, residence);
                //down
                checkGridCellToFindConnection(row + 1, column, foundZones,
                        zoneDistancePairs, currentZoneDistancePair,
                        foundWorkplacesCount, distances, residence);
                //left
                checkGridCellToFindConnection(row, column - 1, foundZones,
                        zoneDistancePairs, currentZoneDistancePair,
                        foundWorkplacesCount, distances, residence);
            }

            if (0 == foundWorkplacesCount) {
                residencesWithNoWorkplace.add(residence);
            }
        });

        Collections.sort(distances);

        /* //For debug.
        System.out.println("----------------------------------------------");
        System.out.println("residences with no connections: ");
        residencesWithNoWorkplace.forEach(residence -> {
            System.out.print("row: " + residence.getTopLeftY() / fieldSize);
            System.out.println("; column: " + residence.getTopLeftX() / fieldSize);
        });
        System.out.println("residences with connections: ");
        distances.forEach(residenceWorkplaceDistance -> {
            System.out.print("residence: row: "
                    + residenceWorkplaceDistance.getResidence().getTopLeftY()
                    / fieldSize);
            System.out.println("; column: "
                    + residenceWorkplaceDistance.getResidence().getTopLeftX()
                    / fieldSize);
            System.out.print("workplace: row: "
                    + residenceWorkplaceDistance.getWorkplace().getTopLeftY()
                    / fieldSize);
            System.out.println("; column: "
                    + residenceWorkplaceDistance.getWorkplace().getTopLeftX()
                    / fieldSize);
            System.out.println("distance: " + residenceWorkplaceDistance
                    .getDistance());
        });
        System.out.println("----------------------------------------------");
         */
        //------------------------------------------------------------------
        //The new residents try to take the best places.
        while (newResidentsCount > 0) {
            Person newPerson = new Person(
                    "Cool Name",
                    30,
                    100,
                    true,
                    EducationLevel.PRIMARY_SCHOOL,
                    null,
                    null
            );
            ///*
            boolean movedIn = residentTriesToMoveIn(newPerson, distances,
                    residencesWithNoWorkplace);
            if (false == movedIn) {
                //The city is full, there is no more residence with free place.
                break;
            } else {
                residents.add(newPerson);
            }

            newResidentsCount--;
        }
        //
        //
        //Calculated not in this function!
        //Destroying or building a zone causes to move people.
        //
        //------------------------------------------------------------------
        //Calculate the happiness of each resident. The happiness changes with a
        //calculated value everyday.
        //Calculate the average happiness.
        //------------------------------------------------------------------
        //Residents with low happiness move out. (<10%)
        //------------------------------------------------------------------
        //Pay the expenses. 
        //(high school -20$, university -30$, police -30$, stadium -$40)
        //Collect the taxes. Residents pay a fix amount (~1$) for the residence, and a
        //salary tax according their education level.
        //(+1$ primary school, +4$ high school, +8$ university) 
        //------------------------------------------------------------------
        //Increase education level.
        //One high school gives education to one resident a day. 
        //Same for the university.
        //The maximum amount of residents with high school and university 
        //education level depends on how many we have of these zones and how 
        //many people lives in the city.
        //(educated people count <= ~50% of people)
        //+1 High school = +30 capacity for people with high school education
        //+1 University  = +30 capacity for people with university education
        //------------------------------------------------------------------
        //Check whether the game is over or not. (average happiness < 20%)

        bigCityJframe.repaintStatPanelAndGrid();
    }

    public int getMoney() {
        return money;
    }

    public double getCombinedHappiness() {
        return combinedHappiness;
    }

    public String getDate() {
        return date;
    }

    public int getTimeSpeed() {
        return timeSpeed;
    }

    public int getTaxPercentage() {
        return taxPercentage;
    }

    public String getName() {
        return name;
    }

    public int addMoney(int value) {
        money += value;
        return money;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setTaxPercentage(int taxPercentage) {
        this.taxPercentage = taxPercentage;
    }

    public int calculateHappieness() {
        //TODO
        return 0;
    }

    public void collectTax() {
        //TODO
    }

    public ArrayList<Person> getResidents() {
        return residents;
    }

}
