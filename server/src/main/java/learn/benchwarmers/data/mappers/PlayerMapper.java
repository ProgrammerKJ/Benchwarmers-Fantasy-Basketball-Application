package learn.benchwarmers.data.mappers;

import learn.benchwarmers.models.Player;
import learn.benchwarmers.models.Team;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PlayerMapper implements RowMapper<Player> {

    @Override
    public Player mapRow(ResultSet resultSet, int i) throws SQLException {
        Player player = new Player();
        player.setPlayerId(resultSet.getInt("p.player_id"));
        player.setFirstName(resultSet.getString("p.first_name"));
        player.setLastName(resultSet.getString("p.last_name"));
        player.setPosition(resultSet.getString("p.position"));
        player.setJerseyNumber(resultSet.getInt("p.jersey_number"));
        player.setFantasyPoints(resultSet.getDouble("p.fantasy_points"));
        player.setTeam(Team.valueOf(resultSet.getString("t.short_name").toUpperCase()));
        return player;
    }
}
