package learn.benchwarmers.data;

import learn.benchwarmers.models.Game;

import java.time.LocalDate;
import java.util.List;

public interface GameRepository {
    List<Game> findByDate(LocalDate date);
    List<Game> findAll();
    List<Game> findByTeam(String team);
    Game add(Game game);
    boolean update(Game game);
    boolean deleteById(int gameId);
}
