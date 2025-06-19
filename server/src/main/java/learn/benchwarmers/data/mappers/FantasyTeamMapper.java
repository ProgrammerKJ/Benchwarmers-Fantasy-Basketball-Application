package learn.benchwarmers.data.mappers;

import learn.benchwarmers.models.FantasyLeague;
import learn.benchwarmers.models.FantasyTeam;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class FantasyTeamMapper implements RowMapper<FantasyTeam> {
    @Override
    public FantasyTeam mapRow(ResultSet resultSet, int i) throws SQLException {
        FantasyTeam fantasyTeam = new FantasyTeam();
        fantasyTeam.setFantasyTeamId(resultSet.getInt("fantasy_team_id"));

        FantasyLeague fantasyLeague = new FantasyLeague();
        fantasyLeague.setFantasyLeagueId(resultSet.getInt("fantasy_league_id"));
        fantasyTeam.setFantasyLeague(fantasyLeague);
        return fantasyTeam;
    }
}
