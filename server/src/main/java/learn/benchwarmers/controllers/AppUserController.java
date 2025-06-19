package learn.benchwarmers.controllers;


import learn.benchwarmers.domain.Result;
import learn.benchwarmers.models.AppUser;
import learn.benchwarmers.models.FantasyLeague;
import learn.benchwarmers.security.AppUserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/app-user")
@CrossOrigin(origins = {"http://localhost:3000"})
public class AppUserController {
    private final AppUserService service;

    public AppUserController(AppUserService service) {
        this.service = service;
    }

    @GetMapping
    public List<AppUser> findAll(){
        return service.findAll();
    }

    @GetMapping("/{username}")
    public ResponseEntity<AppUser> findByUsername(@PathVariable String username){
        AppUser user = service.findByUsername(username);
        if(user == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(user);
    }

    @PutMapping("/{username}")
    public ResponseEntity<Object> update(@PathVariable String username, @RequestBody Map<String, String> parameters){
        Result<AppUser> result = service.update(username, parameters);
        if(result.isSuccess()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return ErrorResponse.build(result);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> delete(@PathVariable int userId){
        if(service.deleteById(userId)){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>( HttpStatus.BAD_REQUEST);
    }
}
