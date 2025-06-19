package learn.benchwarmers.domain;

import learn.benchwarmers.data.PlayerRepository;
import learn.benchwarmers.models.Player;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class PlayerService {
    private final PlayerRepository repository;
    private final NbaApiService nbaApiService;

    // Added NbaApiService as dependency for API integration
    public PlayerService(PlayerRepository repository, NbaApiService nbaApiService) {
        this.repository = repository;
        this.nbaApiService = nbaApiService;
    }

    // List of all players from the database
    public List<Player> findAll() {
        return repository.findAll();
    }

    // List of top 25 leaders in points
    public List<Player> findTopPointsLeaders(){
        return nbaApiService.getTopPointsLeaders();
    }

    // List of players by position
    public List<Player> findByPosition(String position){
        if (position == null || position.isBlank()){
            throw new IllegalArgumentException("Position is required.");
        }

        return repository.findByPosition(position);
    }

//    public Player findById(int playerId){
//        return repository.findAll().stream()
//                .filter(p -> p.getPlayerId() == playerId)
//                .findFirst()
//                .orElse(null);
//    }

    // Find a player in the database by first and last name
    public Player findByName(String firstName, String lastName){
        List<Player> players = repository.findAll();
        return repository.findAll().stream()
                .filter(p -> p.getFirstName().equalsIgnoreCase(firstName) &&
                            p.getLastName().equalsIgnoreCase(lastName))
                .findFirst()
                .orElse(null);
    }

    // Boolean Function to check if we have this player in the database already
    public boolean isPlayerInDatabase(String firstName, String lastName){
        return findByName(firstName, lastName) != null;
    }

    // Function to add a player from the API to our database
    public Result<Player> addPlayerFromApi(String firstName, String lastName){
        // Checking if the player already exists in the database
        if(isPlayerInDatabase(firstName, lastName)){
            Player existingPlayer = findByName(firstName, lastName);
            Result<Player> result = new Result<>();
            result.setPayload(existingPlayer);
            return result;
        }

        // Find the player in the API and add to database
        Player apiPlayer = nbaApiService.findPlayerByName(firstName, lastName);
        if(apiPlayer == null){
            Result<Player> result = new Result<>();
            result.addErrorMessage("Player was not found in the api");
            return result;
        }

        return add(apiPlayer);
    }

    // Add a player method
    public Result<Player> add(Player player){
        Result<Player> result = validate(player);

        if(!result.isSuccess()){
            return result;
        }

        player = repository.add(player);
        result.setPayload(player);
        return result;
    }

    // Update a player
    public Result<Player> update(Player player){
        Result<Player> result = validate(player);

        if(!result.isSuccess()){
            return result;
        }

        if(player.getPlayerId() <= 0){
            result.addErrorMessage("Player ID is required when updating a player.");
            return result;
        }

        boolean success = repository.update(player);
        if(!success){
            result.addErrorMessage("Player not found.");
        }

        return result;
    }

    // Delete by ID
    public boolean deleteById(int playerId){
        if (playerId <= 0){
            return false;
        }
        return repository.deleteById(playerId);
    }

    // Validate a Player
    public Result<Player> validate(Player player){
        Result<Player> result = new Result<>();

        if (player == null){
            result.addErrorMessage("A player cannot be null.");
            return result;
        }

        if(player.getFirstName() == null || player.getFirstName().isBlank()){
            result.addErrorMessage("First name is required.");
        } else if(player.getFirstName().length() > 250){
            result.addErrorMessage("First name cannot have more than 250 characters.");
        }

        if(player.getLastName() == null || player.getLastName().isBlank()){
            result.addErrorMessage("Last name is required.");
        } else if(player.getFirstName().length() > 250){
            result.addErrorMessage("Last name cannot have more than 250 characters.");
        }

        if(player.getJerseyNumber() < 0 || player.getJerseyNumber() > 99){
            result.addErrorMessage("Player jersey number must be between 0 and 99");
        }

        if(player.getPosition() == null || player.getPosition().isBlank()){
            result.addErrorMessage("Player position is required");
        }

        if(player.getTeam() == null){
            result.addErrorMessage("Team is required");
        }

        return result;
    }

    // Might be Able to remove this
    // Grabs a list of available players from the API that are not currently in the database
    public List<Player> getAvailablePlayers(){
        List<Player> apiPlayers = nbaApiService.getTopPointsLeaders();
        List<Player> dbPlayers = repository.findAll();

        // Remove the players that are already in the database
        apiPlayers.removeIf(apiPlayer ->
                dbPlayers.stream().anyMatch(dbPlayer ->
                        dbPlayer.getFirstName().equals(apiPlayer.getFirstName()) &&
                        dbPlayer.getLastName().equals(apiPlayer.getLastName())));

        return apiPlayers;
    }
}
