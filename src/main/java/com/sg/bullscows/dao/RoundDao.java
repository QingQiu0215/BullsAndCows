/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.bullscows.dao;

import com.sg.bullscows.dto.Round;
import java.util.List;

/**
 *
 * @author Qing
 */
public interface RoundDao {
    public Round addRound(String guessNum,String result, int gameId);

    List<Round> getAllRounds();

    List<Round> findRoundByGameId(int id);
    
    public Round findRoundById(int gameid);

}
