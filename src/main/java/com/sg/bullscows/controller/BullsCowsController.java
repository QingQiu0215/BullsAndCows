/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.bullscows.controller;

import com.sg.bullscows.dto.Game;
import com.sg.bullscows.dto.Round;
import com.sg.bullscows.service.BullsCowsService;
import com.sg.bullscows.service.GameDaoPersistanceException;
import com.sg.bullscows.service.GameDataInvalidException;
import com.sg.bullscows.service.GameDoesNotExistException;
import com.sg.bullscows.service.GameNotInProgressException;
import com.sg.bullscows.service.GuessNumberNotIntegerException;
import com.sg.bullscows.service.RoundDaoPersistanceException;
import com.sg.bullscows.service.RoundDoesNotExistException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Qing
 */
@RestController
@RequestMapping("restapi")
public class BullsCowsController {
    
    @Autowired
    BullsCowsService service;
    
    @PostMapping("begin")
    @ResponseStatus(HttpStatus.CREATED)
    public String createGame() throws GameDaoPersistanceException, GameDataInvalidException {
        Game g=service.addGame();
        return "The id of the created game is: "+g.getGameId();
    }
    
    @PostMapping("guess")
    public Round createGuess(String guessNum,int gameId) throws RoundDaoPersistanceException, GameDoesNotExistException, GameDaoPersistanceException, GameDataInvalidException, GameNotInProgressException, GuessNumberNotIntegerException{
        return service.addRound(guessNum,gameId);
    }
    
    
    @GetMapping("game")
    public List<Game> getAllGames() throws GameDaoPersistanceException{
        return service.getAllGames();
    }
            
    
    @GetMapping("game/{gameId}")
   public ResponseEntity<Game> findById(@PathVariable int gameId) throws GameDaoPersistanceException, GameDoesNotExistException {
        Game result = service.getGameById(gameId);
        if (result == null) {
            return new ResponseEntity(null, HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(result);
    }
    
    @GetMapping("rounds/{gameId}")
    public List<Round> getRoundsByGameId(@PathVariable int gameId) throws RoundDaoPersistanceException, RoundDoesNotExistException{
        return service.getRoundByGameId(gameId);
    }
}

