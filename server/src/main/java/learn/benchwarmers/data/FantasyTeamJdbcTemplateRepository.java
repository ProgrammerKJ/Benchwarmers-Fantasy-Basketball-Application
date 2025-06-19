package learn.benchwarmers.data;

import learn.benchwarmers.data.mappers.FantasyTeamMapper;
import learn.benchwarmers.data.mappers.PlayerMapper;
import learn.benchwarmers.data.mappers.AppUserMapper;
import learn.benchwarmers.models.FantasyTeam;
import learn.benchwarmers.models.Player;
import learn.benchwarmers.models.AppUser;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

@Repository
public class FantasyTeamJdbcTemplateRepository implements FantasyTeamRepository{
    //fields
    private JdbcTemplate jdbcTemplate;

    //constructor with DI
    public FantasyTeamJdbcTemplateRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    //Read
    //returns a list of all fantasy teams
    @Override
    public List<FantasyTeam> findAll() {
        final String sql = "select distinct fantasy_team_id, fantasy_league_id from fantasy_team;";
        List<FantasyTeam> teams =  jdbcTemplate.query(sql, new FantasyTeamMapper());
        teams.forEach((team) -> {
            addUser(team);
            addPlayers(team);
            List<Player> players = team.getPlayers();
            double sum = 0;
            for(Player player: players){
                sum += player.getFantasyPoints();
            }
            team.setTotalPoints(sum);
        });

        return teams;
    }

    //Create
    //adds a new player to a specific fantasy team
    @Override
    public FantasyTeam addPlayer(FantasyTeam fantasyTeam, Player player) {
        //verify the fantasy team is not null
        if(fantasyTeam == null){
            return null;
        }

        //verify the player is not null
        if(player == null){
            return null;
        }
        final String sql = "insert into fantasy_team (fantasy_team_id, user_id, player_id, fantasy_league_id) " +
                "values (?,?,?,?);";



        int rowsAffected = jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, fantasyTeam.getFantasyTeamId());
            ps.setInt(2, fantasyTeam.getUser().getUserId());
            ps.setInt(3, player.getPlayerId());
            ps.setInt(4, fantasyTeam.getFantasyLeague().getFantasyLeagueId());
            return ps;
        });

        //verify the create operation was successful
        if(rowsAffected <= 0){
            return null;
        }

        fantasyTeam.getPlayers().add(player); //add the player to the list of players in fantasy team
        return fantasyTeam;
    }

    //adds a fantasy team to the database
    @Override
    public FantasyTeam addFantasyTeam(FantasyTeam fantasyTeam, Player player) {
        //verify fantasy team is not null
        if(fantasyTeam == null){
            return null;
        }

        //verify player is not null
        if(player == null){
            return null;
        }

        int rowsAffected = 0;
        KeyHolder keyHolder = new GeneratedKeyHolder();

        //if the team is a new team, don't add a fantasy team id
        if(fantasyTeam.getFantasyTeamId() <= 0 ) {
            final String sql = "insert into fantasy_team (user_id, player_id, fantasy_league_id) " +
                    "values (?,?,?);";
            rowsAffected = jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
                ps.setInt(1, fantasyTeam.getUser().getUserId());
                ps.setInt(2, player.getPlayerId());
                ps.setInt(3, fantasyTeam.getFantasyLeague().getFantasyLeagueId());
                return ps;
            }, keyHolder);
        }else{ //the team already exists, so add the new player on the same fantasy team id
            final String sql = "insert into fantasy_team (fantasy_team_id, user_id, player_id, fantasy_league_id) " +
                    "values (?,?,?,?);";
            rowsAffected = jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
                ps.setInt(1, fantasyTeam.getFantasyTeamId());
                ps.setInt(2, fantasyTeam.getUser().getUserId());
                ps.setInt(3, player.getPlayerId());
                ps.setInt(4, fantasyTeam.getFantasyLeague().getFantasyLeagueId());
                return ps;
            }, keyHolder);

        }


        //verify the create operation was successful
        if(rowsAffected <= 0){
            return null;
        }

        //verify the players are not null, otherwise create a new list for players
        if(fantasyTeam.getPlayers() == null){
            fantasyTeam.setPlayers(new ArrayList<>());
        }

        fantasyTeam.getPlayers().add(player); //add the player on the list for the team
        fantasyTeam.setFantasyTeamId(keyHolder.getKey().intValue());
        return fantasyTeam;
    }


    //Update
    //update a player on a specific fantasy team
    @Override
    public boolean updatePlayer(FantasyTeam fantasyTeam, Player player) {
        //verify the fantasy team is not null
        if(fantasyTeam == null){
            return false;
        }

        //verify the player is not null
        if(player == null){
            return false;
        }
        final String sql = "update fantasy_team set " +
                "player_id = ? " +
                "where fantasy_team_id= ?;";


        return jdbcTemplate.update(sql,
                player.getPlayerId(),
                fantasyTeam.getFantasyTeamId()) > 0;
    }

    //Delete
    //delete a fantasy team by an id
    @Override
    public boolean deleteById(int fantasyTeamId) {
        //delete the fantasy team, return true if was successful
        return jdbcTemplate.update("delete from fantasy_team where fantasy_team_id = ?;", fantasyTeamId) > 0;
    }

    //delete a player from the team
    @Override
    public boolean deleteByPlayerFromTeam(int fantasyTeamId, int playerId) {
        //delete the fantasy team, return true if was successful
        return jdbcTemplate.update("delete from fantasy_team where fantasy_team_id = ? and player_id = ?;",
                fantasyTeamId,
                playerId) > 0;
    }


    //helper methods
    //update fantasy team to include user details
    private void addUser(FantasyTeam fantasyTeam){
        final String sql = "select distinct u.user_id, u.username, u.email, u.disabled, " +
                "r.name, t.short_name, u.password_hash " +
                "from fantasy_team ft " +
                "inner join `user` u " +
                "on ft.user_id = u.user_id " +
                "inner join app_user_role au " +
                "on u.user_id = au.user_id " +
                "inner join role r " +
                "on r.role_id = au.role_id " +
                "inner join team t " +
                "on u.favorite_team_id = t.team_id " +
                "where ft.fantasy_team_id = ?;";

        List<String> roles = new ArrayList<>();


        AppUser appUser = jdbcTemplate.query(sql, new AppUserMapper(roles), fantasyTeam.getFantasyTeamId())
                .stream().findFirst().orElse(null);

        fantasyTeam.setUser(appUser);
    }

    //update fantasy team to include players details for a specific team
    private void addPlayers(FantasyTeam fantasyTeam){
        final String sql = "select p.player_id, p.first_name, p.last_name, " +
                "p.position, p.jersey_number, p.fantasy_points, t.short_name " +
                "from fantasy_team ft " +
                "inner join player p " +
                "on ft.player_id = p.player_id " +
                "inner join team t " +
                "on p.team_id = t.team_id " +
                "where ft.fantasy_team_id = ?;";

        List<Player> players = jdbcTemplate.query(sql, new PlayerMapper(), fantasyTeam.getFantasyTeamId());
        fantasyTeam.setPlayers(players);
    }
}
