/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.bullscows.service;

/**
 *
 * @author Qing
 */
public class GameDaoPersistanceException extends Exception{

    public GameDaoPersistanceException(String message) {
        super(message);
    }

    public GameDaoPersistanceException(String message, Throwable cause) {
        super(message, cause);
    }
    
    
}
