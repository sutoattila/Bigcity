/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package model;
import bigcity.Person;
import bigcity.Residence;
import bigcity.Zone;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import view.BigCityJframe;

/**
 *
 * @author Sütő Attila
 */
public class EngineTest {
    
    /**
     * Test of getCell method, of class Engine.
     */
    @Test
    public void testGetCell() {
        System.out.println("getCell");
        int row = 0;
        int column = 0;
        BigCityJframe bigcityjframe  = new BigCityJframe("bigcity", false);
        Engine instance = new Engine(10, 10, bigcityjframe.getFieldSize(), bigcityjframe);
        Engine.setCursorSignal(CursorSignal.RESIDENCE);
        instance.build(0, 0, 1, false);
        Residence expResult = new Residence(0,0,1);
        Zone result = instance.getCell(row, column);
        assertEquals(expResult, result);
        

    }

    /**
     * Test of setCursorSignal method, of class Engine.
     */
    @Test
    public void testSetCursorSignal() {
        System.out.println("setCursorSignal");
        CursorSignal signal = null;
        Engine.setCursorSignal(signal);

    }

    /**
     * Test of build method, of class Engine.
     */
    @Test
    public void testBuild() {
        System.out.println("build");
        int rowStart = 0;
        int columnStart = 0;
        int fieldSize = 0;
        boolean load = false;
        Engine instance = null;
        boolean expResult = false;
        boolean result = instance.build(rowStart, columnStart, fieldSize, load);
        assertEquals(expResult, result);

    }

    /**
     * Test of getCursorSignal method, of class Engine.
     */
    @Test
    public void testGetCursorSignal() {
        System.out.println("getCursorSignal");
        Engine instance = null;
        CursorSignal expResult = null;
        CursorSignal result = instance.getCursorSignal();
        assertEquals(expResult, result);

    }

    /**
     * Test of areaInsideGridAndFree method, of class Engine.
     */
    @Test
    public void testAreaInsideGridAndFree() {
        System.out.println("areaInsideGridAndFree");
        int rowStart = 0;
        int rowEnd = 0;
        int columnStart = 0;
        int columnEnd = 0;
        Engine instance = null;
        boolean expResult = false;
        boolean result = instance.areaInsideGridAndFree(rowStart, rowEnd, columnStart, columnEnd);
        assertEquals(expResult, result);

    }

    /**
     * Test of destroyZone method, of class Engine.
     */
    @Test
    public void testDestroyZone() {
        System.out.println("destroyZone");
        int argRow = 0;
        int argColumn = 0;
        int fieldSize = 0;
        boolean disasterHappened = false;
        Engine instance = null;
        boolean expResult = false;
        boolean result = instance.destroyZone(argRow, argColumn, fieldSize, disasterHappened);
        assertEquals(expResult, result);

    }

    /**
     * Test of moveEveryOne method, of class Engine.
     */
    @Test
    public void testMoveEveryOne() {
        System.out.println("moveEveryOne");
        Engine instance = null;
        instance.moveEveryOne();

    }

    /**
     * Test of dayPassed method, of class Engine.
     */
    @Test
    public void testDayPassed() {
        System.out.println("dayPassed");
        Engine instance = null;
        instance.dayPassed();

    }

    /**
     * Test of findCoordsInsideRange method, of class Engine.
     */
    @Test
    public void testFindCoordsInsideRange() {
        System.out.println("findCoordsInsideRange");
        Zone centerZone = null;
        int range = 0;
        Engine instance = null;
        ArrayList<Coords> expResult = null;
        ArrayList<Coords> result = instance.findCoordsInsideRange(centerZone, range);
        assertEquals(expResult, result);

    }

    /**
     * Test of getMoney method, of class Engine.
     */
    @Test
    public void testGetMoney() {
        System.out.println("getMoney");
        Engine instance = null;
        int expResult = 0;
        int result = instance.getMoney();
        assertEquals(expResult, result);

    }

    /**
     * Test of getCombinedHappiness method, of class Engine.
     */
    @Test
    public void testGetCombinedHappiness() {
        System.out.println("getCombinedHappiness");
        Engine instance = null;
        double expResult = 0.0;
        double result = instance.getCombinedHappiness();
        assertEquals(expResult, result, 0);

    }

    /**
     * Test of getDate method, of class Engine.
     */
    @Test
    public void testGetDate() {
        System.out.println("getDate");
        Engine instance = null;
        String expResult = "";
        String result = instance.getDate();
        assertEquals(expResult, result);

    }

    /**
     * Test of getTimeSpeed method, of class Engine.
     */
    @Test
    public void testGetTimeSpeed() {
        System.out.println("getTimeSpeed");
        Engine instance = null;
        int expResult = 0;
        int result = instance.getTimeSpeed();
        assertEquals(expResult, result);

    }

    /**
     * Test of getTaxPercentage method, of class Engine.
     */
    @Test
    public void testGetTaxPercentage() {
        System.out.println("getTaxPercentage");
        Engine instance = null;
        int expResult = 0;
        int result = instance.getTaxPercentage();
        assertEquals(expResult, result);

    }

