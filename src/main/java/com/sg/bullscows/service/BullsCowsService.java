/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.bullscows.service;

import com.sg.bullscows.dao.GameDao;
import com.sg.bullscows.dao.RoundDao;
import com.sg.bullscows.dto.Game;
import com.sg.bullscows.dto.Round;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Qing
 */
@Service
public class BullsCowsService {

    @Autowired
    GameDao gameDao;
    @Autowired
    RoundDao roundDao;

    public BullsCowsService(GameDao gameDao, RoundDao roundDao) {
        this.gameDao = gameDao;
        this.roundDao = roundDao;
    }

    //*****************for Game ********************************
    public Game getGameById(int id) throws GameDaoPersistanceException, GameDoesNotExistException {
        Game g = gameDao.findGameById(id);
        if (g == null) {
            throw new GameDoesNotExistException("Game with id " + id + " does not exist");
        }
        if (g.getGameStatus().equalsIgnoreCase("Progress")) {
            g.setGameAnswer(" ");
        }
        return g;
    }

    public List<Game> getAllGames() throws GameDaoPersistanceException {
        List<Game> games = gameDao.getAllGames();
        for (Game g : games) {
            if (g.getGameStatus().equalsIgnoreCase("Progress")) {
                g.setGameAnswer(" ");
            }
        }
        return games;
    }

    public Game addGame() throws GameDaoPersistanceException {
        return gameDao.addGame(generateAnswer(4));
    }

    public String generateAnswer(int length) {
        ArrayList<Integer> numbers = new ArrayList<Integer>();
        while (length > 0) {
            int digit = (int) (Math.random() * 10);
            if (numbers.contains(digit)) {
                continue;
            }
            numbers.add(digit);
            length--;
        }
        StringBuilder sb = new StringBuilder();
        for (Integer integer : numbers) {
            sb.append(integer);
        }
        return sb.toString();
    }

    public void updateGame(Game game) {
        Game g = gameDao.findGameById(game.getGameId());
        game.setGameAnswer(g.getGameAnswer());
        gameDao.updateGame(game);
    }

    //*****************for Round ********************************
    public List<Round> getRoundByGameId(int id) throws RoundDaoPersistanceException, RoundDoesNotExistException {
        List<Round> r = roundDao.findRoundByGameId(id);
        if (r.size() == 0) {
            throw new RoundDoesNotExistException("Rounds with game id " + id + " does not exist");
        }
        return r;
    }

    public List<Round> getAllRounds() throws RoundDaoPersistanceException {
        return roundDao.getAllRounds();
    }

    public Round addRound(String guessNum, int gameId) throws GameDoesNotExistException, GameDataInvalidException, GameNotInProgressException, GuessNumberNotIntegerException {
        Game game = gameDao.findGameById(gameId);
        if (game == null) {
            throw new GameDoesNotExistException("Game with id " + gameId + " does not exist");
        }
        String result = getResult(guessNum, gameId);
        int countExact = Integer.parseInt(result.charAt(2) + "");
        if (countExact == 4) {
            game.setGameStatus("Finished");
            gameDao.updateGame(game);
        }
        Round round = roundDao.addRound(guessNum, result, gameId);
        return round;
    }

    public String getResult(String guessNum, int gameId) throws GameDoesNotExistException, GameDataInvalidException, GameNotInProgressException, GuessNumberNotIntegerException {
        Game game = gameDao.findGameById(gameId);
        if (game == null) {
            throw new GameDoesNotExistException("Game with id " + gameId + " does not exist");
        }

        if (validGameStatus(game.getGameStatus())) {
            throw new GameNotInProgressException("This game is finished.");
        }

        try {
            Integer.parseInt(guessNum);
        } catch (NumberFormatException ex) {
            throw new GuessNumberNotIntegerException("The guess number is not an integer.");
        }
        if (guessNum.length() != 4) {
            throw new GameDataInvalidException("The length of Guess number should be 4.");
        }

        boolean validGuessNumber = validGuessNumber(guessNum);
        if (!validGuessNumber) {
            throw new GameDataInvalidException("The digits of the guess number should be different.");
        }
        String answer = game.getGameAnswer();
        String result = compareNum(guessNum, answer);

        return result;
    }

    public static String compareNum(String guessNum, String answer) {
        int countExact = 0, countPartial = 0;
        char[] guessNumArr = guessNum.toCharArray();
        char[] answerArr = answer.toCharArray();
        for (int i = 0; i < guessNumArr.length; i++) {
            if (guessNumArr[i] == answerArr[i]) {
                countExact++;
            }
        }
        for (int i = 0; i < guessNumArr.length; i++) {
            for (int j = 0; j < guessNumArr.length; j++) {
                if (guessNumArr[i] == answerArr[j]) {
                    countPartial++;
                }
            }
        }
        countPartial -= countExact;
        return "e:" + countExact + ":p:" + countPartial;
    }

    private boolean validGuessNumber(String guessNum) {
        LinkedHashSet<Character> s = new LinkedHashSet<>();
        for (int i = 0; i < guessNum.length(); ++i) {
            char c = guessNum.charAt(i);
            if (s.contains(c)) {
                return false;
            }
            s.add(c);
        }
        return true;
    }

    private boolean validGameStatus(String status) {
        return status.equalsIgnoreCase("Finished");
    }


}
