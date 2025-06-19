package learn.benchwarmers.data;

import learn.benchwarmers.data.mappers.FantasyLeagueMapper;
import learn.benchwarmers.data.mappers.AppUserMapper;
import learn.benchwarmers.models.FantasyLeague;
import learn.benchwarmers.models.AppUser;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

@Repository
public class FantasyLeagueJdbcTemplateRepository implements FantasyLeagueRepository{
    //fields
    private JdbcTemplate jdbcTemplate;

    //constructor with DI
    public FantasyLeagueJdbcTemplateRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    //Read
    //returns a list of all leagues
    @Override
    public List<FantasyLeague> findAll() {
        final String sql = "select fantasy_league_id, name, season " +
                "from fantasy_league;";

        List<FantasyLeague> leagues = jdbcTemplate.query(sql, new FantasyLeagueMapper());

        //add the admin and member details for a league
        leagues.forEach((league) -> {
            addAdmin(league);
            addMembers(league);
        });

        return leagues;
    }

    //return the league by the member id
    @Override
    public List<FantasyLeague> findByMemberId(int memberId) {
        final String sql = "select fl.fantasy_league_id, fl.name, fl.season " +
                "from fantasy_league fl " +
                "inner join league_members lm " +
                "on fl.fantasy_league_id = lm.fantasy_league_id " +
                "where lm.user_id = ?;";
        return jdbcTemplate.query(sql, new FantasyLeagueMapper(), memberId);
    }

    //return the league by the admin id
    @Override
    public List<FantasyLeague> findByAdminId(int adminId) {
        final String sql = "select fantasy_league_id, name, season " +
                "from fantasy_league " +
                "where admin_id = ?;";
        return jdbcTemplate.query(sql, new FantasyLeagueMapper(), adminId);
    }

    //Create
    //add a fantasy league to the database
    @Override
    public FantasyLeague add(FantasyLeague fantasyLeague) {
        //verify the league is not null
        if(fantasyLeague == null){
            return null;
        }
        final String sql = "insert into fantasy_league (`name`, season, admin_id) " +
                "values (?,?,?);";

        KeyHolder keyHolder = new GeneratedKeyHolder();

        int rowsAffected = jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            ps.setString(1, fantasyLeague.getName());
            ps.setInt(2, fantasyLeague.getSeason());
            ps.setInt(3, fantasyLeague.getAdmin().getUserId());

            return ps;
        }, keyHolder);

        //verify the create operation was successful
        if(rowsAffected <= 0){
            return null;
        }

        fantasyLeague.setFantasyLeagueId(keyHolder.getKey().intValue());
        return fantasyLeague;
    }

    //Update
    //update the fantasy league from database, return true if was successful
    @Override
    public boolean update(FantasyLeague fantasyLeague) {
        //verify the league is not null
        if(fantasyLeague == null){
            return false;
        }
        final String sql = "update fantasy_league set " +
                "name = ?," +
                "season = ? " +
                "where fantasy_league_id = ?;";


        return jdbcTemplate.update(sql,
                fantasyLeague.getName(),
                fantasyLeague.getSeason(),
                fantasyLeague.getFantasyLeagueId()) > 0;
    }



    //Delete
    //delete fantasy league from database, return true if was successful
    @Override
    @Transactional
    public boolean deleteById(int fantasyLeagueId) {
        //delete any data that references the fantasy league
        jdbcTemplate.update("delete from league_members where fantasy_league_id = ?;", fantasyLeagueId);
        jdbcTemplate.update("delete from fantasy_team where fantasy_league_id = ?;", fantasyLeagueId);

        //delete the fantasy league, return true if was successful
        return jdbcTemplate.update("delete from fantasy_league where fantasy_league_id = ?;", fantasyLeagueId) > 0;
    }


    //helper methods
    //update fantasy league to include admin details
    private void addAdmin(FantasyLeague fantasyLeague){
        final String sql = "select u.user_id, u.username, u.email, " +
                "u.disabled, r.name, t.short_name, u.password_hash " +
                "from fantasy_league fl " +
                "inner join `user` u " +
                "on fl.admin_id = u.user_id " +
                "inner join app_user_role au " +
                "on u.user_id = au.user_id " +
                "inner join role r " +
                "on r.role_id = au.role_id " +
                "inner join team t " +
                "on u.favorite_team_id = t.team_id " +
                "where fl.fantasy_league_id = ?;";

        List<String> roles = new ArrayList<>();

        AppUser admin = jdbcTemplate.query(sql, new AppUserMapper(roles), fantasyLeague.getFantasyLeagueId()).stream().findFirst().orElse(null);
        fantasyLeague.setAdmin(admin);
    }

    //update fantasy league to include all members details
    private void addMembers(FantasyLeague fantasyLeague){
        final String sql = "select u.user_id, u.username, u.email, " +
                "u.disabled, r.name, t.short_name, u.password_hash " +
                "from fantasy_league fl " +
                "inner join league_members lm " +
                "on fl.fantasy_league_id = lm.fantasy_league_id " +
                "inner join `user` u " +
                "on lm.user_id = u.user_id " +
                "inner join app_user_role au " +
                "on u.user_id = au.user_id " +
                "inner join role r " +
                "on r.role_id = au.role_id " +
                "inner join team t " +
                "on u.favorite_team_id = t.team_id " +
                "where fl.fantasy_league_id = ?;";

        List<String> roles = new ArrayList<>();

        List<AppUser> members  = jdbcTemplate.query(sql, new AppUserMapper(roles), fantasyLeague.getFantasyLeagueId());

        fantasyLeague.setMembers(members);
    }

}
