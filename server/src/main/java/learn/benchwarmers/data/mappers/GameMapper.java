package learn.benchwarmers.data.mappers;

import learn.benchwarmers.models.Game;
import learn.benchwarmers.models.Team;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class GameMapper implements RowMapper<Game> {
    @Override
    public Game mapRow(ResultSet resultSet, int i) throws SQLException {
        Game game = new Game();
        game.setGameId(resultSet.getInt("g.game_id"));
        game.setSeason(resultSet.getInt("g.season"));
        game.setHomeTeam(Team.valueOf(resultSet.getString("Home Team").toUpperCase()));
        game.setAwayTeam(Team.valueOf(resultSet.getString("Away Team").toUpperCase()));
        game.setHomeTeamScore(resultSet.getInt("g.home_team_score"));
        game.setAwayTeamScore(resultSet.getInt("g.away_team_score"));
        game.setGameDate(resultSet.getDate("g.game_date").toLocalDate());
        return game;
    }
}
