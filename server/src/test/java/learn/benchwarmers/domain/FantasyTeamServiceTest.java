package learn.benchwarmers.domain;

import learn.benchwarmers.data.FantasyTeamRepository;
import learn.benchwarmers.models.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.*;

class FantasyTeamServiceTest {

    @Autowired
    FantasyTeamService fantasyTeamService;

    @Autowired
    PlayerService playerService;

    @Autowired
    NbaApiService nbaApiService;

    @MockBean
    FantasyTeamRepository repository;

}