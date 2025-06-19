package learn.benchwarmers.data;

import learn.benchwarmers.models.AppUser;
import learn.benchwarmers.models.FantasyLeague;

import java.util.List;

public interface FantasyLeagueRepository {
    List<FantasyLeague> findAll();
    List<FantasyLeague> findByMemberId(int memberId);
    List<FantasyLeague> findByAdminId(int adminId);
    FantasyLeague add(FantasyLeague fantasyLeague);
    boolean update(FantasyLeague fantasyLeague);
    boolean deleteById(int fantasyLeagueId);
}
