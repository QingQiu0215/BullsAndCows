/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.bullscows.controller;

import com.sg.bullscows.service.GameDataInvalidException;
import com.sg.bullscows.service.GameDoesNotExistException;
import com.sg.bullscows.service.GameNotInProgressException;
import com.sg.bullscows.service.GuessNumberNotIntegerException;
import com.sg.bullscows.service.RoundDoesNotExistException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 *
 * @author Qing
 */
@ControllerAdvice
@RestController
public class ControllerExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(GameDoesNotExistException.class)
    public final ResponseEntity<Error> handleGameDNE(GameDoesNotExistException ex, WebRequest request) {
        Error err = new Error();
        err.setMessage(ex.getMessage());
        return new ResponseEntity<>(err, HttpStatus.NOT_FOUND);
    }
    
    @ExceptionHandler(GameDataInvalidException.class)
    public final ResponseEntity<Error> handleInvalidData(GameDataInvalidException ex, WebRequest request) {
        Error err = new Error();
        err.setMessage(ex.getMessage());
        return new ResponseEntity<>(err, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(GameNotInProgressException.class)
    public final ResponseEntity<Error> handleNotInProgress(GameNotInProgressException ex, WebRequest request) {
        Error err = new Error();
        err.setMessage(ex.getMessage());
        return new ResponseEntity<>(err, HttpStatus.UNPROCESSABLE_ENTITY);
    }
    
    @ExceptionHandler(RoundDoesNotExistException.class)
    public final ResponseEntity<Error> handleRoundDoesNotExist(RoundDoesNotExistException ex, WebRequest request) {
        Error err = new Error();
        err.setMessage(ex.getMessage());
        return new ResponseEntity<>(err, HttpStatus.UNPROCESSABLE_ENTITY);
    }
    
    @ExceptionHandler(GuessNumberNotIntegerException.class)
    public final ResponseEntity<Error> handleGuessNumberNotInteger(GuessNumberNotIntegerException ex, WebRequest request) {
        Error err = new Error();
        err.setMessage(ex.getMessage());
        return new ResponseEntity<>(err, HttpStatus.UNPROCESSABLE_ENTITY);
    }
    
    
    
}
