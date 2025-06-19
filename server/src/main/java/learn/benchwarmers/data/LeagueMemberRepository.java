package learn.benchwarmers.data;

import learn.benchwarmers.models.FantasyLeague;
import learn.benchwarmers.models.AppUser;

public interface LeagueMemberRepository {
    FantasyLeague add(FantasyLeague fantasyLeague, AppUser appUser);
    boolean deleteById(int fantasyLeagueId, int userId);
}
