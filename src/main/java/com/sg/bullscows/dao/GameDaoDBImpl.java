/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.bullscows.dao;

import com.sg.bullscows.dto.Game;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Qing
 */
@Repository
public class GameDaoDBImpl implements GameDao {

    private final JdbcTemplate jdbc;

    @Autowired
    public GameDaoDBImpl(JdbcTemplate jdbcTemplate) {
        this.jdbc = jdbcTemplate;
    }

    public static final class GameMapper implements RowMapper<Game> {

        @Override
        public Game mapRow(ResultSet rs, int index) throws SQLException {
            Game g = new Game();
            g.setGameId(rs.getInt("Id"));
            g.setGameAnswer(rs.getString("Answer"));
            g.setGameStatus(rs.getString("GameStatus"));

            return g;
        }
    }

    @Override
    @Transactional
    public Game addGame(String answer) {

        GeneratedKeyHolder holder = new GeneratedKeyHolder();
        jdbc.update((Connection conn) -> {
            PreparedStatement ps = conn.prepareStatement(
                    "INSERT INTO game(Answer, GameStatus) values(?,?)",
                    Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, answer);
            ps.setString(2, "Progress");
            return ps;
        }, holder);

        int newId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        return findGameById(newId);
    }

    @Override
    public List<Game> getAllGames() {
        final String SELECT_ALL_GAMES = "SELECT * FROM game";
        List<Game> games = jdbc.query(SELECT_ALL_GAMES, new GameMapper());
        return games;
    }

    @Override
    public Game findGameById(int id) {
        try {
            final String SELECT_GAME_BY_ID = "SELECT * FROM game WHERE id = ?";
            Game g = jdbc.queryForObject(SELECT_GAME_BY_ID, new GameMapper(), id);
            return g;
        } catch (DataAccessException ex) {
            return null;
        }
    }

    @Override
    public void updateGame(Game game) {
        final String UPDATE_GAME = "UPDATE game SET Answer = ?, GameStatus = ? WHERE id = ?";
        jdbc.update(UPDATE_GAME,
                game.getGameAnswer(),
                game.getGameStatus(),
                game.getGameId()
        );
    }
}
