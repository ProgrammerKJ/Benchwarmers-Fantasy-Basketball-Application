package learn.benchwarmers.data;

import learn.benchwarmers.App;
import learn.benchwarmers.data.mappers.AppUserMapper;
import learn.benchwarmers.models.AppUser;
import learn.benchwarmers.models.Role;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@Repository
public class AppUserJdbcTemplateRepository implements AppUserRepository {
    //fields
    private final JdbcTemplate jdbcTemplate;

    //constructor with DI
    public AppUserJdbcTemplateRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    //Read
    //return list of all users
    @Override
    public List<AppUser> findAll() {
        final String sql = "select u.user_id, u.username, u.email,  u.disabled, " +
                "r.name, t.short_name, u.password_hash " +
                "from user u " +
                "inner join app_user_role au " +
                "on u.user_id = au.user_id " +
                "inner join role r " +
                "on r.role_id = au.role_id " +
                "inner join team t " +
                "on u.favorite_team_id = t.team_id;";
        List<String> roles = new ArrayList<>();

        return jdbcTemplate.query(sql, new AppUserMapper(roles));
    }

    //return user for a specific userId
    @Override
    public AppUser findByUsername(String username) {
        if(username.isBlank() || username.isEmpty()){
            return null;
        }

        //get the roles for a user
        List<String> roles = getRolesByUsername(username);
        final String sql = "select u.user_id, u.username, u.email,  u.disabled, " +
                "r.name, t.short_name, u.password_hash " +
                "from user u " +
                "inner join app_user_role au " +
                "on u.user_id = au.user_id " +
                "inner join role r " +
                "on r.role_id = au.role_id " +
                "inner join team t " +
                "on u.favorite_team_id = t.team_id " +
                "where u.username = ?;";
        return jdbcTemplate.query(sql, new AppUserMapper(roles), username).stream().findFirst().orElse(null);
    }

    //Create
    //add user to the database
    @Override
    @Transactional
    public AppUser add(AppUser appUser) {
        //verify the user is not null
        if(appUser == null){
            return null;
        }

        final String sql = "insert into `user` (username, email, favorite_team_id, password_hash, disabled)" +
                "values (?,?,?,?,?);";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        int rowsAffected = jdbcTemplate.update( connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, appUser.getUsername());
            ps.setString(2, appUser.getEmail());
            ps.setInt(3, appUser.getFavoriteTeam().getTeamId());
            ps.setString(4, appUser.getPassword());
            ps.setBoolean(5, appUser.isEnabled());
            return ps;
        }, keyHolder);

        //verify the create operation was successful
        if(rowsAffected <= 0){
            return null;
        }

        appUser.setUserId(keyHolder.getKey().intValue()); //update the user id
        updateRoles(appUser); //update the roles for the user
        return appUser;
    }

    //Update
    //update user in the database, return true if was successful
    @Override
    public boolean update(AppUser appUser, int roleId) {
        //verify the user is not null
        if(appUser == null){
            return false;
        }

        final String sql = "update user u " +
                "inner join app_user_role au " +
                "on u.user_id = au.user_id " +
                "set " +
                "u.email = ?, " +
                "u.favorite_team_id = ?, " +
                "u.password_hash = ?, " +
                "u.disabled = ?, " +
                "au.role_id = ?  " +
                "where u.user_id = ?;";

        //if update was successfully, return true
        return jdbcTemplate.update(sql,
                appUser.getEmail(),
                appUser.getFavoriteTeam().getTeamId(),
                appUser.getPassword(),
                appUser.isEnabled(),
                roleId,
                appUser.getUserId()) > 0;
    }

    //Delete
    //delete user from database, return true if was successful
    @Override
    @Transactional
    public boolean deleteById(int userId) {
        //delete any data that references the user
        jdbcTemplate.update("delete from fantasy_team where user_id = ?;", userId);
        jdbcTemplate.update("delete from league_members " +
                        "where fantasy_league_id " +
                        "in (select distinct fantasy_league_id " +
                        "from fantasy_league where admin_id = ?);", userId);
        jdbcTemplate.update("delete from league_members where user_id = ?;", userId);
        jdbcTemplate.update("delete from fantasy_league where admin_id = ?;", userId);
        jdbcTemplate.update("delete from app_user_role where user_id = ?;", userId);

        //delete the user, return true if was successful
        return jdbcTemplate.update("delete from user where user_id = ?;", userId) > 0;
    }

    //helper methods
    //updates the roles for the user
    private void updateRoles(AppUser user) {
        // delete all roles, then re-add
        jdbcTemplate.update("delete from app_user_role where user_id = ?;", user.getUserId());

        Collection<GrantedAuthority> authorities = user.getAuthorities();

        //verify authorities is not null
        if (authorities == null) {
            return;
        }

        for (String role : AppUser.convertAuthoritiesToRoles(authorities)) {
            String sql = "insert into app_user_role (user_id, role_id) " +
                    "select ?, role_id from role where `name` = ?;";
            jdbcTemplate.update(sql, user.getUserId(), role);
        }
    }

    //returns a list of roles for a user
    private List<String> getRolesByUsername(String username) {
        final String sql = "select r.name " +
                "from app_user_role ur " +
                "inner join `role` r on ur.role_id = r.role_id " +
                "inner join `user` u on ur.user_id = u.user_id " +
                "where u.username = ?;";
        return jdbcTemplate.query(sql, (rs, rowId) -> rs.getString("r.name"), username);
    }
}
