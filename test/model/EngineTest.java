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
     * Test of build method, of class Engine. //Private zones.----------------
     * POLICE(1, 1, 300, 0, 0), STADIUM(2, 2, 300, 0, 0), HIGH_SCHOOL(2, 1, 300,
     * 0, 0), UNIVERSITY(2, 2, 500, 0, 0), ROAD(1, 1, 50, 0, 0), //Public
     * zones.----------------- RESIDENCE(1, 1, 50, 100, 300), INDUSTRY(1, 1,
     * 100, 300, 1000), SERVICE(1, 1, 50, 100, 300),
     */
    @Test
    public void checkBuildOutOfBounds() {
        Engine engine = new Engine(10, 10, 1, "bigcity");
        for (CursorSignal value : CursorSignal.values()) {
            if (!value.equals(CursorSignal.SELECT)) {
                Engine.setCursorSignal(value);
                assertFalse(engine.buildForTesting(-1, -1, 1, false));
                assertFalse(engine.buildForTesting(-1, 0, 1, false));
                assertFalse(engine.buildForTesting(11, 5, 1, false));
                assertFalse(engine.buildForTesting(0, -1, 1, false));
            }
        }
    }

    @Test
    public void checkReturnedMoneyAfterDisaster() {
        Engine engine = new Engine(10, 10, 1, "bigcity");
        int beforeDestruction = engine.getMoney();
        for (CursorSignal value : CursorSignal.values()) {
            if (!value.equals(CursorSignal.SELECT)) {
                Engine.setCursorSignal(value);
                assertFalse(engine.buildForTesting(-1, -1, 1, false));
                assertFalse(engine.buildForTesting(-1, 0, 1, false));
                assertFalse(engine.buildForTesting(11, 5, 1, false));
                assertFalse(engine.buildForTesting(0, -1, 1, false));
            }
        }
    }

    //PUBLIC ZONES
    @Test
    public void testBuildResidence() {
        Engine engine = new Engine(10, 10, 1, "bigcity");
        Engine.setCursorSignal(CursorSignal.RESIDENCE);
        //if zone is empty
        if (engine.areaInsideGridAndFree(0, 0, 0, 0)) {
            //build should return true
            assertTrue(engine.buildForTesting(0, 0, 1, false));
            //the zone where we built should be not null
            assertTrue(engine.getCell(0, 0) != null);
            //the zone where we built should be the specified building 
            assertEquals(engine.getCell(0, 0).getCursorSignal(), CursorSignal.RESIDENCE);
            //the built zone should be added to the buildings list of engine
            assertTrue(!engine.getBuildingsList().isEmpty());
            //the buildings list of engine should contain the built element
            assertEquals(engine.getBuildingsList().get(0).getCursorSignal(), CursorSignal.RESIDENCE);
        } //if zone is not empty
        else //build should return false
        {
            assertFalse(engine.buildForTesting(0, 0, 1, false));
        }
    }

    @Test
    public void checkMoneyAfterResidenceIsBuilt() {
        Engine engine = new Engine(10, 10, 1, "bigcity");
        Engine.setCursorSignal(CursorSignal.RESIDENCE);
        int beforeBuilt = engine.getMoney();
        engine.buildForTesting(0, 0, 1, false);
        assertEquals(beforeBuilt - 50, engine.getMoney());
    }

    @Test
    public void testBuildIndustry() {
        Engine engine = new Engine(10, 10, 1, "bigcity");
        Engine.setCursorSignal(CursorSignal.INDUSTRY);
        //if zone is empty
        if (engine.areaInsideGridAndFree(0, 0, 0, 0)) {
            //build should return true
            assertTrue(engine.buildForTesting(0, 0, 1, false));
            //the zone where we built should be not null
            assertTrue(engine.getCell(0, 0) != null);
            //the zone where we built should be the specified building 
            assertEquals(engine.getCell(0, 0).getCursorSignal(), CursorSignal.INDUSTRY);
            //the built zone should be added to the buildings list of engine
            assertTrue(!engine.getBuildingsList().isEmpty());
            //the buildings list of engine should contain the built element
            assertEquals(engine.getBuildingsList().get(0).getCursorSignal(), CursorSignal.INDUSTRY);
        } //if zone is not empty
        else //build should return false
        {
            assertFalse(engine.buildForTesting(0, 0, 1, false));
        }
    }

    @Test
    public void checkMoneyAfterIndustryIsBuilt() {
        Engine engine = new Engine(10, 10, 1, "bigcity");
        Engine.setCursorSignal(CursorSignal.INDUSTRY);
        int beforeBuilt = engine.getMoney();
        engine.buildForTesting(0, 0, 1, false);
        assertEquals(beforeBuilt - 100, engine.getMoney());
    }

    @Test
    public void testBuildService() {
        Engine engine = new Engine(10, 10, 1, "bigcity");
        Engine.setCursorSignal(CursorSignal.SERVICE);
        //if zone is empty
        if (engine.areaInsideGridAndFree(0, 0, 0, 0)) {
            //build should return true
            assertTrue(engine.buildForTesting(0, 0, 1, false));
            //the zone where we built should be not null
            assertTrue(engine.getCell(0, 0) != null);
            //the zone where we built should be the specified building 
            assertEquals(engine.getCell(0, 0).getCursorSignal(), CursorSignal.SERVICE);
            //the built zone should be added to the buildings list of engine
            assertTrue(!engine.getBuildingsList().isEmpty());
            //the buildings list of engine should contain the built element
            assertEquals(engine.getBuildingsList().get(0).getCursorSignal(), CursorSignal.SERVICE);
        } //if zone is not empty
        else //build should return false
        {
            assertFalse(engine.buildForTesting(0, 0, 1, false));
        }
    }

    @Test
    public void checkMoneyAfterServiceIsBuilt() {
        Engine engine = new Engine(10, 10, 1, "bigcity");
        Engine.setCursorSignal(CursorSignal.SERVICE);
        int beforeBuilt = engine.getMoney();
        engine.buildForTesting(0, 0, 1, false);
        assertEquals(beforeBuilt - 50, engine.getMoney());
    }

    @Test
    public void testBuildPolice() {
        Engine engine = new Engine(10, 10, 1, "bigcity");
        Engine.setCursorSignal(CursorSignal.POLICE);
        //if zone is empty
        if (engine.areaInsideGridAndFree(0, 0, 0, 0)) {
            //build should return true
            assertTrue(engine.buildForTesting(0, 0, 1, false));
            //the zone where we built should be not null
            assertTrue(engine.getCell(0, 0) != null);
            //the zone where we built should be the specified building 
            assertEquals(engine.getCell(0, 0).getCursorSignal(), CursorSignal.POLICE);
            //the built zone should be added to the buildings list of engine
            assertTrue(!engine.getBuildingsList().isEmpty());
            //the buildings list of engine should contain the built element
            assertEquals(engine.getBuildingsList().get(0).getCursorSignal(), CursorSignal.POLICE);
        } //if zone is not empty
        else //build should return false
        {
            assertFalse(engine.buildForTesting(0, 0, 1, false));
        }
    }

    @Test
    public void checkMoneyAfterPoliceIsBuilt() {
        Engine engine = new Engine(10, 10, 1, "bigcity");
        Engine.setCursorSignal(CursorSignal.POLICE);
        int beforeBuilt = engine.getMoney();
        engine.buildForTesting(0, 0, 1, false);
        assertEquals(beforeBuilt - 300, engine.getMoney());
    }

    @Test
    public void testBuildStadium() {
        Engine engine = new Engine(10, 10, 1, "bigcity");
        Engine.setCursorSignal(CursorSignal.STADIUM);
        //if zone is empty
        if (engine.areaInsideGridAndFree(0, 0, 0, 0)) {
            //build should return true
            assertTrue(engine.buildForTesting(0, 0, 1, false));
            //the zone where we built should be not null
            assertTrue(engine.getCell(0, 0) != null);
            //the zone where we built should be the specified building 
            assertEquals(engine.getCell(0, 0).getCursorSignal(), CursorSignal.STADIUM);
            //the built zone should be added to the buildings list of engine
            assertTrue(!engine.getBuildingsList().isEmpty());
            //the buildings list of engine should contain the built element
            assertEquals(engine.getBuildingsList().get(0).getCursorSignal(), CursorSignal.STADIUM);
        } //if zone is not empty
        else //build should return false
        {
            assertFalse(engine.buildForTesting(0, 0, 1, false));
        }
    }

    @Test
    public void checkMoneyAfterStadiumIsBuilt() {
        Engine engine = new Engine(10, 10, 1, "bigcity");
        Engine.setCursorSignal(CursorSignal.STADIUM);
        int beforeBuilt = engine.getMoney();
        engine.buildForTesting(0, 0, 1, false);
        assertEquals(beforeBuilt - 300, engine.getMoney());
    }

    @Test
    public void testBuildHighSchool() {
        Engine engine = new Engine(10, 10, 1, "bigcity");
        Engine.setCursorSignal(CursorSignal.HIGH_SCHOOL);
        //if zone is empty
        if (engine.areaInsideGridAndFree(0, 0, 0, 0)) {
            //build should return true
            assertTrue(engine.buildForTesting(0, 0, 1, false));
            //the zone where we built should be not null
            assertTrue(engine.getCell(0, 0) != null);
            //the zone where we built should be the specified building 
            assertEquals(engine.getCell(0, 0).getCursorSignal(), CursorSignal.HIGH_SCHOOL);
            //the built zone should be added to the buildings list of engine
            assertTrue(!engine.getBuildingsList().isEmpty());
            //the buildings list of engine should contain the built element
            assertEquals(engine.getBuildingsList().get(0).getCursorSignal(), CursorSignal.HIGH_SCHOOL);
        } //if zone is not empty
        else //build should return false
        {
            assertFalse(engine.buildForTesting(0, 0, 1, false));
        }
    }

    @Test
    public void checkMoneyAfterHighSchoolIsBuilt() {
        Engine engine = new Engine(10, 10, 1, "bigcity");
        Engine.setCursorSignal(CursorSignal.STADIUM);
        int beforeBuilt = engine.getMoney();
        engine.buildForTesting(0, 0, 1, false);
        assertEquals(beforeBuilt - 300, engine.getMoney());
    }

    @Test
    public void testBuildUniversity() {
        Engine engine = new Engine(10, 10, 1, "bigcity");
        Engine.setCursorSignal(CursorSignal.UNIVERSITY);
        //if zone is empty
        if (engine.areaInsideGridAndFree(0, 0, 0, 0)) {
            //build should return true
            assertTrue(engine.buildForTesting(0, 0, 1, false));
            //the zone where we built should be not null
            assertTrue(engine.getCell(0, 0) != null);
            //the zone where we built should be the specified building 
            assertEquals(engine.getCell(0, 0).getCursorSignal(), CursorSignal.UNIVERSITY);
            //the built zone should be added to the buildings list of engine
            assertTrue(!engine.getBuildingsList().isEmpty());
            //the buildings list of engine should contain the built element
            assertEquals(engine.getBuildingsList().get(0).getCursorSignal(), CursorSignal.UNIVERSITY);
        } //if zone is not empty
        else //build should return false
        {
            assertFalse(engine.buildForTesting(0, 0, 1, false));
        }
    }

    @Test
    public void checkMoneyAfterUniversityIsBuilt() {
        Engine engine = new Engine(10, 10, 1, "bigcity");
        Engine.setCursorSignal(CursorSignal.UNIVERSITY);
        int beforeBuilt = engine.getMoney();
        engine.buildForTesting(0, 0, 4, false);
        assertEquals(beforeBuilt - 500, engine.getMoney());
    }

    @Test
    public void testBuildRoad() {
        Engine engine = new Engine(10, 10, 1, "bigcity");
        Engine.setCursorSignal(CursorSignal.ROAD);
        //if zone is empty
        if (engine.areaInsideGridAndFree(0, 0, 0, 0)) {
            //build should return true
            assertTrue(engine.buildForTesting(0, 0, 1, false));
            //the zone where we built should be not null
            assertTrue(engine.getCell(0, 0) != null);
            //the zone where we built should be the specified building 
            assertEquals(engine.getCell(0, 0).getCursorSignal(), CursorSignal.ROAD);
            //the built zone should be added to the buildings list of engine
            assertTrue(!engine.getBuildingsList().isEmpty());
            //the buildings list of engine should contain the built element
            assertEquals(engine.getBuildingsList().get(0).getCursorSignal(), CursorSignal.ROAD);
        } //if zone is not empty
        else //build should return false
        {
            assertFalse(engine.buildForTesting(0, 0, 1, false));
        }
    }

    @Test
    public void checkMoneyAfterRoadIsBuilt() {
        Engine engine = new Engine(10, 10, 1, "bigcity");
        Engine.setCursorSignal(CursorSignal.ROAD);
        int beforeBuilt = engine.getMoney();
        engine.buildForTesting(0, 0, 1, false);
        assertEquals(beforeBuilt - 50, engine.getMoney());
    }

    /**
     * Test of destroy method, of class Engine. //Private zones.----------------
     * POLICE(1, 1, 300, 0, 0), STADIUM(2, 2, 300, 0, 0), HIGH_SCHOOL(2, 1, 300,
     * 0, 0), UNIVERSITY(2, 2, 500, 0, 0), ROAD(1, 1, 50, 0, 0), //Public
     * zones.----------------- RESIDENCE(1, 1, 50, 100, 300), INDUSTRY(1, 1,
     * 100, 300, 1000), SERVICE(1, 1, 50, 100, 300),
     */
    @Test
    public void testDestroyResidence() {
        Engine engine = new Engine(10, 10, 1, "bigcity");
        //if zone is a residence
        Engine.setCursorSignal(CursorSignal.RESIDENCE);
        engine.buildForTesting(0, 0, 1, false);
        //destroy should return true
        assertTrue(engine.destroyZoneForTesting(0, 0, 1, false));
        //the destroyed zone should be null
        assertEquals(engine.getCell(0, 0), null);
        //the destroyed zone should be removed from the building list of engine
        assertTrue(engine.getBuildingsList().isEmpty());

        //if zone is null destroy should return false
        assertFalse(engine.destroyZoneForTesting(0, 0, 1, false));
    }

    @Test
    public void checkReturnedMoneyAfterL1ResidenceIsDestroyed() {
        Engine engine = new Engine(10, 10, 1, "bigcity");
        Engine.setCursorSignal(CursorSignal.RESIDENCE);
        engine.buildForTesting(0, 0, 1, false);
        int beforeDestruction = engine.getMoney();
        engine.destroyZoneForTesting(0, 0, 1, false);
        assertEquals(beforeDestruction + 25, engine.getMoney());
    }

    @Test
    public void checkReturnedMoneyAfterL2ResidenceIsDestroyed() {
        Engine engine = new Engine(10, 10, 1, "bigcity");
        Engine.setCursorSignal(CursorSignal.RESIDENCE);
        engine.buildForTesting(0, 0, 1, false);
        engine.getCell(0, 0).setLevel(2);
        int beforeDestruction = engine.getMoney();
        engine.destroyZoneForTesting(0, 0, 1, false);
        assertEquals(beforeDestruction + 75, engine.getMoney());
    }

    @Test
    public void checkReturnedMoneyAfterL3ResidenceIsDestroyed() {
        Engine engine = new Engine(10, 10, 1, "bigcity");
        Engine.setCursorSignal(CursorSignal.RESIDENCE);
        engine.buildForTesting(0, 0, 1, false);
        engine.getCell(0, 0).setLevel(3);
        int beforeDestruction = engine.getMoney();
        engine.destroyZoneForTesting(0, 0, 1, false);
        assertEquals(beforeDestruction + 225, engine.getMoney());
    }

    @Test
    public void testDestroyIndustry() {
        Engine engine = new Engine(10, 10, 1, "bigcity");
        //if zone is a residence
        Engine.setCursorSignal(CursorSignal.INDUSTRY);
        engine.buildForTesting(0, 0, 1, false);
        //destroy should return true
        assertTrue(engine.destroyZoneForTesting(0, 0, 1, false));
        //the destroyed zone should be null
        assertEquals(engine.getCell(0, 0), null);
        //the destroyed zone should be removed from the building list of engine
        assertTrue(engine.getBuildingsList().isEmpty());

        //if zone is null destroy should return false
        assertFalse(engine.destroyZoneForTesting(0, 0, 1, false));
    }

    @Test
    public void checkReturnedMoneyAfterL1IndustryIsDestroyed() {
        Engine engine = new Engine(10, 10, 1, "bigcity");
        Engine.setCursorSignal(CursorSignal.INDUSTRY);
        engine.buildForTesting(0, 0, 1, false);
        int beforeDestruction = engine.getMoney();
        engine.destroyZoneForTesting(0, 0, 1, false);
        assertEquals(beforeDestruction + 50, engine.getMoney());
    }

    @Test
    public void checkReturnedMoneyAfterL2IndustryIsDestroyed() {
        Engine engine = new Engine(10, 10, 1, "bigcity");
        Engine.setCursorSignal(CursorSignal.INDUSTRY);
        engine.buildForTesting(0, 0, 1, false);
        engine.getCell(0, 0).setLevel(2);
        int beforeDestruction = engine.getMoney();
        engine.destroyZoneForTesting(0, 0, 1, false);
        assertEquals(beforeDestruction + 200, engine.getMoney());
    }

    @Test
    public void checkReturnedMoneyAfterL3IndustryIsDestroyed() {
        Engine engine = new Engine(10, 10, 1, "bigcity");
        Engine.setCursorSignal(CursorSignal.INDUSTRY);
        engine.buildForTesting(0, 0, 1, false);
        engine.getCell(0, 0).setLevel(3);
        int beforeDestruction = engine.getMoney();
        engine.destroyZoneForTesting(0, 0, 1, false);
        assertEquals(beforeDestruction + 700, engine.getMoney());
    }

    @Test
    public void testDestroyService() {
        Engine engine = new Engine(10, 10, 1, "bigcity");
        //if zone is a residence
        Engine.setCursorSignal(CursorSignal.SERVICE);
        engine.buildForTesting(0, 0, 1, false);
        //destroy should return true
        assertTrue(engine.destroyZoneForTesting(0, 0, 1, false));
        //the destroyed zone should be null
        assertEquals(engine.getCell(0, 0), null);
        //the destroyed zone should be removed from the building list of engine
        assertTrue(engine.getBuildingsList().isEmpty());

        //if zone is null destroy should return false
        assertFalse(engine.destroyZoneForTesting(0, 0, 1, false));
    }

    @Test
    public void checkReturnedMoneyAfterL1ServiceIsDestroyed() {
        Engine engine = new Engine(10, 10, 1, "bigcity");
        Engine.setCursorSignal(CursorSignal.SERVICE);
        engine.buildForTesting(0, 0, 1, false);
        int beforeDestruction = engine.getMoney();
        engine.destroyZoneForTesting(0, 0, 1, false);
        assertEquals(beforeDestruction + 25, engine.getMoney());
    }

    @Test
    public void checkReturnedMoneyAfterL2ServiceIsDestroyed() {
        Engine engine = new Engine(10, 10, 1, "bigcity");
        Engine.setCursorSignal(CursorSignal.SERVICE);
        engine.buildForTesting(0, 0, 1, false);
        engine.getCell(0, 0).setLevel(2);
        int beforeDestruction = engine.getMoney();
        engine.destroyZoneForTesting(0, 0, 1, false);
        assertEquals(beforeDestruction + 75, engine.getMoney());
    }

    @Test
    public void checkReturnedMoneyAfterL3ServiceIsDestroyed() {
        Engine engine = new Engine(10, 10, 1, "bigcity");
        Engine.setCursorSignal(CursorSignal.SERVICE);
        engine.buildForTesting(0, 0, 1, false);
        engine.getCell(0, 0).setLevel(3);
        int beforeDestruction = engine.getMoney();
        engine.destroyZoneForTesting(0, 0, 1, false);
        assertEquals(beforeDestruction + 225, engine.getMoney());
    }

    @Test
    public void testDestroyPolice() {
        Engine engine = new Engine(10, 10, 1, "bigcity");
        //if zone is a residence
        Engine.setCursorSignal(CursorSignal.POLICE);
        engine.buildForTesting(0, 0, 1, false);
        //destroy should return true
        assertTrue(engine.destroyZoneForTesting(0, 0, 1, false));
        //the destroyed zone should be null
        assertEquals(engine.getCell(0, 0), null);
        //the destroyed zone should be removed from the building list of engine
        assertTrue(engine.getBuildingsList().isEmpty());

        //if zone is null destroy should return false
        assertFalse(engine.destroyZoneForTesting(0, 0, 1, false));
    }

    @Test
    public void checkReturnedMoneyAfterPoliceIsDestroyed() {
        Engine engine = new Engine(10, 10, 1, "bigcity");
        Engine.setCursorSignal(CursorSignal.POLICE);
        engine.buildForTesting(0, 0, 1, false);
        int beforeDestruction = engine.getMoney();
        engine.destroyZoneForTesting(0, 0, 1, false);
        assertEquals(beforeDestruction + 150, engine.getMoney());
    }

    @Test
    public void testDestroyStadium() {
        Engine engine = new Engine(10, 10, 1, "bigcity");
        //if zone is a residence
        Engine.setCursorSignal(CursorSignal.STADIUM);
        engine.buildForTesting(0, 0, 4, false);
        //destroy should return true
        assertTrue(engine.destroyZoneForTesting(0, 0, 4, false));
        //the destroyed zone should be null
        assertEquals(engine.getCell(0, 0), null);
        //the destroyed zone should be removed from the building list of engine
        assertTrue(engine.getBuildingsList().isEmpty());

        //if zone is null destroy should return false
        assertFalse(engine.destroyZoneForTesting(0, 0, 4, false));
    }

    @Test
    public void checkReturnedMoneyAfterStadiumIsDestroyed() {
        Engine engine = new Engine(10, 10, 1, "bigcity");
        Engine.setCursorSignal(CursorSignal.STADIUM);
        engine.buildForTesting(0, 0, 1, false);
        int beforeDestruction = engine.getMoney();
        engine.destroyZoneForTesting(0, 0, 4, false);
        assertEquals(beforeDestruction + 150, engine.getMoney());
    }

    @Test
    public void testDestroyHighSchool() {
        Engine engine = new Engine(10, 10, 1, "bigcity");
        //if zone is a residence
        Engine.setCursorSignal(CursorSignal.HIGH_SCHOOL);
        engine.buildForTesting(0, 0, 2, false);
        //destroy should return true
        assertTrue(engine.destroyZoneForTesting(0, 0, 2, false));
        //the destroyed zone should be null
        assertEquals(engine.getCell(0, 0), null);
        //the destroyed zone should be removed from the building list of engine
        assertTrue(engine.getBuildingsList().isEmpty());

        //if zone is null destroy should return false
        assertFalse(engine.destroyZoneForTesting(0, 0, 2, false));
    }

    @Test
    public void checkReturnedMoneyAfterHighSchoolIsDestroyed() {
        Engine engine = new Engine(10, 10, 1, "bigcity");
        Engine.setCursorSignal(CursorSignal.HIGH_SCHOOL);
        engine.buildForTesting(0, 0, 1, false);
        int beforeDestruction = engine.getMoney();
        engine.destroyZoneForTesting(0, 0, 2, false);
        assertEquals(beforeDestruction + 150, engine.getMoney());
    }

    @Test
    public void testDestroyUniversity() {
        Engine engine = new Engine(10, 10, 1, "bigcity");
        //if zone is a residence
        Engine.setCursorSignal(CursorSignal.UNIVERSITY);
        engine.buildForTesting(0, 0, 4, false);
        //destroy should return true
        assertTrue(engine.destroyZoneForTesting(0, 0, 4, false));
        //the destroyed zone should be null
        assertEquals(engine.getCell(0, 0), null);
        //the destroyed zone should be removed from the building list of engine
        assertTrue(engine.getBuildingsList().isEmpty());

        //if zone is null destroy should return false
        assertFalse(engine.destroyZoneForTesting(0, 0, 4, false));
    }

    @Test
    public void checkReturnedMoneyAfterUniversityIsDestroyed() {
        Engine engine = new Engine(10, 10, 1, "bigcity");
        Engine.setCursorSignal(CursorSignal.UNIVERSITY);
        engine.buildForTesting(0, 0, 1, false);
        int beforeDestruction = engine.getMoney();
        engine.destroyZoneForTesting(0, 0, 4, false);
        assertEquals(beforeDestruction + 250, engine.getMoney());
    }

    @Test
    public void testDestroyRoad() {
        Engine engine = new Engine(10, 10, 1, "bigcity");
        //if zone is a residence
        Engine.setCursorSignal(CursorSignal.ROAD);
        engine.buildForTesting(0, 0, 1, false);
        //destroy should return true
        assertTrue(engine.destroyZoneForTesting(0, 0, 1, false));
        //the destroyed zone should be null
        assertEquals(engine.getCell(0, 0), null);
        //the destroyed zone should be removed from the building list of engine
        assertTrue(engine.getBuildingsList().isEmpty());

        //if zone is null destroy should return false
        assertFalse(engine.destroyZoneForTesting(0, 0, 1, false));
    }

    @Test
    public void checkReturnedMoneyAfterRoadIsDestroyed() {
        Engine engine = new Engine(10, 10, 1, "bigcity");
        Engine.setCursorSignal(CursorSignal.ROAD);
        engine.buildForTesting(0, 0, 1, false);
        int beforeDestruction = engine.getMoney();
        engine.destroyZoneForTesting(0, 0, 1, false);
        assertEquals(beforeDestruction + 25, engine.getMoney());
    }
}
