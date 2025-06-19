package learn.benchwarmers.data;

import learn.benchwarmers.data.mappers.PlayerMapper;
import learn.benchwarmers.models.Player;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

@Repository
public class PlayerJdbcTemplateRepository implements PlayerRepository{
    //fields
    private JdbcTemplate jdbcTemplate;

    //constructor with DI
    public PlayerJdbcTemplateRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    //Read
    //return list of all players for a specific position
    @Override
    public List<Player> findByPosition(String position) {
        //verify the position string is not blank or empty
        if(position.isBlank() || position.isEmpty()){
            return null;
        }
        final String sql = "select p.player_id, p.first_name, p.last_name, " +
                "p.position, p.jersey_number, p.fantasy_points, t.short_name " +
                "from player p " +
                "inner join team t " +
                "on p.team_id = t.team_id " +
                "where p.position = ?;";

        return jdbcTemplate.query(sql, new PlayerMapper(), position);
    }

    //return list of all players
    @Override
    public List<Player> findAll() {
        final String sql = "select p.player_id, p.first_name, p.last_name, " +
                "p.position, p.jersey_number, p.fantasy_points, t.short_name " +
                "from player p " +
                "inner join team t " +
                "on p.team_id = t.team_id;";
        return jdbcTemplate.query(sql, new PlayerMapper());
    }

    //Create
    //add player to the database
    @Override
    public Player add(Player player) {
        //verify the player is not null
        if(player == null){
            return null;
        }
        final String sql = "insert into player (first_name, last_name, position, jersey_number, fantasy_points, team_id)" +
                "values (?,?,?,?,?,?);";

        KeyHolder keyHolder = new GeneratedKeyHolder();

        int rowsAffected = jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, player.getFirstName());
            ps.setString(2, player.getLastName());
            ps.setString(3, player.getPosition());
            ps.setInt(4, player.getJerseyNumber());
            ps.setDouble(5, player.getFantasyPoints());
            ps.setInt(6, player.getTeam().getTeamId());
            return ps;
        }, keyHolder);

        //verify the create operation was successful
        if(rowsAffected <= 0){
            return null;
        }

        player.setPlayerId(keyHolder.getKey().intValue());
        return player;
    }

    //Update
    //update player in the database, return true if was successful
    @Override
    public boolean update(Player player) {
        //verify the player is not null
        if(player == null){
            return false;
        }
        final String sql = "update player set " +
                "first_name = ?, " +
                "last_name = ?, " +
                "position = ?, " +
                "jersey_number = ?, " +
                "fantasy_points = ?, " +
                "team_id = ? " +
                "where player_id = ?;";

        return jdbcTemplate.update(sql,
                player.getFirstName(),
                player.getLastName(),
                player.getPosition(),
                player.getJerseyNumber(),
                player.getFantasyPoints(),
                player.getTeam().getTeamId(),
                player.getPlayerId()) > 0;
    }

    //Delete
    //delete player in the database, return true if was successful
    @Override
    @Transactional
    public boolean deleteById(int playerId) {
        //delete any data that references the player
        jdbcTemplate.update("delete from fantasy_team where player_id = ?;", playerId);

        //delete the player, return true if was successful
        return jdbcTemplate.update("delete from player where player_id = ?;", playerId) > 0;
    }


}
