/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.bullscows.dto;

import java.util.Objects;

/**
 *
 * @author Qing
 */
public class Game {
    private int gameId;
    //String
    private String gameAnswer;
    private String gameStatus;

    public Game() {
    }

    public Game(int gameId, String gameAnswer, String gameStatus) {
        this.gameId = gameId;
        this.gameAnswer = gameAnswer;
        this.gameStatus = gameStatus;
    }

    public int getGameId() {
        return gameId;
    }

    public void setGameId(int gameId) {
        this.gameId = gameId;
    }

    public String getGameAnswer() {
        return gameAnswer;
    }

    public void setGameAnswer(String gameAnswer) {
        this.gameAnswer = gameAnswer;
    }

    public String getGameStatus() {
        return gameStatus;
    }

    public void setGameStatus(String gameStatus) {
        this.gameStatus = gameStatus;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 41 * hash + this.gameId;
        hash = 41 * hash + Objects.hashCode(this.gameAnswer);
        hash = 41 * hash + Objects.hashCode(this.gameStatus);
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
        final Game other = (Game) obj;
        if (this.gameId != other.gameId) {
            return false;
        }
        if (!Objects.equals(this.gameAnswer, other.gameAnswer)) {
            return false;
        }
        if (!Objects.equals(this.gameStatus, other.gameStatus)) {
            return false;
        }
        return true;
    }

    
    
}
