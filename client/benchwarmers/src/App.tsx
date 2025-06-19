import {BrowserRouter as Router, Routes, Route, data} from "react-router-dom";
import HomePage from "./Pages/HomePage";
import AccountPage from "./Pages/AccountPage";
import TeamsPage from "./Pages/TeamsPage";
import TeamFocusPage from "./Pages/TeamFocusPage";
import PlayersPage from "./Pages/PlayersPage";
import LeagueHomePage from "./Pages/LeagueHomePage";
import LeagueFocusPage from "./Pages/LeagueFocusPage";
import LeagueAccountPage from "./Pages/LeagueAccountPage";
import LeaguePlayersPage from "./Pages/LeaguePlayersPage";
import LeaguePlayersEditPage from "./Pages/LeaguePlayersEditPage";
import NavBar from "./Components/Navbar";
import 'slick-carousel/slick/slick.css';
import 'slick-carousel/slick/slick-theme.css';
import NotFoundPage from "./Pages/NotFoundPage";
import {useEffect, useState} from "react";
import LoggedInWrap from "./Components/LoggedInWrap";
import {checkAuthToken} from "./UtilityFunctions/api";

function App() {
    const appApi: string = process.env.REACT_APP_APPLICATION_USER_API || "";
    const radarApi = "";
    const appUserApi: string = process.env.REACT_APP_APPLICATION_APP_USER_API || "";
    const appPlayerApi: string  = process.env.REACT_APP_APPLICATION_PLAYER_API || "";
    const appTeamApi: string  = process.env.REACT_APP_APPLICATION_TEAM_API || "";
    const appLeagueApi: string  = process.env.REACT_APP_APPLICATION_LEAGUE_API || "";
    const [isLoggedIn, setIsLoggedIn] = useState(false);

    useEffect(() => {
        checkAuthToken().then(data => {
            console.log(data)
                if (data) {
                    setIsLoggedIn(true);
                } else {
                    setIsLoggedIn(false);
                    localStorage.clear();
                }
            }
        )
    }, []);



    return (
        <Router>
            <NavBar isLoggedIn={isLoggedIn} setIsLoggedIn={setIsLoggedIn}/>
            <Routes>
                <Route path={"/"} element={<HomePage appApi={appApi} radarApi={radarApi}/>}/>
                <Route path={"/login"} element={<AccountPage isLoggedIn={isLoggedIn} setIsLoggedIn={setIsLoggedIn} appApi={appApi} radarApi={radarApi} appUser={appUserApi} type={"login"}/>}/>
                <Route path={"/signup"} element={<AccountPage isLoggedIn={isLoggedIn} setIsLoggedIn={setIsLoggedIn} appApi={appApi} radarApi={radarApi} appUser={appUserApi} type={"signup"}/>}/>
                <Route path={"/editAccount"} element={<AccountPage isLoggedIn={isLoggedIn} setIsLoggedIn={setIsLoggedIn} appApi={appApi} radarApi={radarApi} appUser={appUserApi} type={"edit"}/>}/>
                <Route path={"/team"} element={<TeamsPage appApi={appApi} radarApi={radarApi}/>}/>
                <Route path={"/team/:teamId"} element={<TeamFocusPage appApi={appApi} radarApi={radarApi}/>}/>
                <Route path={"/player"} element={<PlayersPage appApi={appApi} radarApi={radarApi} playerApi={appPlayerApi} isLoggedIn={isLoggedIn}/>}/>

                <Route path={"/league"} element={<LoggedInWrap isLoggedIn={isLoggedIn} setIsLoggedIn={setIsLoggedIn}><LeagueHomePage  appApi={appApi} radarApi={radarApi} leagueApi={appLeagueApi}/></LoggedInWrap>}/>
                <Route path={"/league/:leagueId"} element={<LoggedInWrap isLoggedIn={isLoggedIn} setIsLoggedIn={setIsLoggedIn}><LeagueFocusPage appApi={appApi} radarApi={radarApi} teamApi={appTeamApi}/></LoggedInWrap>}/>
                <Route path={"/league/create"} element={<LoggedInWrap isLoggedIn={isLoggedIn} setIsLoggedIn={setIsLoggedIn}><LeagueAccountPage appApi={appApi} radarApi={radarApi} leagueApi={appLeagueApi} type={"create"}/></LoggedInWrap>}/>
                <Route path={"/league/:leagueId/players"} element={<LoggedInWrap isLoggedIn={isLoggedIn} setIsLoggedIn={setIsLoggedIn}><LeaguePlayersPage appApi={appApi} radarApi={radarApi} playerApi={appPlayerApi} /></LoggedInWrap>}/>
                <Route path={"/league/edit/:leagueId"} element={<LoggedInWrap isLoggedIn={isLoggedIn} setIsLoggedIn={setIsLoggedIn}><LeagueAccountPage appApi={appApi} radarApi={radarApi} leagueApi={appLeagueApi} type={"edit"}/></LoggedInWrap>}/>
                <Route path={"/league/:leagueId/players/edit"} element={<LoggedInWrap isLoggedIn={isLoggedIn} setIsLoggedIn={setIsLoggedIn}><LeaguePlayersEditPage appApi={appApi} radarApi={radarApi} teamApi={appTeamApi}/></LoggedInWrap>}/>

                <Route path={"*"} element={<NotFoundPage/>}/>
            </Routes>
        </Router>
    );
}

export default App;
