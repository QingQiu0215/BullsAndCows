/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.bullscows.dao;

import com.sg.bullscows.TestApplicationConfiguration;
import com.sg.bullscows.dto.Game;
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
public class GameDaoDBImplTest {
    
    @Autowired
    GameDao gameDao;
    
    @Autowired
    JdbcTemplate jdbc;
    
    public GameDaoDBImplTest() {
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
     * Test of addGame method, of class GameDaoDBImpl.
     */
    @Test
    public void testAddGameAndFindGameById() {      

        Game g=gameDao.addGame("1234");
        Game fromDao=gameDao.findGameById(g.getGameId());
        assertEquals(g, fromDao);
    }

    /**
     * Test of getAllGames method, of class GameDaoDBImpl.
     */
    @Test
    public void testGetAllGames() {
        List<Game> gamesFromDao=gameDao.getAllGames();
        
        assertEquals(0, gamesFromDao.size());
        Game g1=gameDao.addGame("1234");
        Game g2=gameDao.addGame("2345");
        gamesFromDao=gameDao.getAllGames();
        assertEquals(2, gamesFromDao.size());
        assertEquals(g1,gamesFromDao.get(0));
        assertEquals(g2,gamesFromDao.get(1));
    }

    /**
     * Test of updateGame method, of class GameDaoDBImpl.
     */
    @Test
    public void testUpdateGame() {
        Game g=gameDao.addGame("1234");
        Game fromDao=gameDao.findGameById(g.getGameId());
        assertEquals(g, fromDao);
        fromDao.setGameStatus("Test");
        gameDao.updateGame(fromDao);
        Game afterUpdate=gameDao.findGameById(g.getGameId());
        assertNotEquals(g,afterUpdate);
        g.setGameStatus("Test");
        assertEquals(g,afterUpdate);
    }

    
}
