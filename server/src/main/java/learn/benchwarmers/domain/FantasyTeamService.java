package learn.benchwarmers.domain;

import learn.benchwarmers.data.FantasyTeamRepository;
import learn.benchwarmers.data.LeagueMemberRepository;
import learn.benchwarmers.models.AppUser;
import learn.benchwarmers.models.FantasyLeague;
import learn.benchwarmers.models.FantasyTeam;
import learn.benchwarmers.models.Player;
import learn.benchwarmers.security.AppUserService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class FantasyTeamService {

    private final FantasyTeamRepository repository;
    private final PlayerService playerService;

    private final AppUserService appUserServiceService;
    private final FantasyLeagueService fantasyLeagueService;
    private final LeagueMemberRepository leagueMemberRepository;
    private final NbaApiService nbaApiService;
    private final int MAX_PLAYERS_PER_TEAM = 5;

    public FantasyTeamService(FantasyTeamRepository repository, PlayerService playerService, AppUserService appUserServiceService, FantasyLeagueService fantasyLeagueService, LeagueMemberRepository leagueMemberRepository, NbaApiService nbaApiService) {
        this.repository = repository;
        this.playerService = playerService;
        this.appUserServiceService = appUserServiceService;
        this.fantasyLeagueService = fantasyLeagueService;
        this.leagueMemberRepository = leagueMemberRepository;
        this.nbaApiService = nbaApiService;
    }

    // Find all fantasy teams
    public List<FantasyTeam> findAll() {
        List<FantasyTeam> teams = repository.findAll();
        teams.sort((team1, team2) -> sortFantasyPoint(team1.getTotalPoints(), team2.getTotalPoints()));
        return teams;
    }

    // Find fantasy team by ID
    public FantasyTeam findById(int teamId) {
        return repository.findAll().stream()
                .filter(t -> t.getFantasyTeamId() == teamId)
                .findFirst()
                .orElse(null);
    }

    // Find fantasy teams by league ID
    public List<FantasyTeam> findByLeagueId(int leagueId) {
        if (leagueId <= 0) {
            throw new IllegalArgumentException("League ID cannot be negative.");
        }

        List<FantasyTeam> teams = repository.findAll().stream()
                .filter(t -> t.getFantasyLeague().getFantasyLeagueId() == leagueId)
                .collect(Collectors.toList());

        teams.sort((team1, team2) -> sortFantasyPoint(team1.getTotalPoints(), team2.getTotalPoints()));
        return teams;
    }

    // Find fantasy teams by user ID
    public List<FantasyTeam> findByUserId(int userId) {
        if (userId <= 0) {
            throw new IllegalArgumentException("User ID cannot be negative.");
        }
        return repository.findAll().stream()
                .filter(t -> t.getUser().getUserId() == userId)
                .collect(Collectors.toList());
    }

    // Add a fantasy team
    public Result<FantasyTeam> add(Player player, String username, int leagueId) {
        AppUser user = appUserServiceService.findByUsername(username);
        FantasyLeague league = fantasyLeagueService.findById(leagueId);
        FantasyTeam team = repository.findAll().stream()
                .filter(cur -> cur.getUser().getUsername().equalsIgnoreCase(username))
                .findFirst().orElse(null);

        if(team == null){
            team = new FantasyTeam();
        }

        team.setUser(user);
        team.setFantasyLeague(league);
        Result<FantasyTeam> result = validate(team);

        if (!result.isSuccess()) {
            return result;
        }


        // Check if user is member of the league
        final FantasyTeam teamForLambda = team;
        boolean isMember = teamForLambda.getFantasyLeague().getMembers().stream()
                .anyMatch(m -> m.getUserId() == teamForLambda.getUser().getUserId());

        if (!isMember) {
            leagueMemberRepository.add(league, user);
        }

        if(player == null){
            player = playerService.findAll().get(0);
        }

        if(player.getPlayerId() == 0){
            player = playerService.add(player).getPayload();
        }

        team.getPlayers().add(player);
        repository.addFantasyTeam(team, player);

        result.setPayload(team);
        return result;
    }

    // Update a fantasy team
    public Result<FantasyTeam> update(FantasyTeam team, Player player) {
        Result<FantasyTeam> result = validate(team);

        if (!result.isSuccess()) {
            return result;
        }

        if (team.getFantasyTeamId() <= 0) {
            result.addErrorMessage("Fantasy team must have an ID for update");
            return result;
        }

        FantasyTeam existing = findById(team.getFantasyTeamId());
        if (existing == null) {
            result.addErrorMessage("Fantasy team not found.");
            return result;
        }

        if (team.getUser().getUserId() != existing.getUser().getUserId()) {
            result.addErrorMessage("Cannot change the team owner.");
            return result;
        }

        if (team.getFantasyLeague().getFantasyLeagueId() != existing.getFantasyLeague().getFantasyLeagueId()) {
            result.addErrorMessage("Cannot change fantasy league");
            return result;
        }

        boolean success = repository.updatePlayer(team, player);

        if (!success) {
            result.addErrorMessage("Fantasy team update failed.");
        }

        return result;
    }

    // Delete a fantasy team by ID
    public boolean deleteById(int teamId) {
        if (teamId <= 0) {
            return false;
        }
        return repository.deleteById(teamId);
    }

    // Validate A Fantasy Team
    public Result<FantasyTeam> validate(FantasyTeam team) {
        Result<FantasyTeam> result = new Result<>();

        if (team == null) {
            result.addErrorMessage("Fantasy team cannot be null.");
            return result;
        }

        if (team.getUser() == null || team.getUser().getUserId() <= 0) {
            result.addErrorMessage("Valid user is required.");
        }

        if (team.getFantasyLeague() == null || team.getFantasyLeague().getFantasyLeagueId() <= 0) {
            result.addErrorMessage("Valid fantasy league is required.");
        }

        // Validating the players list
        if (team.getPlayers() == null) {
            result.addErrorMessage("The players list cannot be null.");
        } else {
            // Checking for duplicate players
            Set<Integer> playerIds = new HashSet<>();
            for (Player player : team.getPlayers()) {
                if (player == null || player.getPlayerId() <= 0) {
                    result.addErrorMessage("All players must be valid.");
                    break;
                }
                if (!playerIds.add(player.getPlayerId())) {
                    result.addErrorMessage("Cannot have duplicate players.");
                    break;
                }
            }

            if (team.getPlayers().size() > MAX_PLAYERS_PER_TEAM) {
                result.addErrorMessage("Team cannot have more than " + MAX_PLAYERS_PER_TEAM + " players.");
            }
        }

        return result;
    }

    // Get a list of available players from the API but exclude the ones already selected
    public List<Player> getAvailablePlayersForLeague(int leagueId) {
        // Grabbing all players from API
        List<Player> allPlayersFromApi = nbaApiService.getTopPointsLeaders();

        // Grabbing all the teams within this league
        List<FantasyTeam> leagueTeams = findByLeagueId(leagueId);
        Set<String> draftedPlayerNames = new HashSet<>();

        // Collect all the names of the players already drafted within this league
        for (FantasyTeam team : leagueTeams) {
            for (Player player : team.getPlayers()) {
                draftedPlayerNames.add(player.getFirstName() + " " + player.getLastName());
            }
        }

        // Now we filter the list to only include the available players
        List<Player> availablePlayers = new ArrayList<>();
        for (Player player : allPlayersFromApi) {
            String fullName = player.getFirstName() + " " + player.getLastName();
            if (!draftedPlayerNames.contains(fullName)) {
                availablePlayers.add(player);
            }
        }

        return availablePlayers;

    }

    // Add a player from API to a team
    public Result<FantasyTeam> addPlayerFromApiToTeam(int teamId, String firstName, String lastName, int requestingUserId) {
        Result<FantasyTeam> result = new Result<>();

        FantasyTeam team = findById(teamId);
        if (team == null) {
            result.addErrorMessage("Fantasy team not found.");
            return result;
        }

        boolean isTeamOwner = team.getUser().getUserId() == requestingUserId;
        boolean isLeagueAdmin = team.getFantasyLeague().getAdmin().getUserId() == requestingUserId;

        if (!isTeamOwner && !isLeagueAdmin) {
            result.addErrorMessage("Only the team owner or league admin can modify the team.");
            return result;
        }

        // Check team player size limit
        if (team.getPlayers().size() >= MAX_PLAYERS_PER_TEAM) {
            result.addErrorMessage("Team is already at maximum capacity (" + MAX_PLAYERS_PER_TEAM + " players).");
            return result;
        }

        // Check if player is already on another team in the same league
        List<FantasyTeam> leagueTeams = findByLeagueId(team.getFantasyLeague().getFantasyLeagueId());
        for (FantasyTeam otherTeam : leagueTeams) {
            if (otherTeam.getFantasyTeamId() == teamId) {
                continue;
            }

            for (Player otherPlayer : otherTeam.getPlayers()) {
                if (otherPlayer.getFirstName().equalsIgnoreCase(firstName) &&
                        otherPlayer.getLastName().equalsIgnoreCase(lastName)) {
                    result.addErrorMessage("Player is already on another team in this league.");
                    return result;
                }
            }
        }

        // Find the player from the API then Add to the database if they exist
        Player apiPlayer = nbaApiService.findPlayerByName(firstName, lastName);
        if (apiPlayer == null) {
            result.addErrorMessage("Player not found in API data.");
            return result;
        }


        Result<Player> playerResult = playerService.add(apiPlayer);
        if (!playerResult.isSuccess()) {
            result.addErrorMessage("Failed to add player to database: " + String.join(", ", playerResult.getErrorMessages()));
            return result;
        }

        Player savedPlayer = playerResult.getPayload();

        team.getPlayers().add(savedPlayer);
        boolean success = repository.updatePlayer(team, savedPlayer);

        if (!success) {
            result.addErrorMessage("Failed to add player to team.");
            return result;
        }

        result.setPayload(team);
        return result;
    }

    // Helper method for removing a player from a fantasy team
    public Result<FantasyTeam> removePlayerFromTeam(int teamId, int playerId, String username){
        Result<FantasyTeam> result = new Result<>();

        FantasyTeam team = findById(teamId);
        if(team == null){
            result.addErrorMessage("Fantasy team was not found.");
            return result;
        }

        // Verify the user is the team owner
        boolean isTeamOwner = team.getUser().getUsername().equalsIgnoreCase(username);

        if (!isTeamOwner) {
            result.addErrorMessage("Only the team owner can modify the team.");
            return result;
        }

        // Checking if player is found or not and remove if so
        boolean playerFound = false;
        for(int i = 0; i < team.getPlayers().size(); i++){
            if(team.getPlayers().get(i).getPlayerId() == playerId){
                team.getPlayers().remove(i);
                playerFound = true;
                break;
            }
        }

        if(!playerFound){
            result.addErrorMessage("Player not found on this team.");
            return result;
        }

        boolean success = repository.deleteByPlayerFromTeam(teamId, playerId);

        if(!success){
            result.addErrorMessage("Failed to remove player from this league");
            return result;
        }

        result.setPayload(team);
        return result;
    }

    private int sortFantasyPoint(double a, double b){
        if(a > b){
            return -1;
        }else if(b > a){
            return 1;
        }
        return 0;
    }
}
