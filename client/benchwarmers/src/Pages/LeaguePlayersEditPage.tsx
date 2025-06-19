import { useState, useEffect } from "react"
import {useParams} from "react-router-dom";
import api, {checkResStatus} from "../UtilityFunctions/api";


interface LeaguePlayersEditPageProps {
    appApi: string,
    radarApi: string,
    teamApi: string
}

// Gonna add some dummy data for now

interface player{
    playerId: number, firstName: string, lastName: string, jerseyNumber: number, position: string, team:string

}
interface apiObj{
    fantasyLeague: {fantasyLeagueId: number},
    fantasyTeamId: number,
    players: player[],
    totalPoints: number,
    user: { username: string | null, userId: number};
}
export default function LeaguePlayersEditPage(props: LeaguePlayersEditPageProps) {

    const [team, setTeam] = useState<apiObj>();
    const [selectedPlayer, setSelectedPlayer] = useState<string | null>(null);
    const [availablePlayers, setAvailablePlayers] = useState<player[]>([]);
    const [refresh, setRefresh] = useState(false);
    const {leagueId} = useParams();

    function handleRemove(play : player){
        if (refresh){
            return;
        }

        const token = localStorage.getItem("jwt_token");
        const options = {
            method: "DELETE",
            headers: {
                "Content-Type": "application/json",
                "Authorization": `Bearer ${token?.slice(1,token.length-1)}`

            },
            body: JSON.stringify(play)
        }



        fetch(`http://localhost:8080/api/fantasy-team/${team?.fantasyTeamId}/player/${play.playerId}/${localStorage.getItem("username")}`,options);


        setRefresh(true);

    }

    function handleAdd() {
        if (refresh){
            return;
        }

        const token = localStorage.getItem("jwt_token");
        const play = availablePlayers.find((find) => `${find.firstName}${find.lastName}` === selectedPlayer);

        const options = {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
                "Authorization": `Bearer ${token?.slice(1,token.length-1)}`

            },
            body: JSON.stringify(play)
        }

        fetch(`http://localhost:8080/api/fantasy-team/${localStorage.getItem("username")}/${leagueId}`,options)
        .then(response => {
            if(team && play){
                setTeam({
                    ...team,
                    players: [...team.players, play]
                });
                setSelectedPlayer(null);
            }
        })

        // setRefresh(true);
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
        const memberTeams = await fetch(`http://localhost:8080/api/fantasy-team/league/${leagueId}`, options).then((res) => {return checkResStatus(res,[200])});
        setTeam(memberTeams.filter((userT: { user: { username: string | null; }; }) => userT.user.username === localStorage.getItem("username"))[0]);
    }

    async function getAvailPlay(){

        const token = localStorage.getItem("jwt_token");
        const options = {
            method: "GET",
            headers: {
                "Content-Type": "application/json",
                "Authorization": `Bearer ${token?.slice(1,token.length-1)}`
            }
        }
        const play = await fetch(`http://localhost:8080/api/fantasy-team/league/${leagueId}/available-players`, options).then((res) => {return checkResStatus(res,[200])});
        setAvailablePlayers(play);
        console.log(play)
    }

    useEffect(() => {
        if (leagueId !== "0"){
            getTeamsForMembers();
            getAvailPlay();

        }
        setRefresh(false);

    }, [leagueId, refresh]);



    return (
        <div className="p-8 space-y-8">
            <h1 className="text-3xl font-bold mb-4">Edit your Fantasy Team</h1>


            {/* Current Team */ }
            <section className="bg-white p-6 rounded shadow">
                <h2 className="text-xl font-semibold mb-4"> Current Team</h2>
                <div className="grid grid-cols-2 md:grid-cols-4 gap-4">
                    {/* Team map goes here */}
                    {team?.players.map((player) => (
                        <div key={player.firstName + player.lastName} className="bg-gray-100 p-4 rounded flex items-center justify-between">
                        <span>{player.firstName + " " + player.lastName}</span>
                        <button className="ml-2 text-red-600 hover:underline text-sm" onClick={() => handleRemove(player)}>
                            Remove
                        </button>
                        </div>
                    ))}
                </div>
            </section>

            {/* Add Player */}
            <section className="bg-white p-6 rounded shadow">
                    <h2 className="text-xl  font-semibold mb-4"> Add Player</h2>
                    <div className="flex gap-4 items-center">
                        <select className="border border-gray-300 p-2 rounded w-full max-w-sm" value={selectedPlayer ?? ''} onChange={(e) => setSelectedPlayer(e.target.value)}>
                        <option value="" key={-1}>
                            Select a player
                        </option>
                        {
                            availablePlayers.map((player) => (
                                <option key={player.firstName + player.lastName} value={player.firstName + player.lastName} >
                                        {player.firstName + " " + player.lastName}
                                </option>
                            ))

                        }
                        </select>
                        <button className="px-4 py-2 bg-green-600 hover:bg-green-700 text-white rounded" onClick={() => handleAdd()}>
                            Add
                        </button>
                    </div>
            </section>


        </div>
    )
}