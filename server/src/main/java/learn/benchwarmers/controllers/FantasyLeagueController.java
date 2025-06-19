package learn.benchwarmers.controllers;

import learn.benchwarmers.domain.FantasyLeagueService;
import learn.benchwarmers.domain.Result;
import learn.benchwarmers.models.AppUser;
import learn.benchwarmers.models.FantasyLeague;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/fantasy-league")
@CrossOrigin(origins = {"http://localhost:3000"})

public class FantasyLeagueController {

    private final FantasyLeagueService service;

    public FantasyLeagueController(FantasyLeagueService service) {
        this.service = service;
    }

    @GetMapping
    public List<FantasyLeague> findAll(){
        return service.findAll();
    }

    @GetMapping("/{leagueId}")
    public ResponseEntity<FantasyLeague> findById(@PathVariable int leagueId){
        FantasyLeague league = service.findById(leagueId);
        if(league == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(league);
    }

//    @GetMapping("/season/{season}")
//    public List<FantasyLeague> findBySeason(@PathVariable int season){
//        return service.findBySeason(season);
//    }

    @PostMapping
    public ResponseEntity<Object> add( @RequestBody Map<String, String> parameters){
        Result<FantasyLeague> result = service.add(parameters);
        if(result.isSuccess()){
            return new ResponseEntity<>(result.getPayload(), HttpStatus.CREATED);
        }
        return ErrorResponse.build(result);
    }

    @PutMapping("/{leagueId}")
    public ResponseEntity<Object> update(@PathVariable int leagueId, @RequestBody Map<String, String> parameters){

        Result<FantasyLeague> result = service.update(leagueId, parameters);
        if(result.isSuccess()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return ErrorResponse.build(result);
    }


    @DeleteMapping("/{leagueId}/admin/{userId}")
    public ResponseEntity<Object> deleteLeague(@PathVariable int leagueId, @PathVariable int userId){
        Result<Void> result = service.deleteLeague(leagueId, userId);
        if(result.isSuccess()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(result.getErrorMessages(), HttpStatus.BAD_REQUEST);
    }

}
