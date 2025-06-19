package learn.benchwarmers.data.mappers;

import learn.benchwarmers.models.FantasyLeague;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class FantasyLeagueMapper implements RowMapper<FantasyLeague> {

    @Override
    public FantasyLeague mapRow(ResultSet resultSet, int i) throws SQLException {
        FantasyLeague fantasyLeague = new FantasyLeague();
        fantasyLeague.setFantasyLeagueId(resultSet.getInt("fantasy_league_id"));
        fantasyLeague.setName(resultSet.getString("name"));
        fantasyLeague.setSeason(resultSet.getInt("season"));
        return fantasyLeague;
    }
}
