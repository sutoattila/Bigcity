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
import java.nio.charset.Charset;
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

    private final int fixGroupOfPeopleCount = 10;

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

        //TODO (Mate's issue)
        //Conflictual destruction.
        //If private zone has been destroyed, decrease the people's happiness
        //who worked or lived there.
        //If a road has been destroyed find those people who can't get to their
        //workplace on road and decrease their happiness.
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
                resident.setHomeJobDistance(distance.getDistance());
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
                    resident.setHomeJobDistance(-1);
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
                    resident.setHomeJobDistance(-1);
                    movedIn = true;
                    //Moved in.
                    break;
                }
            }
        }
        return movedIn;
    }

    private int newResidentsCount() {

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
        //------------------------------------------------------------------
        //~10 people move in immediately if possible. They leave only if there 
        //isn't enough residence.
        //The polpulation tries to increase by ~1% everyday.
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
                    "Name",
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
        //------------------------------------------------------------------
        //Calculate the happiness of each resident. The happiness changes with a
        //calculated value everyday.
        //has job: +1
        //doesn't have job: -1
        //Person objects should store the distance between house and workplace.
        //distance between house and workplace: (distance/5==0) [1;4]-> +1,
        //[5,...[ -> -1*2^n where n=distance/5
        //Zone happiness change according range. (industry, stadium)
        //Happiness change according saturation(home, job). If capacity==size
        //and there is no police nearby, then -3.
        //Calculate the average happiness. Write it to the top panel.

        //has job: +1
        //doesn't have job: -1
        //Person objects should store the distance between house and workplace.
        //distance between house and workplace: (distance/5==0) [1;4]-> +1,
        //[5,...[ -> -1*2^n where n=distance/5
        residents.forEach(resident -> {
            if (null == resident.getJob()) {
                resident.changeHappinessBy(-1);
            } else {
                resident.changeHappinessBy(1);
                //distance between house and workplace:
                //  (distance/5==0) [0;4]-> +1,
                //  [5,...[ -> -1*2^n where n=distance/5
                int happinessChangeAccordingDistance
                        = resident.getHomeJobDistance() / 5 == 0
                        ? 1
                        : -1 * (int) Math.pow(2,
                                resident.getHomeJobDistance() / 5);
                resident.changeHappinessBy(happinessChangeAccordingDistance);
            }
        });
        //Find all industries and stadiums. Change happiness inside the range.
        findAllIndustries().forEach(industry -> {
            findCoordsInsideRange(industry, Industry.range)
                    .forEach(coords -> {
                        Zone zone = grid[coords.getY()][coords.getX()];
                        if (null != zone) {
                            if (zone instanceof Residence) {
                                ((Residence) zone).getResidents().forEach(
                                        resident -> {
                                            resident.changeHappinessBy(-1);
                                        });
                            } else if (zone instanceof Workplace) {
                                ((Workplace) zone).getWorkers().forEach(
                                        worker -> {
                                            worker.changeHappinessBy(-1);
                                        });
                            }
                        }
                    });
        });
        /*//debug
        findAllStadiums().forEach(stadium
                -> System.out.println("count coords around: "
                        + findCoordsInsideRange(stadium,
                                Stadium.range).size()));
         */
        findAllStadiums().forEach(stadium -> {
            //System.out.println("---------------------------------");
            findCoordsInsideRange(stadium, Stadium.range)
                    .forEach(coords -> {
                        /*
                        System.out.println("x: " + coords.getX() + ", y: "
                                + coords.getY() + ",step: " + coords.getStep()
                        );
                         */
                        Zone zone = grid[coords.getY()][coords.getX()];
                        if (null != zone) {
                            if (zone instanceof Residence) {
                                ((Residence) zone).getResidents().forEach(
                                        resident -> {
                                            resident.changeHappinessBy(4);
                                        });
                            } else if (zone instanceof Workplace) {
                                ((Workplace) zone).getWorkers().forEach(
                                        worker -> {
                                            worker.changeHappinessBy(4);
                                        });
                            }
                        }
                    });
            //System.out.println("---------------------------------");
        });

        ///Happiness change according saturation(home, job). If capacity==size
        //and there is no police nearby, then -3.
        ArrayList<Coords> coordsInPoliceRange = new ArrayList<>();
        for (int row = 0; row < height; row++) {
            for (int column = 0; column < width; column++) {
                Zone zone = grid[row][column];
                if (null != zone) {
                    if (zone instanceof Police) {
                        for (Coords coords
                                : findCoordsInsideRange(zone,
                                        Police.range)) {
                            boolean alreadyIn = false;
                            for (Coords coordsInRange : coordsInPoliceRange) {
                                if (coordsInRange.getX() == coords.getX()
                                        && coordsInRange.getY()
                                        == coords.getY()) {
                                    alreadyIn = true;
                                    break;
                                }
                            }
                            if (false == alreadyIn) {
                                coordsInPoliceRange.add(coords);
                            }
                        }
                    }
                }
            }
        }
        ArrayList<Coords> coordsNotInPoliceRange = new ArrayList<>();
        for (int row = 0; row < height; row++) {
            for (int column = 0; column < width; column++) {
                boolean coordsAffectedByPolice = false;
                for (Coords coords : coordsInPoliceRange) {
                    if (column == coords.getX() && row == coords.getY()) {
                        coordsAffectedByPolice = true;
                        break;
                    }
                }
                if (false == coordsAffectedByPolice) {
                    coordsNotInPoliceRange.add(new Coords(column, row));
                }
            }
        }
        coordsNotInPoliceRange.forEach(coords -> {
            Zone zone = grid[coords.getY()][coords.getX()];
            if (null != zone) {
                if (zone instanceof Residence) {
                    Residence residence = (Residence) zone;
                    residence.getResidents().forEach(resident -> {
                        if (residence.getCapacity() == residence.getSize()) {
                            resident.changeHappinessBy(-3);
                        }
                    });
                } else if (zone instanceof Workplace) {
                    Workplace workplace = (Workplace) zone;
                    workplace.getWorkers().forEach(worker -> {
                        if (workplace.getCapacity() == workplace.getSize()) {
                            worker.changeHappinessBy(-3);
                        }
                    });
                }
            }
        });

        //Big difference between industry and service workers causes negative 
        //happiness.
        int numberOfIndustryWorkers = 0;
        int numberOfServiceWorkers = 0;
        for (Person resident : residents) {
            if (null != resident.getJob()) {
                if (resident.getJob() instanceof Service) {
                    numberOfServiceWorkers++;
                } else if (resident.getJob() instanceof Industry) {
                    numberOfIndustryWorkers++;
                }
            }
        }
        /*//debug
        System.out.println("----------------------------------------------");
        System.out.println("(double) Math.abs("
                + "numberOfIndustryWorkers - numberOfServiceWorkers): "
                + (double) Math.abs(
                        numberOfIndustryWorkers - numberOfServiceWorkers));
        System.out.println("numberOfIndustryWorkers + numberOfServiceWorkers: "
                + (numberOfIndustryWorkers + numberOfServiceWorkers));
        System.out.println("(double) Math.abs("
                + "numberOfIndustryWorkers - numberOfServiceWorkers)"
                + " / (numberOfIndustryWorkers + numberOfServiceWorkers): " + (double) Math.abs(
                        numberOfIndustryWorkers - numberOfServiceWorkers)
                / (numberOfIndustryWorkers + numberOfServiceWorkers));
         */
        if (0.5 < (double) Math.abs(
                numberOfIndustryWorkers - numberOfServiceWorkers)
                / (numberOfIndustryWorkers + numberOfServiceWorkers)) {
            //System.out.println("The difference is greater than 50%.");
            residents.forEach(resident -> resident.changeHappinessBy(-1));
        }/* else {
            System.out.println("The difference is less than 50%.");
        }*/
        //System.out.println("----------------------------------------------");


        //------------------------------------------------------------------
        //Residents with low happiness move out. (<10%)
        //The starter group won't move out unless there is not enough place.
        ArrayList<Person> peopleMoveOut = new ArrayList<>();

        residents.stream().forEach(resident -> {
            if (resident.getHappiness() < 10) {
                peopleMoveOut.add(resident);
            }
        });
        for (Person resident : peopleMoveOut) {
            if (residents.size() <= fixGroupOfPeopleCount) {
                break;
            }
            ((Residence) resident.getHome()).getResidents().remove(resident);
            if (null != resident.getJob()) {
                ((Workplace) resident.getJob()).getWorkers().remove(resident);
            }
            residents.remove(resident);
        }

        //Set maximum and minimum happiness.
        residents.forEach(resident -> {
            if (100 < resident.getHappiness()) {
                resident.setHappiness(100);
            } else if (resident.getHappiness() < 0) {
                resident.setHappiness(0);
            }
        });

        //------------------------------------------------------------------
        //Pay the expenses. 
        //(high school -20$, university -30$, police -30$, stadium -$40)
        HashSet<Zone> zonesCostMoney = new HashSet<>();
        for (int row = 0; row < height; row++) {
            for (int column = 0; column < width; column++) {
                if (null != grid[row][column]
                        && !zonesCostMoney.contains(grid[row][column])) {
                    if (grid[row][column] instanceof HighSchool) {
                        money -= 20;
                        zonesCostMoney.add(grid[row][column]);
                    } else if (grid[row][column] instanceof University) {
                        money -= 30;
                        zonesCostMoney.add(grid[row][column]);
                    } else if (grid[row][column] instanceof Police) {
                        money -= 30;
                        zonesCostMoney.add(grid[row][column]);
                    } else if (grid[row][column] instanceof Stadium) {
                        money -= 40;
                        zonesCostMoney.add(grid[row][column]);
                    }
                }
            }
        }
        //TODO (Norbi's issue)
        //Collect the taxes. Residents pay a fix amount (~1$) for the residence,
        //and a salary tax according their education level.
        //(+1$ primary school, +4$ high school, +8$ university) 
        //------------------------------------------------------------------
        //TODO (Norbi's issue)
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
        int maxSumHappiness = residents.size() * 100;
        int currentSumHappiness = 0;
        for (Person person : residents) {
            currentSumHappiness += person.getHappiness();
        }
        int averageHappiness = Math.round(
                (float) currentSumHappiness / maxSumHappiness * 100);
        if (0 == residents.size()) {
            averageHappiness = 100;
        }
        bigCityJframe.getHappy().setText(averageHappiness + "%");

        checkGameOver(averageHappiness);
        //------------------------------------------------------------------
        //Increase age. Old people die and changed to new people with
        //low education level.
        for (Person resident : residents) {
            resident.growOlder();
            if (70 == resident.getAge()) {
                resident.die();
            }
        }
        //------------------------------------------------------------------
        bigCityJframe.repaintStatPanelAndGrid();
    }

    private ArrayList<Industry> findAllIndustries() {
        ArrayList<Industry> industries = new ArrayList<>();
        for (int row = 0; row < height; row++) {
            for (int column = 0; column < width; column++) {
                if (null != grid[row][column]) {
                    if (grid[row][column] instanceof Industry) {
                        industries.add((Industry) grid[row][column]);
                    }
                }
            }
        }
        return industries;
    }

    private ArrayList<Stadium> findAllStadiums() {
        ArrayList<Stadium> stadiums = new ArrayList<>();
        for (int row = 0; row < height; row++) {
            for (int column = 0; column < width; column++) {
                Zone zone = grid[row][column];
                if (null != zone) {
                    if (zone instanceof Stadium) {
                        boolean alreadyIn = false;
                        for (Stadium stadium : stadiums) {
                            if (stadium == zone) {
                                alreadyIn = true;
                                break;
                            }
                        }
                        if (false == alreadyIn) {
                            stadiums.add((Stadium) grid[row][column]);
                        }
                    }
                }
            }
        }
        return stadiums;
    }

    public ArrayList<Coords> findCoordsInsideRange(Zone centerZone, int range) {
        LinkedList<Coords> coordsInRange = new LinkedList<>();
        ArrayList<Coords> processedCoords = new ArrayList<>();

        int x = centerZone.getTopLeftX() / fieldSize;
        int y = centerZone.getTopLeftY() / fieldSize;

        Coords coords = new Coords(x, y);
        coords.setStep(0);
        coordsInRange.addLast(coords);
        processedCoords.add(coords);

        //The center zone is a 2x2 zone if it is a stadium.
        if (centerZone instanceof Stadium) {
            coords = new Coords(x + 1, y);
            coords.setStep(0);
            coordsInRange.addLast(coords);
            processedCoords.add(coords);

            coords = new Coords(x + 1, y + 1);
            coords.setStep(0);
            coordsInRange.addLast(coords);
            processedCoords.add(coords);

            coords = new Coords(x, y + 1);
            coords.setStep(0);
            coordsInRange.addLast(coords);
            processedCoords.add(coords);
        }

        ArrayList<Coords> result = new ArrayList<>();

        while (0 != coordsInRange.size()) {
            coords = coordsInRange.removeFirst();
            x = coords.getX();
            y = coords.getY();
            int nextStep = coords.getStep() + 1;
            //up
            Coords nextCoords = getNextCoords(x, y - 1, nextStep, range,
                    processedCoords);
            if (null != nextCoords) {
                processedCoords.add(nextCoords);
                result.add(nextCoords);
                coordsInRange.addLast(nextCoords);
            }
            //right
            nextCoords = getNextCoords(x + 1, y, nextStep, range,
                    processedCoords);
            if (null != nextCoords) {
                processedCoords.add(nextCoords);
                result.add(nextCoords);
                coordsInRange.addLast(nextCoords);
            }
            //down
            nextCoords = getNextCoords(x, y + 1, nextStep, range,
                    processedCoords);
            if (null != nextCoords) {
                processedCoords.add(nextCoords);
                result.add(nextCoords);
                coordsInRange.addLast(nextCoords);
            }
            //left
            nextCoords = getNextCoords(x - 1, y, nextStep, range,
                    processedCoords);
            if (null != nextCoords) {
                processedCoords.add(nextCoords);
                result.add(nextCoords);
                coordsInRange.addLast(nextCoords);
            }
        }

        return result;
    }

    private Coords getNextCoords(int x, int y, int newStep, int range,
            ArrayList<Coords> processedCoords) {
        if (0 <= x && x < width && 0 <= y && y < height && newStep <= range) {
            for (Coords processedCoord : processedCoords) {
                //already found coords
                if (processedCoord.getX() == x && processedCoord.getY() == y) {
                    return null;
                }
            }
            Coords coords = new Coords(x, y);
            coords.setStep(newStep);
            return coords;
        } else {
            return null;
        }
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

    private void checkGameOver(int averageHappiness) {
        if (averageHappiness < 20) {
            System.out.println("Game over! "
                    + "The average happiness is below 20%");
            gameOver();
        }
    }

    private void gameOver() {
        //TODO
    }

}
