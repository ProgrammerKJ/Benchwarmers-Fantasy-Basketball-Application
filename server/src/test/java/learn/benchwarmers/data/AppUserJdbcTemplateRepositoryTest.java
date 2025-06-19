package learn.benchwarmers.data;

import learn.benchwarmers.models.Role;
import learn.benchwarmers.models.Team;
import learn.benchwarmers.models.AppUser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
class AppUserJdbcTemplateRepositoryTest {

    @Autowired
    AppUserJdbcTemplateRepository repository;
    @Autowired
    KnownGoodState knownGoodState;


    @BeforeEach
    void setup(){knownGoodState.set();}

    //Read
    //Happy Path
    @Test
    void shouldFindAll(){
        List<AppUser> appUsers = repository.findAll();
        assertNotNull(appUsers);

        assertTrue(appUsers.size() >= 1);
    }


    //Create
    //Happy Path
    @Test
    void shouldAddUser(){
        List<String> roles = new ArrayList<>();
        roles.add("ADMIN");
        AppUser appUser =  new AppUser(1, "username", "passHash", false, roles);
        appUser.setFavoriteTeam(Team.KNICKS);
        appUser.setEmail("test@email.com");

        AppUser result = repository.add(appUser);

        List<AppUser> appUsers = repository.findAll();
        assertNotNull(result);
        assertTrue(result.getUserId()  > 0);
        assertTrue(appUsers.size() >= 2);
    }

    //Unhappy path
    @Test
    void shouldNotAddNullUser(){
        AppUser result = repository.add(null);
        assertNull(result);
    }

    //Update
    //Happy path
    @Test
    void shouldUpdateUser(){
        AppUser appUser = repository.findAll().get(1);
        appUser.setEmail("updatedEmail@email.com");

        assertTrue(repository.update(appUser, Role.valueOf("LEAGUE_MEMBER").getId()));

        List<AppUser> appUsers = repository.findAll();
        AppUser result = appUsers.stream().filter(user -> user.getUserId() == appUser.getUserId()).findFirst().orElse(null);
        assertNotNull(result);
        assertEquals("updatedEmail@email.com", result.getEmail());
    }

    //unhappy
    @Test
    void shouldNotUpdateNullUser(){
        assertFalse(repository.update(null, Role.valueOf("LEAGUE_MEMBER").getId()));
    }


    //Delete
    //Happy path
    @Test
    void shouldDeleteUser(){
        AppUser appUser = repository.findAll().get(4);
        assertTrue(repository.deleteById(appUser.getUserId()));

        List<AppUser> appUsers = repository.findAll();
        assertFalse(appUsers.contains(appUser));
    }

    //unhappy
    @Test
    void shouldNotDeleteNonExistingId(){
        assertFalse(repository.deleteById(-1));
    }
}