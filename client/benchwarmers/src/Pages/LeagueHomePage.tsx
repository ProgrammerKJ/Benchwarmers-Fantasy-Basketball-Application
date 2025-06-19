import {Link} from "react-router-dom"
import {useEffect, useState} from "react";
import {checkResStatus, getLeagueListsFromToken} from "../UtilityFunctions/api";
import LeaugeBanner from "../Assets/Banner/Leauge_Banner.png"

interface LeagueHomePageProps {
  appApi: string;
  radarApi: string;
  leagueApi: string;
}

interface League {
  fantasyLeagueId: number;
  name: string;
  season: number;
  admin: object;
  members: object[];
}

interface player{
  playerId: number, firstName: string, lastName: string, jerseyNumber: number, position: string, team:string

}



export default function LeagueHomePage(props: LeagueHomePageProps) {
  const { appApi, radarApi } = props;
  const url = "http://localhost:8080/api/fantasy-league";


    const [userLeague, setUserLeague] = useState<League>({
        admin: {},
        fantasyLeagueId: 0,
        members: [],
        name: "",
        season: 0,
    });
    const [availableLeagues, setAvailableLeagues] = useState<League[]>([]);
    const [isAdmin, setIsAdmin] = useState(false);
    const [leagueTeams, setLeagueTeams] = useState([]);

  useEffect(() => {


    const token = localStorage.getItem("jwt_token");
    const auth = `Bearer ${token?.slice(1,token.length-1)}`;
    const init = {
      method: 'GET',
      headers: {
        'Accept': 'application/json',
        'Authorization': auth
      }
    }


    fetch(`${url}`, init)
      .then(response => {
        if(response.status === 200 || response.status === 403){
          return response.json();
        }
      })
      .then(data => {
        if(data[0]){
          setAvailableLeagues(data);
        }
      })
      .catch(console.log);

  }, []);

  async function getPartOf(){
      const tokenLeagues = await getLeagueListsFromToken();
      console.log(tokenLeagues)

      if (tokenLeagues && tokenLeagues.partOf) {
          setUserLeague(tokenLeagues.partOf)
          console.log(tokenLeagues.partOf)

          if (tokenLeagues.owned){
              setIsAdmin(true);
          }
      }
  }

  useEffect(() => {
      getPartOf();


  },[]);

      async function addToLeague(league: League){
          const newLeague = {...league};
          const token = localStorage.getItem("jwt_token");

          let options = {
              method: "GET",
              headers: {
                  "Content-Type": "application/json",
                  "Authorization": `Bearer ${token?.slice(1,token.length-1)}`
              }
          }
          newLeague.members.push(await fetch(`http://localhost:8080/api/app-user/`, options).then(res => {
              return checkResStatus(res, [200]);
          }).then(data => {
              return data.filter((user: {
                  username: string | null;
              }) => user.username === localStorage.getItem("username"))[0];
          }).catch(console.log));

          let optionsPut = {
              method: "PUT",
              headers: {
                  "Content-Type": "application/json",
                  "Authorization": `Bearer ${token?.slice(1,token.length-1)}`
              },
              body: JSON.stringify({name: newLeague.name, season: newLeague.season, members:newLeague.members})
          }
          console.log(newLeague)

          fetch(`http://localhost:8080/api/fantasy-league/2`, optionsPut).then(res => {
              return checkResStatus(res, [200,204]);
          }).then(data => {

              return data;
          }).catch(console.log);

      }

      async function joinNewLeague(league : League){
        const token = localStorage.getItem("jwt_token");
        const DEFAULT_PLAYER = {
          "playerId" : 0,
          "firstName": "Jayson",
          "lastName": "Tatum",
          "position": "PF",
          "jerseyNumber": 0,
          "fantasyPoints": 3328.0,
          "team": "CELTICS"
        }

        const options = {
          method: "POST",
          headers: {
              "Content-Type": "application/json",
              "Authorization": `Bearer ${token?.slice(1,token.length-1)}`

          },
          body: JSON.stringify(DEFAULT_PLAYER)
        } 

        fetch(`http://localhost:8080/api/fantasy-team/${localStorage.getItem("username")}/${league.fantasyLeagueId}`,options)
            .then((res) => {return checkResStatus(res,[201])})
            .then(data => setUserLeague(league));

      }


    async function getTeamsForMembers(){

        const token = localStorage.getItem("jwt_token");
        const options = {
            method: "GET",
            headers: {
                "Content-Type": "application/json",
                "Authorization": `Bearer ${token?.slice(1,token.length-1)}`
            }
        }
        const memberTeams = await fetch(`http://localhost:8080/api/fantasy-team/league/${userLeague.fantasyLeagueId}`, options).then((res) => {return checkResStatus(res,[200])});
        setLeagueTeams(memberTeams);
        console.log(memberTeams)
    }

    useEffect(() => {
        if (userLeague.fantasyLeagueId != 0){
            getTeamsForMembers();

        }

    }, [userLeague]);

    return (
      <div className="flex flex-col bg-gray-100 min-h-screen">
        {/* Going to place the banner image here */}
        <div className="w-full mb-8">
          <img 
            src={LeaugeBanner} 
            alt="Benchwarmers Fantasy League" 
            className="w-full object-cover max-h-[400px]"
          />
        </div>
        
        <div className="p-6 space-y-12 max-w-6xl mx-auto">
          {userLeague.fantasyLeagueId === 0 && (
            <>
              <section className="bg-white p-6 rounded shadow-sm space-y-4">
                <div className="flex justify-between items-center mb-6">
                  <h2 className="text-xl font-semibold">Join a League</h2>
                  <Link to={"/league/create"} className="px-4 py-2 bg-orange-500 hover:bg-orange-600 text-white rounded-md text-sm font-medium">
                    Create New League
                  </Link>
                </div>
                
                <p className="text-gray-600 mb-4">Select a league to join:</p>
  
                <div className="space-y-4">
                  {availableLeagues.map((league) => (
                    <div
                      key={league.fantasyLeagueId}
                      className="flex justify-between items-center bg-gray-100 p-4 rounded"
                    >
                      <div>
                        <p className="font-semibold">{league.name}</p>
                        <p className="text-sm text-gray-600">
                          Season: {league.season}
                        </p>
                      </div>
                      <button
                        onClick={() => joinNewLeague(league)}
                        className="px-4 py-2 bg-orange-500 hover:bg-orange-600 text-white rounded text-sm"
                      >
                        Join
                      </button>
                    </div>
                  ))}
                </div>
              </section>
            </>
          )}
  
          {userLeague.fantasyLeagueId != 0 && (
            <>
              {/* League Title or Overview */}
              <section className="bg-white p-6 rounded shadow">
                <h1 className="text-3xl font-bold mb-2">My Fantasy League</h1>
                <p className="text-gray-700">Welcome to the 2024-2025 Season</p>
                {isAdmin &&
                  <Link to={`/league/edit/${userLeague.fantasyLeagueId}`}>
                    <button className="px-4 py-2 bg-orange-500 hover:bg-orange-600 text-white rounded-md text-sm font-medium">
                      Edit League
                    </button>
                  </Link>
                }
              </section>
  
              {/* User's Team */}
              <section className="bg-white p-6 rounded shadow">
                <h2 className="text-xl font-semibold mb-4">Your Team</h2>
  
                <div className="flex flex-wrap gap-4 mb-4">
                  <Link to={`/league/${userLeague.fantasyLeagueId}/players`} >
                    <button className="px-4 py-2 bg-orange-500 hover:bg-orange-600 text-white rounded-md text-sm font-medium">
                      View Top Performers
                    </button>
                  </Link>
  
                  <Link to={`/league/${userLeague.fantasyLeagueId}/players/edit`} >
                    <button className="px-4 py-2 bg-orange-500 hover:bg-orange-600 text-white rounded-md text-sm font-medium">
                      Edit team
                    </button>
                  </Link>
                </div>
  
                {/* Insert Map of Players team here */}
              </section>
  
              {/* Current Standings */}
              <section className="bg-white p-6 rounded shadow">
                <h2 className="text-xl font-semibold mb-4">League Standings</h2>
                {/* Table / map goes here */}
                <table className="w-full text-left">
                  <thead>
                    <tr className="border-b">
                      <th className="py-2">Team</th>
                      <th className="py-2">Team Owner</th>
                      <th className="py-2">Points</th>
                    </tr>
                  </thead>
                  <tbody>
                    {leagueTeams.map((team: {fantasyTeamId: number, totalPoints: number, user: {username: string}}) => (
                      <tr key={team.fantasyTeamId}>
                        <td>{team.fantasyTeamId}</td>
                        <td>{team.user.username}</td>
                        <td>{team.totalPoints}</td>
                      </tr>
                    ))}
                  </tbody>
                </table>
              </section>
  
              {/* Upcoming / News section */}
            </>
          )}
        </div>
      </div>
    );
}
