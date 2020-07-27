/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.bullscows.dao;

import com.sg.bullscows.TestApplicationConfiguration;
import com.sg.bullscows.dto.Game;
import com.sg.bullscows.dto.Round;
import java.util.List;
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
public class RoundDaoDBImplTest {
    
    @Autowired
    RoundDao roundDao;
    
    @Autowired
    GameDao gameDao;
    
    @Autowired
    JdbcTemplate jdbc;
    
    public RoundDaoDBImplTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
 
        jdbc.update("DELETE FROM round");
        jdbc.update("DELETE FROM game");
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of addRound method, of class RoundDaoDBImpl.
     */
    @Test
    public void testAddRoundAndFindRoundById() {
        Game g=gameDao.addGame("1234");
        Round r=roundDao.addRound("2315", "result", g.getGameId());
        Round fromDao=roundDao.findRoundById(r.getRoundId());
        assertEquals(r, fromDao);
    }

    /**
     * Test of getAllRounds method, of class RoundDaoDBImpl.
     */
    @Test
    public void testGetAllRounds() {
        Game g=gameDao.addGame("1234");
        List<Round> roundsFromDao=roundDao.getAllRounds();
        
        assertEquals(0, roundsFromDao.size());
        Round r1=roundDao.addRound("2315", "result1", g.getGameId());
        Round r2=roundDao.addRound("2316", "result2", g.getGameId());

        roundsFromDao=roundDao.getAllRounds();
        assertEquals(2, roundsFromDao.size());
        assertEquals(r1,roundsFromDao.get(0));
        assertEquals(r2,roundsFromDao.get(1));
    }

    /**
     * Test of findRoundByGameId method, of class RoundDaoDBImpl.
     */
    @Test
    public void testFindRoundByGameId() {
        Game g=gameDao.addGame("1234");
        Round r1=roundDao.addRound("1234", "result1", g.getGameId());
        Round r2=roundDao.addRound("2345", "result2", g.getGameId());
        List<Round> roundsFromDao=roundDao.findRoundByGameId(g.getGameId());
        assertEquals(2, roundsFromDao.size());
        assertEquals(r1,roundsFromDao.get(0));
        assertEquals(r2,roundsFromDao.get(1));
    }

   
    
}
