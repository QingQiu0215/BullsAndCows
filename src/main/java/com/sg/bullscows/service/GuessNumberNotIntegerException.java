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
public class GuessNumberNotIntegerException extends Exception{

    public GuessNumberNotIntegerException(String message) {
        super(message);
    }

    public GuessNumberNotIntegerException(String message, Throwable cause) {
        super(message, cause);
    }
    
}
