package learn.benchwarmers.data;

import learn.benchwarmers.models.FantasyTeam;
import learn.benchwarmers.models.Player;

import java.util.List;

public interface FantasyTeamRepository {
    List<FantasyTeam> findAll();
    FantasyTeam addPlayer(FantasyTeam fantasyTeam, Player player);
    FantasyTeam addFantasyTeam(FantasyTeam fantasyTeam, Player player);
    boolean updatePlayer(FantasyTeam fantasyTeam, Player player);
    boolean deleteById(int fantasyTeamId);
    boolean deleteByPlayerFromTeam(int fantasyTeamId, int playerId);
}
