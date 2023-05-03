package model;
import bigcity.Residence;
import java.util.ArrayList;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import model.Engine;


public class EngineTest {
    
    /**
     * Test of build method, of class Engine.
        //Private zones.----------------
        POLICE(1, 1, 300, 0, 0),
        STADIUM(2, 2, 300, 0, 0),
        HIGH_SCHOOL(2, 1, 300, 0, 0),
        UNIVERSITY(2, 2, 500, 0, 0),
        ROAD(1, 1, 50, 0, 0),
        //Public zones.-----------------
        RESIDENCE(1, 1, 50, 100, 300),
        INDUSTRY(1, 1, 100, 300, 1000),
        SERVICE(1, 1, 50, 100, 300),
     */

    //PUBLIC ZONES
    @Test
    public void testBuildResidence() {
        Engine engine = new Engine(10, 10, 1, "bigcity");
        Engine.setCursorSignal(CursorSignal.RESIDENCE);
        //if zone is empty
        if(engine.areaInsideGridAndFree(0,0,0,0)){
            //build should return true
            assertTrue(engine.buildForTesting(0, 0, 1, false));
            //the zone where we built should be not null
            assertTrue(engine.getCell(0, 0)!= null);
            //the zone where we built should be the specified building 
            assertEquals(engine.getCell(0, 0).getCursorSignal(),CursorSignal.RESIDENCE);
            //the built zone should be added to the buildings list of engine
            assertTrue(!engine.getBuildingsList().isEmpty());
            //the buildings list of engine should contain the built element
            assertEquals(engine.getBuildingsList().get(0).getCursorSignal(),CursorSignal.RESIDENCE);
        }
        //if zone is not empty
        else
            //build should return false
            assertFalse(engine.buildForTesting(0, 0, 1, false));
    }
    @Test
    public void testBuildIndustry() {
        Engine engine = new Engine(10, 10, 1, "bigcity");
        Engine.setCursorSignal(CursorSignal.INDUSTRY);
        //if zone is empty
        if(engine.areaInsideGridAndFree(0,0,0,0)){
            //build should return true
            assertTrue(engine.buildForTesting(0, 0, 1, false));
            //the zone where we built should be not null
            assertTrue(engine.getCell(0, 0)!= null);
            //the zone where we built should be the specified building 
            assertEquals(engine.getCell(0, 0).getCursorSignal(),CursorSignal.INDUSTRY);
            //the built zone should be added to the buildings list of engine
            assertTrue(!engine.getBuildingsList().isEmpty());
            //the buildings list of engine should contain the built element
            assertEquals(engine.getBuildingsList().get(0).getCursorSignal(),CursorSignal.INDUSTRY);
        }
        //if zone is not empty
        else
            //build should return false
            assertFalse(engine.buildForTesting(0, 0, 1, false));
    }
    @Test
    public void testBuildService() {
        Engine engine = new Engine(10, 10, 1, "bigcity");
        Engine.setCursorSignal(CursorSignal.SERVICE);
        //if zone is empty
        if(engine.areaInsideGridAndFree(0,0,0,0)){
            //build should return true
            assertTrue(engine.buildForTesting(0, 0, 1, false));
            //the zone where we built should be not null
            assertTrue(engine.getCell(0, 0)!= null);
            //the zone where we built should be the specified building 
            assertEquals(engine.getCell(0, 0).getCursorSignal(),CursorSignal.SERVICE);
            //the built zone should be added to the buildings list of engine
            assertTrue(!engine.getBuildingsList().isEmpty());
            //the buildings list of engine should contain the built element
            assertEquals(engine.getBuildingsList().get(0).getCursorSignal(),CursorSignal.SERVICE);
        }
        //if zone is not empty
        else
            //build should return false
            assertFalse(engine.buildForTesting(0, 0, 1, false));
    }
    @Test
    public void testBuildPolice() {
        Engine engine = new Engine(10, 10, 1, "bigcity");
        Engine.setCursorSignal(CursorSignal.POLICE);
        //if zone is empty
        if(engine.areaInsideGridAndFree(0,0,0,0)){
            //build should return true
            assertTrue(engine.buildForTesting(0, 0, 1, false));
            //the zone where we built should be not null
            assertTrue(engine.getCell(0, 0)!= null);
            //the zone where we built should be the specified building 
            assertEquals(engine.getCell(0, 0).getCursorSignal(),CursorSignal.POLICE);
            //the built zone should be added to the buildings list of engine
            assertTrue(!engine.getBuildingsList().isEmpty());
            //the buildings list of engine should contain the built element
            assertEquals(engine.getBuildingsList().get(0).getCursorSignal(),CursorSignal.POLICE);
        }
        //if zone is not empty
        else
            //build should return false
            assertFalse(engine.buildForTesting(0, 0, 1, false));
    }
    @Test
    public void testBuildStadium() {
        Engine engine = new Engine(10, 10, 1, "bigcity");
        Engine.setCursorSignal(CursorSignal.STADIUM);
        //if zone is empty
        if(engine.areaInsideGridAndFree(0,0,0,0)){
            //build should return true
            assertTrue(engine.buildForTesting(0, 0, 1, false));
            //the zone where we built should be not null
            assertTrue(engine.getCell(0, 0)!= null);
            //the zone where we built should be the specified building 
            assertEquals(engine.getCell(0, 0).getCursorSignal(),CursorSignal.STADIUM);
            //the built zone should be added to the buildings list of engine
            assertTrue(!engine.getBuildingsList().isEmpty());
            //the buildings list of engine should contain the built element
            assertEquals(engine.getBuildingsList().get(0).getCursorSignal(),CursorSignal.STADIUM);
        }
        //if zone is not empty
        else
            //build should return false
            assertFalse(engine.buildForTesting(0, 0, 1, false));
    }
    @Test
    public void testBuildHighSchool() {
        Engine engine = new Engine(10, 10, 1, "bigcity");
        Engine.setCursorSignal(CursorSignal.HIGH_SCHOOL);
        //if zone is empty
        if(engine.areaInsideGridAndFree(0,0,0,0)){
            //build should return true
            assertTrue(engine.buildForTesting(0, 0, 1, false));
            //the zone where we built should be not null
            assertTrue(engine.getCell(0, 0)!= null);
            //the zone where we built should be the specified building 
            assertEquals(engine.getCell(0, 0).getCursorSignal(),CursorSignal.HIGH_SCHOOL);
            //the built zone should be added to the buildings list of engine
            assertTrue(!engine.getBuildingsList().isEmpty());
            //the buildings list of engine should contain the built element
            assertEquals(engine.getBuildingsList().get(0).getCursorSignal(),CursorSignal.HIGH_SCHOOL);
        }
        //if zone is not empty
        else
            //build should return false
            assertFalse(engine.buildForTesting(0, 0, 1, false));
    }
    @Test
    public void testBuildUniversity() {
        Engine engine = new Engine(10, 10, 1, "bigcity");
        Engine.setCursorSignal(CursorSignal.UNIVERSITY);
        //if zone is empty
        if(engine.areaInsideGridAndFree(0,0,0,0)){
            //build should return true
            assertTrue(engine.buildForTesting(0, 0, 1, false));
            //the zone where we built should be not null
            assertTrue(engine.getCell(0, 0)!= null);
            //the zone where we built should be the specified building 
            assertEquals(engine.getCell(0, 0).getCursorSignal(),CursorSignal.UNIVERSITY);
            //the built zone should be added to the buildings list of engine
            assertTrue(!engine.getBuildingsList().isEmpty());
            //the buildings list of engine should contain the built element
            assertEquals(engine.getBuildingsList().get(0).getCursorSignal(),CursorSignal.UNIVERSITY);
        }
        //if zone is not empty
        else
            //build should return false
            assertFalse(engine.buildForTesting(0, 0, 1, false));
    }
    @Test
    public void testBuildRoad() {
        Engine engine = new Engine(10, 10, 1, "bigcity");
        Engine.setCursorSignal(CursorSignal.ROAD);
        //if zone is empty
        if(engine.areaInsideGridAndFree(0,0,0,0)){
            //build should return true
            assertTrue(engine.buildForTesting(0, 0, 1, false));
            //the zone where we built should be not null
            assertTrue(engine.getCell(0, 0)!= null);
            //the zone where we built should be the specified building 
            assertEquals(engine.getCell(0, 0).getCursorSignal(),CursorSignal.ROAD);
            //the built zone should be added to the buildings list of engine
            assertTrue(!engine.getBuildingsList().isEmpty());
            //the buildings list of engine should contain the built element
            assertEquals(engine.getBuildingsList().get(0).getCursorSignal(),CursorSignal.ROAD);
        }
        //if zone is not empty
        else
            //build should return false
            assertFalse(engine.buildForTesting(0, 0, 1, false));
    }
}
