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
        Residence expResult = new Residence(0,0,50);
        Zone result = instance.getCell(row, column);
        assertEquals(expResult, result);
        

    }  
}
