package learn.benchwarmers.data;

import learn.benchwarmers.data.mappers.GameMapper;
import learn.benchwarmers.models.Game;
import learn.benchwarmers.models.Team;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Repository
public class GameJdbcTemplateRepository implements GameRepository{
    //fields
    private JdbcTemplate jdbcTemplate;

    //constructor with DI
    public GameJdbcTemplateRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    //Read
    //returns list of all games for a specific date
    @Override
    public List<Game> findByDate(LocalDate date) {
        final String sql = "select g.game_id, g.season, h.short_name as 'Home Team', " +
                "a.short_name as 'Away Team', g.home_team_score, g.away_team_score, g.game_date " +
                "from game g " +
                "inner join team h " +
                "on g.home_team_id = h.team_id " +
                "inner join team a " +
                "on g.away_team_id = a.team_id " +
                "where g.game_date = ?;";

        return jdbcTemplate.query(sql, new GameMapper(), date);
    }

    //returns list of all games
    @Override
    public List<Game> findAll() {
        final String sql = "select g.game_id, g.season, h.short_name as 'Home Team', " +
                "a.short_name as 'Away Team', g.home_team_score, g.away_team_score, g.game_date " +
                "from game g " +
                "inner join team h " +
                "on g.home_team_id = h.team_id " +
                "inner join team a " +
                "on g.away_team_id = a.team_id;";

        return jdbcTemplate.query(sql, new GameMapper());
    }

    //returns list of all games for a specific team
    @Override
    public List<Game> findByTeam(String team) {
        //verify if the team string is empty or blank
        if(team.isBlank() || team.isEmpty()){
            return null;
        }
        final String sql = "select g.game_id, g.season, h.short_name as 'Home Team', " +
                "a.short_name as 'Away Team', g.home_team_score, g.away_team_score, g.game_date " +
                "from game g " +
                "inner join team h " +
                "on g.home_team_id = h.team_id " +
                "inner join team a " +
                "on g.away_team_id = a.team_id " +
                "where g.home_team_id = ? or g.away_team_id = ?;";

        return jdbcTemplate.query(sql, new GameMapper(), Team.valueOf(team));
    }

    //Create
    //adds the game to the database
    @Override
    public Game add(Game game) {
        //verify the game is not null
        if(game == null){
            return null;
        }
        final String sql = "insert into game (season, home_team_id, away_team_id, home_team_score, away_team_score, game_date) " +
                "values (?,?,?,?,?,?);";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        int rowsAffected = jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, game.getSeason());
            ps.setInt(2, game.getHomeTeam().getTeamId());
            ps.setInt(3, game.getAwayTeam().getTeamId());
            ps.setInt(4, game.getHomeTeamScore());
            ps.setInt(5, game.getAwayTeamScore());
            ps.setDate(6, Date.valueOf(game.getGameDate()));
            return ps;
        }, keyHolder);

        //verify the create operation was successful
        if(rowsAffected <= 0){
            return null;
        }

        game.setGameId(keyHolder.getKey().intValue());
        return game;
    }

    //Update
    //update the game in the database, return true if was successful
    @Override
    public boolean update(Game game) {
        //verify the game is not null
        if(game == null){
            return false;
        }
        final String sql = "update game set " +
                "home_team_score = ?, " +
                "away_team_score = ?, " +
                "game_date = ? " +
                "where game_id = ?;";

        return jdbcTemplate.update(sql,
                game.getHomeTeamScore(),
                game.getAwayTeamScore(),
                game.getGameDate(),
                game.getGameId()) > 0;
    }

    //Delete
    //deletes the game in the database, return true if was successful
    @Override
    public boolean deleteById(int gameId) {
        return jdbcTemplate.update("delete from game where game_id = ?;", gameId) > 0;
    }


}
