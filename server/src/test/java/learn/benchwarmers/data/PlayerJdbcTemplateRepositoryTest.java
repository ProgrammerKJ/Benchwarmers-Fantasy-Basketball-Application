package learn.benchwarmers.data;

import learn.benchwarmers.models.Player;
import learn.benchwarmers.models.Team;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
class PlayerJdbcTemplateRepositoryTest {
    @Autowired
    PlayerJdbcTemplateRepository repository;
    @Autowired
    KnownGoodState knownGoodState;


    @BeforeEach
    void setup(){knownGoodState.set();}

    //Read
    //Happy Path
    @Test
    void shouldFindAll(){
        List<Player> players = repository.findAll();
        assertNotNull(players);

        assertTrue(players.size() >=  1);
    }

    //Unhappy path

    //Create
    //Happy Path
    @Test
    void shouldAddPlayer(){
        Player player = new Player(0, "Carmelo", "Anthony", "SF", 7, 21431, Team.KNICKS);


        Player result = repository.add(player);

        List<Player> users = repository.findAll();
        assertNotNull(result);
        assertTrue(result.getPlayerId()  > 0);
        assertTrue(users.size() >= 2);
    }

    //unhappy path

    //Update
    //Happy path
    @Test
    void shouldUpdatePlayer(){
        Player player = repository.findAll().get(1);
        player.setJerseyNumber(8);

        assertTrue(repository.update(player));

        List<Player> players = repository.findAll();
        assertNotNull(players.get(1));
        assertEquals(8, players.get(1).getJerseyNumber());
    }

    //unhappy path

    //Delete
    //happy path
    @Test
    void shouldDeletePlayer(){
        Player player = repository.findAll().get(2);
        assertTrue(repository.deleteById(player.getPlayerId()));

        List<Player> players = repository.findAll();
        assertFalse(players.contains(player));
    }

    //unhappy path
    @Test
    void shouldNotDeleteNonExistingId(){
        assertFalse(repository.deleteById(-1));
    }
}