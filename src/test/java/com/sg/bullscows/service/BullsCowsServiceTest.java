/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.bullscows.service;

import com.sg.bullscows.TestApplicationConfiguration;
import com.sg.bullscows.dto.Game;
import com.sg.bullscows.dto.Round;
import java.util.LinkedHashSet;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;

/**
 *
 * @author Qing
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestApplicationConfiguration.class)
public class BullsCowsServiceTest {

    @Autowired
    BullsCowsService service;

    @Autowired
    JdbcTemplate jdbc;

//    public BullsAndCowsServiceTest(){
//        service=new BullsAndCowsService(new GameDaoDBMock(), new RoundDaoDBMock());
//    }
    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {

    }

    @After
    public void tearDown() {
    }

    /**
     * Test of getGameById method, of class BullsAndCowsService.
     */
    @Test
    public void testGetGameById() throws Exception {

        try {
            //good pass
            Game g = service.addGame();
            service.getGameById(g.getGameId());
        } catch (GameDoesNotExistException ex) {
            fail();
        }

        try {
            //bad pass
            Game g = service.addGame();
            service.getGameById(99999);
            fail();
        } catch (GameDoesNotExistException ex) {

        }
    }

    /**
     * Test of getRoundByGameId method, of class BullsAndCowsService.
     */
    @Test
    public void testGetRoundByGameId() throws Exception {

        try {
            //good pass
            Game g = service.addGame();
            Round round = service.addRound("1234", g.getGameId());
            service.getRoundByGameId(g.getGameId());
        } catch (RoundDoesNotExistException ex) {
            fail();
        }

        try {
            //bad pass
            Game g = service.addGame();
            service.getRoundByGameId(9999);
            fail();
        } catch (RoundDoesNotExistException ex) {

        }
    }

    /**
     * Test of getResult method, of class BullsAndCowsService.
     */
    @Test
    public void testGetResult() throws Exception {

        //*************GameDoesNotExistException
        try {
            //good pass
            Game g = service.addGame();
            service.getResult("1234", g.getGameId());
        } catch (GameDoesNotExistException ex) {
            fail();
        }

        try {
            //bad pass
            Game g = service.addGame();
            service.getResult("1234", 99999);
            fail();
        } catch (GameDoesNotExistException ex) {

        }

        //********GameNotInProgressException
        try {
            //good pass
            Game g = service.addGame();
            service.getResult("1234", g.getGameId());
        } catch (GameNotInProgressException ex) {
            fail();
        }

        try {
            //bad pass
            Game g = service.addGame();
            g.setGameStatus("Finished");
            service.updateGame(g);
            service.getResult("1234", g.getGameId());
            fail();
        } catch (GameNotInProgressException ex) {

        }

        //****************GameDataInvalidException
        try {
            //good pass
            Game g = service.addGame();
            service.getResult("1234", g.getGameId());
        } catch (GameDataInvalidException ex) {
            fail();
        }

        try {
            //bad pass
            Game g = service.addGame();
            service.getResult("1234567", g.getGameId());
            fail();
        } catch (GameDataInvalidException ex) {

        }

        try {
            //good pass
            Game g = service.addGame();
            service.getResult("1234", g.getGameId());
        } catch (GameDataInvalidException ex) {
            fail();
        }

        try {
            //bad pass

            Game g = service.addGame();
            service.getResult("1111", g.getGameId());
            fail();
        } catch (GameDataInvalidException ex) {

        }

        //*************GuessNumberNotIntegerException
        try {
            //good pass
            Game g = service.addGame();
            service.getResult("1234", g.getGameId());
        } catch (GuessNumberNotIntegerException ex) {
            fail();
        }

        try {
            //bad pass

            Game g = service.addGame();
            service.getResult("1abd", g.getGameId());
            fail();
        } catch (GuessNumberNotIntegerException ex) {

        }
    }

    /**
     * Test of compareNum method, of class BullsCowsService.
     */
    @Test
    public void testCompareNum() {
        String result = service.compareNum("1234", "1234");
        assertEquals(result, "e:4:p:0");
        result = service.compareNum("1234", "7890");
        assertEquals(result, "e:0:p:0");
        result = service.compareNum("1234", "7892");
        assertEquals(result, "e:0:p:1");
        result = service.compareNum("1234", "7812");
        assertEquals(result, "e:0:p:2");
        result = service.compareNum("1234", "7412");
        assertEquals(result, "e:0:p:3");
        result = service.compareNum("1234", "4321");
        assertEquals(result, "e:0:p:4");
        result = service.compareNum("1234", "1567");
        assertEquals(result, "e:1:p:0");
        result = service.compareNum("1234", "1562");
        assertEquals(result, "e:1:p:1");
        result = service.compareNum("1234", "0243");
        assertEquals(result, "e:1:p:2");
        result = service.compareNum("1234", "1423");
        assertEquals(result, "e:1:p:3");
        result = service.compareNum("1234", "1256");
        assertEquals(result, "e:2:p:0");
        result = service.compareNum("1234", "1245");
        assertEquals(result, "e:2:p:1");
        result = service.compareNum("1234", "1243");
        assertEquals(result, "e:2:p:2");
        result = service.compareNum("1234", "1235");
        assertEquals(result, "e:3:p:0");

    }

    /**
     * Test of generateAnswer method, of class BullsCowsService.
     */
    @Test
    public void testGenerateAnswer() {

        for (int k = 0; k < 1000; k++) {
            String answer = service.generateAnswer(4);
            LinkedHashSet<Character> lhs = new LinkedHashSet<>();
            for (int i = 0; i < answer.length(); i++) {
                lhs.add(answer.charAt(i));
            }
            assertEquals(4, lhs.size());
        }

    }

}
