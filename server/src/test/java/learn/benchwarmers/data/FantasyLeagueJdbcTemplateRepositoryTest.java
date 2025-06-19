package learn.benchwarmers.data;

import learn.benchwarmers.models.FantasyLeague;
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
class FantasyLeagueJdbcTemplateRepositoryTest {
    @Autowired
    FantasyLeagueJdbcTemplateRepository fantasyLeagueRepository;

    @Autowired
    AppUserJdbcTemplateRepository userRepository;

    @Autowired
    KnownGoodState knownGoodState;


    @BeforeEach
    void setup(){knownGoodState.set();}

    //Read
    //happy
    @Test
    void shouldFindAll(){
        List<FantasyLeague> leagues = fantasyLeagueRepository.findAll();
        assertNotNull(leagues);

        assertTrue(leagues.size() >= 1);
    }

    @Test
    void shouldFindByMemberId(){
        List<FantasyLeague> leagues = fantasyLeagueRepository.findByMemberId(3);
        assertNotNull(leagues);

        assertTrue(leagues.size() >= 1);
    }

    @Test
    void shouldFindByAdminId(){
        List<FantasyLeague> leagues = fantasyLeagueRepository.findByAdminId(2);
        assertNotNull(leagues);

        assertTrue(leagues.size() >= 1);
    }

    //unhappy
    @Test
    void shouldNotFindByNonExistingAdminId(){
        List<FantasyLeague> leagues = fantasyLeagueRepository.findByAdminId(-2);
        assertNotNull(leagues);
        assertTrue(leagues.size() == 0);
    }

    @Test
    void shouldNotFindByNonExistingMemberId(){
        List<FantasyLeague> leagues = fantasyLeagueRepository.findByMemberId(-2);
        assertNotNull(leagues);
        assertTrue(leagues.size() == 0);
    }

    //Create
    //happy
    @Test
    void shouldCreateFantasyLeague(){
        AppUser appUser = userRepository.findAll().get(1);
        List<AppUser> members = new ArrayList<>();
        members.add(appUser);
        FantasyLeague league = new FantasyLeague(0, "Lebronto", 2020, appUser, members);

        FantasyLeague result = fantasyLeagueRepository.add(league);

        List<FantasyLeague> leagues = fantasyLeagueRepository.findAll();
        assertNotNull(result);
        assertTrue(result.getFantasyLeagueId()  > 0);
        assertTrue(leagues.size() >= 2);
    }

    //unhappy
    @Test
    void shouldNotAddNullLeague(){
        FantasyLeague result = fantasyLeagueRepository.add(null);
        assertNull(result);
    }

    //Update
    //happy
    @Test
    void shouldUpdateFantasyLeague(){
        FantasyLeague league = fantasyLeagueRepository.findAll().get(1);
        league.setName("SpursBasketball");

        assertTrue(fantasyLeagueRepository.update(league));

        List<FantasyLeague> leagues = fantasyLeagueRepository.findAll();
        assertNotNull(leagues.get(1));
        assertEquals("SpursBasketball", leagues.get(1).getName());
    }

    //unhappy
    @Test
    void shouldNotUpdateNullLeague(){
        assertFalse(fantasyLeagueRepository.update(null));
    }


    //Delete
    //happy
    @Test
    void shouldDeleteFantasyLeague(){
        FantasyLeague league = fantasyLeagueRepository.findAll().get(2);
        assertTrue(fantasyLeagueRepository.deleteById(league.getFantasyLeagueId()));

        List<FantasyLeague> leagues = fantasyLeagueRepository.findAll();
        assertFalse(leagues.contains(league));
    }

    //unhappy
    @Test
    void shouldNotDeleteNonExistingId(){
        assertFalse(fantasyLeagueRepository.deleteById(-1));
    }
}