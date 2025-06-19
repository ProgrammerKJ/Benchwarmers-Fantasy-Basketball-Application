package learn.benchwarmers.data.mappers;

import learn.benchwarmers.models.Role;
import learn.benchwarmers.models.Team;
import learn.benchwarmers.models.AppUser;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class AppUserMapper implements RowMapper<AppUser> {
    //fields
    private final List<String> roles;

    //constructor
    public AppUserMapper(List<String> roles) {
        this.roles = roles;
    }

    @Override
    public AppUser mapRow(ResultSet resultSet, int i) throws SQLException {
        if(roles.isEmpty()) {
            roles.add(resultSet.getString("r.name"));
        }
        AppUser appUser = new AppUser(
                resultSet.getInt("u.user_id"),
                resultSet.getString("u.username"),
                resultSet.getString("u.password_hash"),
                resultSet.getBoolean("u.disabled"),
                roles
        );
        appUser.setEmail(resultSet.getString("u.email"));
        appUser.setFavoriteTeam(Team.valueOf(resultSet.getString("t.short_name").toUpperCase()));
        return appUser;
    }

}
