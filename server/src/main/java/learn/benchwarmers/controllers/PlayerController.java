package learn.benchwarmers.controllers;

import learn.benchwarmers.domain.PlayerService;
import learn.benchwarmers.domain.Result;
import learn.benchwarmers.models.Player;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = {"http://localhost:3000"})
@RequestMapping("/api/player")
public class PlayerController {

    private final PlayerService service;

    public PlayerController(PlayerService service){
        this.service = service;
    }

    @GetMapping("/position/{position}")
    public List<Player> findByPosition(@PathVariable String position){
        return service.findByPosition(position);
    }

    @GetMapping("/top-leaders")
    public List<Player> findTopPointsLeaders(){
        return service.findTopPointsLeaders();
    }

    @GetMapping("/{firstName}/{lastName}")
    public Player findPlayerByName(@PathVariable String firstName, @PathVariable String lastName){
        return service.findByName(firstName, lastName);
    }

    @PostMapping
    public ResponseEntity<Object> add(@RequestBody Player player){
        Result<Player> result = service.add(player);

        if(result.isSuccess()){
            return new ResponseEntity<>(result.getPayload(), HttpStatus.CREATED);
        }

        return ErrorResponse.build(result);
    }

    @PutMapping("/{playerId}")
    public ResponseEntity<Object> update(@PathVariable int playerId, @RequestBody Player player){
        if(playerId != player.getPlayerId()){
            return new ResponseEntity<>(List.of("Player ID mismatch"), HttpStatus.CONFLICT);
        }

        Result<Player> result = service.update(player);
        if(result.isSuccess()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return ErrorResponse.build(result);
    }

    @DeleteMapping("/{playerId}")
    public ResponseEntity<Void> delete(@PathVariable int playerId){
        if(service.deleteById(playerId)){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}
