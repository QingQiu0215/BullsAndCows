/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.bullscows.dao;

import com.sg.bullscows.dto.Game;
import java.util.List;

/**
 *
 * @author Qing
 */
public interface GameDao {
    public Game addGame(String answer);

    List<Game> getAllGames();

    Game findGameById(int id);

    // true if item exists and is updated
    void updateGame(Game game);

}