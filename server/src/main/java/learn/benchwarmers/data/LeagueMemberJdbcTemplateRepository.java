package learn.benchwarmers.data;

import learn.benchwarmers.models.FantasyLeague;
import learn.benchwarmers.models.AppUser;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;

@Repository
public class LeagueMemberJdbcTemplateRepository implements LeagueMemberRepository{
    //fields
    private JdbcTemplate jdbcTemplate;

    //constructor with DI
    public LeagueMemberJdbcTemplateRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    //Create
    //adds a user to the league
    @Override
    public FantasyLeague add(FantasyLeague fantasyLeague, AppUser appUser) {
        //verify the league was not null
        if(fantasyLeague == null){
            return null;
        }

        //verify the app user is not null
        if(appUser == null){
            return null;
        }
        final String sql = "insert into league_members (fantasy_league_id, user_id) " +
                "values (?,?);";

        int rowsAffected = jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, fantasyLeague.getFantasyLeagueId());
            ps.setInt(2, appUser.getUserId());
            return ps;
        });

        //verify the create operation was successful
        if(rowsAffected <= 0){
            return fantasyLeague;
        }

        fantasyLeague.getMembers().add(appUser);
        return fantasyLeague;
    }

    //Delete
    //delete the member from a league, return true if was successful
    @Override
    public boolean deleteById(int fantasyLeagueId, int userId) {
        return jdbcTemplate.update("delete from league_members " +
                "where fantasy_league_id = ? and user_id = ?; ",
                fantasyLeagueId, userId) > 0;
    }
}
