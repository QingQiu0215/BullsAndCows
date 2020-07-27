/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.bullscows.dto;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 *
 * @author Qing
 */
public class Round {
    
    private int roundId;
    private int guess;
    private LocalDateTime guessTime;
    private String result;
    private Game game;

    public Round() {
    }

    public Round(int roundId, int guess, LocalDateTime guessTime, String result, Game game) {
        this.roundId = roundId;
        this.guess = guess;
        this.guessTime = guessTime;
        this.result = result;
        this.game = game;
    }

    public int getRoundId() {
        return roundId;
    }

    public void setRoundId(int roundId) {
        this.roundId = roundId;
    }

    public int getGuess() {
        return guess;
    }

    public void setGuess(int guess) {
        this.guess = guess;
    }

    public LocalDateTime getGuessTime() {
        return guessTime;
    }

    public void setGuessTime(LocalDateTime guessTime) {
        this.guessTime = guessTime;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 97 * hash + this.roundId;
        hash = 97 * hash + this.guess;
        hash = 97 * hash + Objects.hashCode(this.result);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Round other = (Round) obj;
        if (this.roundId != other.roundId) {
            return false;
        }
        if (this.guess != other.guess) {
            return false;
        }
        if (!Objects.equals(this.result, other.result)) {
            return false;
        }
        return true;
    }

    
    
    
    
}
