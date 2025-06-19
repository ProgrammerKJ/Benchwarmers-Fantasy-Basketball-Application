package learn.benchwarmers.domain;

import learn.benchwarmers.data.GameRepository;
import learn.benchwarmers.models.Game;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class GameService {
    private final GameRepository repository;

    public GameService(GameRepository repository){
        this.repository = repository;
    }

    public List<Game> findAll(){
        return repository.findAll();
    }

    public List<Game> findByDate(LocalDate date){
        if(date == null){
            throw new IllegalArgumentException("Date is required.");
        }

        return repository.findByDate(date);
    }

    public List<Game> findByTeam(String team){
        if(team == null || team.isBlank()){
            throw new IllegalArgumentException("Team is required.");
        }

        return repository.findByTeam(team);
    }

    public Result<Game> add(Game game){
        Result<Game> result = validate(game);

        if(!result.isSuccess()){
            return result;
        }

        if(game.getGameId() != 0){
            result.addErrorMessage("Game ID cannot be set when adding a new game.");
            return result;
        }

        game = repository.add(game);
        result.setPayload(game);
        return result;
    }

    public Result<Game> update(Game game){
        Result<Game> result = validate(game);

        if(!result.isSuccess()){
            return result;
        }

        if(game.getGameId() <= 0){
            result.addErrorMessage("Game ID is required when updating a game.");
            return result;
        }

        boolean success = repository.update(game);

        if(!success){
            result.addErrorMessage("Game not found.");
        }

        return result;
    }

    public boolean deleteById(int gameId){
        if(gameId <= 0){
            return false;
        }

        return repository.deleteById(gameId);
    }

    public Result<Game> validate(Game game){
        Result<Game> result = new Result<>();

        if(game == null){
            result.addErrorMessage("A Game cannot be null.");
            return result;
        }

        if(game.getSeason() <= 0){
            result.addErrorMessage("Season must be greater than 0.");
        }

        if(game.getHomeTeam() == null){
            result.addErrorMessage("Home team is required.");
        }

        if(game.getAwayTeam() == null){
            result.addErrorMessage("Away team is required.");
        }

        if(game.getHomeTeam() != null && game.getAwayTeam() != null &&
            game.getHomeTeam().equals(game.getAwayTeam())){
            result.addErrorMessage("Home and Away Teams cannot be the same.");
        }

        if(game.getHomeTeamScore() < 0 || game.getAwayTeamScore() < 0){
            result.addErrorMessage("A team score cannot be negative.");
        }

        if(game.getGameDate() == null){
            result.addErrorMessage("A game must have a date.");
        }

        // If the game is in the future, the score must be 0
        if(game.getGameDate().isAfter(LocalDate.now())){
            if(game.getHomeTeamScore() != 0 || game.getAwayTeamScore() != 0){
                result.addErrorMessage("For games that have not been played yet, scores must be 0");
            }
        }

        return result;
    }


}
