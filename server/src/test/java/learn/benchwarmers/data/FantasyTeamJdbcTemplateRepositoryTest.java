package learn.benchwarmers.data;

import learn.benchwarmers.models.FantasyLeague;
import learn.benchwarmers.models.FantasyTeam;
import learn.benchwarmers.models.Player;
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
class FantasyTeamJdbcTemplateRepositoryTest {
    @Autowired
    FantasyTeamJdbcTemplateRepository fantasyTeamRepository;

    @Autowired
    FantasyLeagueJdbcTemplateRepository fantasyLeagueRepository;

    @Autowired
    PlayerJdbcTemplateRepository playerRepository;

    @Autowired
    AppUserJdbcTemplateRepository userRepository;

    @Autowired
    KnownGoodState knownGoodState;


    @BeforeEach
    void setup(){knownGoodState.set();}

    //Read
    //happy path
    @Test
    void shouldFindAll(){
        List<FantasyTeam> teams = fantasyTeamRepository.findAll();
        assertNotNull(teams);


        assertTrue(teams.size() >= 1);
    }

    //Create
    //happy path
    @Test
    void shouldAddPlayerToFantasyTeam(){
        Player player = playerRepository.findAll().get(2);

        FantasyTeam fantasyTeam = fantasyTeamRepository.findAll().get(1);

        FantasyTeam result = fantasyTeamRepository.addPlayer(fantasyTeam, player);

        assertNotNull(result);
        assertTrue(result.getPlayers().size()  > 0);

    }

    @Test
    void shouldAddNewFantasyTeam(){
        Player player = playerRepository.findAll().get(1);
        AppUser appUser =  userRepository.findAll().get(1);

        FantasyLeague league = fantasyLeagueRepository.findAll().get(1);
        List<Player> players = new ArrayList<>();
        FantasyTeam fantasyTeam = new FantasyTeam(0, appUser, players, league);

        FantasyTeam result = fantasyTeamRepository.addFantasyTeam(fantasyTeam, player);

        assertNotNull(result);
        assertTrue(result.getPlayers().size() == 1);

    }

    //unhappy
    @Test
    void shouldNotAddNullPlayer(){
        FantasyTeam fantasyTeam = fantasyTeamRepository.findAll().get(1);
        FantasyTeam result = fantasyTeamRepository.addPlayer(fantasyTeam, null);
        assertNull(result);
    }

    //Update
    //happy path
    @Test
    void shouldUpdatePlayerOnFantasyTeam(){
        Player player = playerRepository.findAll().get(0);
        FantasyTeam fantasyTeam = fantasyTeamRepository.findAll().get(2);

        assertTrue(fantasyTeam.getPlayers().get(0).getPlayerId() == 2);

        assertTrue(fantasyTeamRepository.updatePlayer(fantasyTeam, player));

        FantasyTeam result = fantasyTeamRepository.findAll().get(0);
        assertEquals(player.getPlayerId(), result.getPlayers().get(0).getPlayerId());
    }

    //unhappy


    //Delete
    //happy
    @Test
    void shouldRemoveFantasyTeam(){
        FantasyTeam fantasyTeam = fantasyTeamRepository.findAll().get(2);
        List<FantasyTeam> fantasyTeams = fantasyTeamRepository.findAll();

        assertTrue(fantasyTeams.size() == 3);
        assertTrue(fantasyTeamRepository.deleteById(fantasyTeam.getFantasyTeamId()));

        List<FantasyTeam> result = fantasyTeamRepository.findAll();
        assertTrue(result.size() < 3 );
    }

    @Test
    void shouldRemovePlayerFromFantasyTeam(){
        Player player = playerRepository.findAll().get(1);
        FantasyTeam fantasyTeam = fantasyTeamRepository.findAll().get(1);

        assertNotNull(fantasyTeam.getPlayers().stream()
                .filter(cur -> cur.getPlayerId() == player.getPlayerId()).findFirst().orElse(null));

        assertTrue(fantasyTeamRepository.deleteByPlayerFromTeam(fantasyTeam.getFantasyTeamId(), player.getPlayerId()));

        List<FantasyTeam> teams = fantasyTeamRepository.findAll();
        FantasyTeam result = fantasyTeamRepository.findAll().stream().filter(team -> team.getFantasyTeamId() == fantasyTeam.getFantasyTeamId()).findFirst().orElse(null);
        assertNull(result.getPlayers().stream()
                .filter(cur -> cur.getPlayerId() == player.getPlayerId()).findFirst().orElse(null));
    }

    //unhappy
}