    /**
     * Test of getName method, of class Engine.
     */
    @Test
    public void testGetName() {
        System.out.println("getName");
        Engine instance = null;
        String expResult = "";
        String result = instance.getName();
        assertEquals(expResult, result);

    }

    /**
     * Test of addMoney method, of class Engine.
     */
    @Test
    public void testAddMoney() {
        System.out.println("addMoney");
        int value = 0;
        Engine instance = null;
        int expResult = 0;
        int result = instance.addMoney(value);
        assertEquals(expResult, result);

    }

    /**
     * Test of setDate method, of class Engine.
     */
    @Test
    public void testSetDate() {
        System.out.println("setDate");
        String date = "";
        Engine instance = null;
        instance.setDate(date);

    }

    /**
     * Test of setTaxPercentage method, of class Engine.
     */
    @Test
    public void testSetTaxPercentage() {
        System.out.println("setTaxPercentage");
        int taxPercentage = 0;
        Engine instance = null;
        instance.setTaxPercentage(taxPercentage);

    }

    /**
     * Test of calculateHappieness method, of class Engine.
     */
    @Test
    public void testCalculateHappieness() {
        System.out.println("calculateHappieness");
        Engine instance = null;
        double expResult = 0.0;
        double result = instance.calculateHappieness();
        assertEquals(expResult, result, 0);

    }

    /**
     * Test of collectTax method, of class Engine.
     */
    @Test
    public void testCollectTax() {
        System.out.println("collectTax");
        Engine instance = null;
        instance.collectTax();

    }

    /**
     * Test of getResidents method, of class Engine.
     */
    @Test
    public void testGetResidents() {
        System.out.println("getResidents");
        Engine instance = null;
        ArrayList<Person> expResult = null;
        ArrayList<Person> result = instance.getResidents();
        assertEquals(expResult, result);

    }

    /**
     * Test of educatePeople method, of class Engine.
     */
    @Test
    public void testEducatePeople() {
        System.out.println("educatePeople");
        Engine instance = null;
        instance.educatePeople();

    }

    /**
     * Test of findResidencesOnRoad method, of class Engine.
     */
    @Test
    public void testFindResidencesOnRoad() {
        System.out.println("findResidencesOnRoad");
        int row = 0;
        int col = 0;
        int fieldWidth = 0;
        int fieldHeight = 0;
        Engine instance = null;
        Set<Residence> expResult = null;
        Set<Residence> result = instance.findResidencesOnRoad(row, col, fieldWidth, fieldHeight);
        assertEquals(expResult, result);

    }

    /**
     * Test of getWidth method, of class Engine.
     */
    @Test
    public void testGetWidth() {
        System.out.println("getWidth");
        Engine instance = null;
        int expResult = 0;
        int result = instance.getWidth();
        assertEquals(expResult, result);

    }

    /**
     * Test of getHeight method, of class Engine.
     */
    @Test
    public void testGetHeight() {
        System.out.println("getHeight");
        Engine instance = null;
        int expResult = 0;
        int result = instance.getHeight();
        assertEquals(expResult, result);

    }

    /**
     * Test of getFieldsize method, of class Engine.
     */
    @Test
    public void testGetFieldsize() {
        System.out.println("getFieldsize");
        Engine instance = null;
        int expResult = 0;
        int result = instance.getFieldsize();
        assertEquals(expResult, result);

    }

    /**
     * Test of getBuildingsList method, of class Engine.
     */
    @Test
    public void testGetBuildingsList() {
        System.out.println("getBuildingsList");
        Engine instance = null;
        List<Zone> expResult = null;
        List<Zone> result = instance.getBuildingsList();
        assertEquals(expResult, result);

    }

    /**
     * Test of refreshHappiness method, of class Engine.
     */
    @Test
    public void testRefreshHappiness() {
        System.out.println("refreshHappiness");
        Engine instance = null;
        instance.refreshHappiness();

    }

    /**
     * Test of makeDisaster method, of class Engine.
     */
    @Test
    public void testMakeDisaster() {
        System.out.println("makeDisaster");
        Engine instance = null;
        instance.makeDisaster();

    }

    /**
     * Test of isZoneSelected method, of class Engine.
     */
    @Test
    public void testIsZoneSelected() {
        System.out.println("isZoneSelected");
        int row = 0;
        int col = 0;
        Engine instance = null;
        boolean expResult = false;
        boolean result = instance.isZoneSelected(row, col);
        assertEquals(expResult, result);

    }

    /**
     * Test of unselectZone method, of class Engine.
     */
    @Test
    public void testUnselectZone() {
        System.out.println("unselectZone");
        Engine instance = null;
        instance.unselectZone();

    }

    /**
     * Test of saveGame method, of class Engine.
     */
    @Test
    public void testSaveGame() {
        System.out.println("saveGame");
        Engine instance = null;
        instance.saveGame();

    }



    
}
