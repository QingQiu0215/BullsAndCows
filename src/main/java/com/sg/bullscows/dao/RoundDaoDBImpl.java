/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.bullscows.dao;

import com.sg.bullscows.dao.GameDaoDBImpl.GameMapper;
import com.sg.bullscows.dto.Game;
import com.sg.bullscows.dto.Round;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Qing
 */
@Repository
public class RoundDaoDBImpl implements RoundDao {

    private final JdbcTemplate jdbc;

    @Autowired
    public RoundDaoDBImpl(JdbcTemplate jdbcTemplate) {
        this.jdbc = jdbcTemplate;
    }

    public static final class RoundMapper implements RowMapper<Round> {

        @Override
        public Round mapRow(ResultSet rs, int index) throws SQLException {
            Round r = new Round();
            r.setRoundId(rs.getInt("Id"));
            r.setGuess(rs.getInt("Guess"));
            r.setGuessTime(rs.getTimestamp("GuessTime").toLocalDateTime());
            r.setResult(rs.getString("Result"));
            return r;
        }
    }

    @Override
    @Transactional
    public Round addRound(String guessNum,String result, int gameId) {
        final String INSERT_ROUND = "INSERT INTO round(Guess, GuessTime, Result,GameId) VALUES(?,?,?,?)";
        jdbc.update(INSERT_ROUND,
                guessNum,
                Timestamp.valueOf(LocalDateTime.now()),
                result,
                gameId);
        int newId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);

        return findRoundById(newId);
    }

    @Override
    public List<Round> getAllRounds() {
        final String SELECT_ALL_ROUNDS = "SELECT * FROM round ORDER BY GuessTime ASC ";
        List<Round> rounds = jdbc.query(SELECT_ALL_ROUNDS, new RoundMapper());

        addGamesToRounds(rounds);

        return rounds;
    }

    private void addGamesToRounds(List<Round> rounds) {
        for (Round r : rounds) {
            r.setGame(getGameForRound(r));
        }
    }
    
    private Game getGameForRound(Round r){
        final String SELECT_GAME_FOR_ROUND = "SELECT g.* FROM game g "
                + "JOIN round r ON g.id = r.gameId WHERE r.id = ?";
        return jdbc.queryForObject(SELECT_GAME_FOR_ROUND, new GameMapper(), 
                r.getRoundId());
    }

    @Override
    public List<Round> findRoundByGameId(int gameid) {
        try {
            final String SELECT_ROUNDS_BY_GAMEID = "SELECT * FROM round WHERE gameid = ?";
            List<Round> rounds = jdbc.query(SELECT_ROUNDS_BY_GAMEID, 
                    new RoundMapper(), gameid);
     
            return rounds;
        } catch(DataAccessException ex) {
            return null;
        }
    }
    
    @Override
    public Round findRoundById(int id) {
        try {
            final String SELECT_ROUNDS_BY_GAMEID = "SELECT * FROM round WHERE id = ?";
            Round round = jdbc.queryForObject(SELECT_ROUNDS_BY_GAMEID, 
                    new RoundMapper(), id);
     
            return round;
        } catch(DataAccessException ex) {
            return null;
        }
    }


}

