package learn.benchwarmers.data;

import learn.benchwarmers.models.Player;

import java.util.List;

public interface PlayerRepository {
    List<Player> findByPosition(String position);
    List<Player> findAll();
    Player add(Player player);
    boolean update(Player player);
    boolean deleteById(int playerId);
}
