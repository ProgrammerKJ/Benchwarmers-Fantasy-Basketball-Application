package learn.benchwarmers.controllers;

import learn.benchwarmers.domain.FantasyTeamService;
import learn.benchwarmers.domain.Result;
import learn.benchwarmers.models.FantasyTeam;
import learn.benchwarmers.models.Player;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/fantasy-team")
@CrossOrigin(origins = {"http://localhost:3000"})
public class FantasyTeamController {
    //fields
    private final FantasyTeamService service;

    //constructor with DI
    public FantasyTeamController(FantasyTeamService service) {
        this.service = service;
    }

    @GetMapping
    public List<FantasyTeam> findAll(){
        return service.findAll();
    }

    @GetMapping("/{teamId}")
    public ResponseEntity<FantasyTeam> findById(@PathVariable int teamId){
        FantasyTeam team = service.findById(teamId);
        if(team == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(team);
    }

    @GetMapping("/league/{leagueId}")
    public List<FantasyTeam> findByLeagueId(@PathVariable int leagueId){
        return service.findByLeagueId(leagueId);
    }

    @GetMapping("/user/{userId}")
    public List<FantasyTeam> findByUserId(@PathVariable int userId){
        return service.findByUserId(userId);
    }


   

    @PostMapping("/{username}/{leagueId}")
    public ResponseEntity<Object> add(@RequestBody Player player, @PathVariable String username, @PathVariable int leagueId){
        Result<FantasyTeam> result = service.add(player, username, leagueId);
        if(result.isSuccess()){
            return new ResponseEntity<>(result.getPayload(), HttpStatus.CREATED);
        }
        return ErrorResponse.build(result);
    }


    @PutMapping("/{teamId}")
    public ResponseEntity<Object> update(@PathVariable int teamId, @RequestBody Player player){
        FantasyTeam team = service.findById(teamId);
        if(team == null){
            return  new ResponseEntity<>(List.of("Team not found."), HttpStatus.NOT_FOUND);
        }

        Result<FantasyTeam> result = service.update(team, player);
        if(result.isSuccess()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return ErrorResponse.build(result);
    }

    @DeleteMapping("/{teamId}")
    public ResponseEntity<Void> deleteById(@PathVariable int teamId){
        if(service.deleteById(teamId)){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/league/{leagueId}/available-players")
    public List<Player> getAvailablePlayersForLeague(@PathVariable int leagueId){
        return service.getAvailablePlayersForLeague(leagueId);
    }

//    @PostMapping("/{teamId}/player/{firstName}/{lastName}/{userId}")
//    public ResponseEntity<Object> addPlayerFromApiToTeam(@PathVariable int teamId,
//                                                         @PathVariable String firstName,
//                                                         @PathVariable String lastName,
//                                                         @PathVariable int userId) {
//        Result<FantasyTeam> result = service.addPlayerFromApiToTeam(teamId, firstName, lastName, userId);
//        if (result.isSuccess()) {
//            return new ResponseEntity<>(result.getPayload(), HttpStatus.CREATED);
//        }
//        return new ResponseEntity<>(result.getErrorMessages(), HttpStatus.BAD_REQUEST);
//    }

    @DeleteMapping("/{teamId}/player/{playerId}/{username}")
    public ResponseEntity<Object> removePlayerFromTeam(@PathVariable int teamId,
                                                       @PathVariable int playerId,
                                                       @PathVariable String username) {
        Result<FantasyTeam> result = service.removePlayerFromTeam(teamId, playerId, username);
        if (result.isSuccess()) {
            return new ResponseEntity<>(result.getPayload(), HttpStatus.OK);
        }
        return new ResponseEntity<>(result.getErrorMessages(), HttpStatus.BAD_REQUEST);
    }
}
