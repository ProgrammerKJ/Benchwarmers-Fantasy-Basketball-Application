package learn.benchwarmers.data;

import learn.benchwarmers.models.FantasyLeague;
import learn.benchwarmers.models.AppUser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
class LeagueMemberJdbcTemplateRepositoryTest {
    @Autowired
    LeagueMemberJdbcTemplateRepository leagueMemberRepository;

    @Autowired
    FantasyLeagueJdbcTemplateRepository fantasyLeagueRepository;

    @Autowired
    AppUserJdbcTemplateRepository userRepository;

    @Autowired
    KnownGoodState knownGoodState;


    @BeforeEach
    void setup(){knownGoodState.set();}

    //Create
    //Happy Path
    @Test
    void shouldAddLeagueMember(){
        FantasyLeague fantasyLeague = fantasyLeagueRepository.findAll().get(1);


        AppUser appUser = userRepository.findAll().get(2);

        assertFalse(fantasyLeague.getMembers().contains(appUser));
        fantasyLeague = leagueMemberRepository.add(fantasyLeague, appUser);

        List<FantasyLeague> leagues = fantasyLeagueRepository.findAll();
        assertNotNull(leagues);

        List<AppUser> members = leagues.get(1).getMembers();
        assertNotNull(members.stream()
                .filter(member -> member.getUserId() == appUser.getUserId()));
        assertTrue(leagues.get(1).getMembers().size() >= 2);
    }

    //Unhappy path
    @Test
    void shouldNotAddNullLeague(){
        AppUser appUser = userRepository.findAll().get(2);
        FantasyLeague league = leagueMemberRepository.add(null, appUser);
        assertNull(league);
    }

    @Test
    void shouldNotAddNullUser(){
        FantasyLeague league = fantasyLeagueRepository.findAll().get(1);
        FantasyLeague result = leagueMemberRepository.add(league, null);
        assertNull(result);
    }

    //Delete
    //Happy path
    @Test
    void shouldDeleteUser(){
        FantasyLeague fantasyLeague = fantasyLeagueRepository.findAll().get(0);

        assertTrue(fantasyLeague.getMembers().size() == 3);
        AppUser appUser = fantasyLeague.getMembers().get(2);

        assertTrue(leagueMemberRepository.deleteById(fantasyLeague.getFantasyLeagueId(), appUser.getUserId()));

        FantasyLeague result = fantasyLeagueRepository.findAll().get(0);
        assertTrue(result.getMembers().size() < 3);
    }

    //unhappy
    @Test
    void shouldNotDeleteNonExistingId(){
        assertFalse(fantasyLeagueRepository.deleteById(-1));
    }
}