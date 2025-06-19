package learn.benchwarmers.domain;

import learn.benchwarmers.data.FantasyLeagueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.*;

class FantasyLeagueServiceTest {

    @Autowired
    FantasyLeagueService service;

    @MockBean
    FantasyLeagueRepository repository;

}