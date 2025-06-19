package learn.benchwarmers.domain;

import learn.benchwarmers.data.AppUserRepository;
import learn.benchwarmers.data.FantasyLeagueRepository;
import learn.benchwarmers.data.LeagueMemberRepository;
import learn.benchwarmers.models.AppUser;
import learn.benchwarmers.models.FantasyLeague;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class FantasyLeagueService {
    private final FantasyLeagueRepository repository;
    private final AppUserRepository userRepository;

    private final LeagueMemberRepository leagueMemberRepository;
    private final int MAX_MEMBERS_PER_LEAGUE = 5;

    public FantasyLeagueService(FantasyLeagueRepository repository, AppUserRepository userRepository, LeagueMemberRepository leagueMemberRepository) {
        this.repository = repository;
        this.userRepository = userRepository;
        this.leagueMemberRepository = leagueMemberRepository;
    }

    // Find all fantasy leagues
    public List<FantasyLeague> findAll(){
        return repository.findAll();
    }

    // Find a fantasy league by ID
    public FantasyLeague findById(int leagueId){
        return repository.findAll().stream()
                .filter(l -> l.getFantasyLeagueId() == leagueId)
                .findFirst()
                .orElse(null);
    }

    // Find fantasy leagues by season
    public List<FantasyLeague> findBySeason(int season){
        if(season <= 0){
            throw new IllegalArgumentException("Season cannot be negative.");
        }

        return repository.findAll().stream()
                .filter(l -> l.getSeason() == season)
                .collect(Collectors.toList());
    }

    // Add a fantasy league
    public Result<FantasyLeague> add(Map<String, String> parameters){
        FantasyLeague league = new FantasyLeague();
        AppUser admin = userRepository.findByUsername(parameters.get("adminName"));
        List<AppUser> members = new ArrayList<>();
        members.add(admin);

        league.setAdmin(admin);
        league.setName(parameters.get("name"));
        league.setSeason(Integer.parseInt(parameters.get("season")));
        league.setMembers(members);
        Result<FantasyLeague> result = validate(league);

        if(!result.isSuccess()){
            return result;
        }

        if(league.getFantasyLeagueId() != 0){
            result.addErrorMessage("Fantasy league ID cannot be set when adding");
            return result;
        }

        // Setting user to admin
        userRepository.update(league.getAdmin(), 1);

        league = repository.add(league);

        //add admin to the league
        leagueMemberRepository.add(league, admin);

        result.setPayload(league);
        return result;
    }

    // Update a fantasy league
    public Result<FantasyLeague> update(int leagueId, Map<String, String> parameters){
        FantasyLeague league = repository.findAll().stream()
                .filter(cur -> cur.getFantasyLeagueId() == leagueId)
                .findFirst().orElse(null);

        league.setName(parameters.get("name"));
        league.setSeason(Integer.parseInt(parameters.get("season")));
        Result<FantasyLeague> result = validate(league);

        if(league == null){
            result.addErrorMessage("Fantasy league does not exist.");
            return result;
        }

        if(!result.isSuccess()){
            return result;
        }

        if(league.getFantasyLeagueId() <= 0){
            result.addErrorMessage("Fantasy league ID is required when updating.");
            return result;
        }

        FantasyLeague existing = findById(league.getFantasyLeagueId());
        if(existing == null){
            result.addErrorMessage("This Fantasy league was not found.");
            return result;
        }

        // An admin cannot be changed, so we check it here
        if(league.getAdmin().getUserId() != existing.getAdmin().getUserId()){
            result.addErrorMessage("Admin cannot be change for a league.");
            return result;
        }

        if(parameters.get("name") == null || parameters.get("name").isBlank()){
            result.addErrorMessage("League name is required.");
            return result;
        }

        if(parameters.get("name").length() > 250){
            result.addErrorMessage("A League name cannot exceed 250 characters.");
            return result;
        }


        boolean success = repository.update(league);
        if(!success){
            result.addErrorMessage("Fantasy league failed to update.");
        }

        return result;
    }


    // Validate a fantasy league
    public Result<FantasyLeague> validate(FantasyLeague league){
        Result<FantasyLeague> result = new Result<>();

        if (league == null) {
            result.addErrorMessage("A Fantasy league cannot be null.");
            return result;
        }

        if(league.getName() == null || league.getName().isBlank()){
            result.addErrorMessage("League name is required.");
        }

        if(league.getName().length() > 250){
            result.addErrorMessage("League name cannot exceed 250 characters.");
        }

        if(league.getSeason() <= 0){
            result.addErrorMessage("Season must be greater than 0.");
        }

        if(league.getAdmin() == null || league.getAdmin().getUserId() <= 0){
            result.addErrorMessage("Valid admin is required.");
        }

        //Validate members list
        if(league.getMembers() == null){
            result.addErrorMessage("Members list cannot be null.");
        } else{
            Set<Integer> membersIds = new HashSet<>();
            for(AppUser member : league.getMembers()){
                if(member == null || member.getUserId() <= 0){
                    result.addErrorMessage("All members must be a valid user.");
                    break;
                }

                if(!membersIds.add(member.getUserId())){
                    result.addErrorMessage("Duplicate members are not allowed.");
                    break;
                }
            }

            if(league.getMembers().size() > MAX_MEMBERS_PER_LEAGUE){
                result.addErrorMessage("A League cannot have more than " + MAX_MEMBERS_PER_LEAGUE + " members.");
            }
        }

        return result;
    }

    // Delete a league (Only Admins)
    public Result<Void> deleteLeague(int leagueId, int userId){
        Result<Void> result = new Result<>();

        FantasyLeague league = findById(leagueId);
        if(league == null){
            result.addErrorMessage("The Fantasy league was not found.");
            return result;
        }

        // Only admins can delete a league, so we check it here
        if(league.getAdmin().getUserId()  != userId){
            result.addErrorMessage("Only the league admin can delete the league");
            return result;
        }

        boolean success = repository.deleteById(leagueId);
        if(!success){
            result.addErrorMessage("Failed to delete league this league");
        }

        return result;
    }

    private boolean hasAdminRole(AppUser user){
        return user.getAuthorities().stream()
                .anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN"));
    }

}
