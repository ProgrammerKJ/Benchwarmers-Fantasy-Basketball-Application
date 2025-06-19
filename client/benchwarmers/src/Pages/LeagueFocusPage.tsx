import { useState, useEffect } from "react";

interface LeagueFocusPageProps {
  appApi: string;
  radarApi: string;
  teamApi: string;
}

interface PlayerStats {
  name: string;
  team: string;
  position: string;
  points: number;
  fantasyPoints: number;
  opponent: string;
}

interface FantasyTeam {
  manager: string;
  players: PlayerStats[];
  totalPoints: number;
}

export default function LeagueFocusPage(props: LeagueFocusPageProps) {
  const [players, setPlayers] = useState<PlayerStats[]>([]);
  const mockPlayers: PlayerStats[] = [
    {
      name: "LeBron James",
      team: "LAL",
      position: "SF",
      points: 27,
      fantasyPoints: 45.6,
      opponent: "GSW",
    },
    {
      name: "Luka Dončić",
      team: "DAL",
      position: "PG",
      points: 32,
      fantasyPoints: 52.3,
      opponent: "PHX",
    },
    {
      name: "Jayson Tatum",
      team: "BOS",
      position: "PF",
      points: 28,
      fantasyPoints: 41.7,
      opponent: "NYK",
    },
  ];

  useEffect(() => {
    setPlayers(mockPlayers);
  }, []);

  const userTeam: FantasyTeam = {
    manager: "You",
    players,
    totalPoints: players.reduce((sum, p) => sum + p.fantasyPoints, 0),
  };

  const opponentTeam: FantasyTeam = {
    manager: "Rival Squad",
    players: [
      {
        name: "Kevin Durant",
        team: "PHX",
        position: "SF",
        points: 30,
        fantasyPoints: 46.2,
        opponent: "LAL",
      },
      {
        name: "Joel Embiid",
        team: "PHI",
        position: "C",
        points: 29,
        fantasyPoints: 48.7,
        opponent: "MIL",
      },
    ],
    totalPoints: 94.9,
  };

  return (
    <div className="p-6 space-y-10 bg-gray-50 min-h-screen">
      <h1 className="text-3xl font-bold text-center">League Focus</h1>

      {/* Matchup Section */}
      <section className="bg-white p-6 rounded shadow">
        <h2 className="text-xl font-semibold mb-4">This Week's Matchup</h2>
        <div className="flex flex-col md:flex-row justify-between items-center gap-6">
          {/* User's Team */}
          <div className="flex-1 text-center">
            <h3 className="text-lg font-bold mb-2">{userTeam.manager}</h3>
            <p className="text-2xl font-bold text-blue-700">
              {userTeam.totalPoints.toFixed(1)} pts
            </p>
            <ul className="mt-2 text-sm text-gray-700">
              {userTeam.players.slice(0, 3).map((p) => (
                <li key={p.name}>
                  {p.name} - {p.fantasyPoints.toFixed(1)} pts
                </li>
              ))}
            </ul>
          </div>

          <div className="text-xl font-bold text-gray-700">VS</div>

          {/* Opponent Team */}

          <div className="flex-1 text-center">
            <h3 className="text-lg font-bold mb-2">{opponentTeam.manager}</h3>
            <p className="text-2xl font-bold text-red-700">
              {opponentTeam.totalPoints.toFixed(1)} pts
            </p>
            <ul className="mt-2 text-sm text-gray-700">
              {opponentTeam.players.slice(0, 3).map((p) => (
                <li key={p.name}>
                  {p.name} - {p.fantasyPoints.toFixed(1)} pts
                </li>
              ))}
            </ul>
          </div>
        </div>
      </section>

      {/* Team Summary */}
      <section className="bg-white p-6 rounded shadow">
        <h2 className="text-xl font-semibold mb-4">
          Your Team's Performance
        </h2>
        <div className="grid grid-cols-1 md:grid-cols-3 gap-4 text-center">
          <div className="bg-blue-100 p-4 rounded">
            <p className="text-lg font-medium">Total Fantasy Points</p>
            <p className="text-2xl font-bold">
              {userTeam.totalPoints.toFixed(1)}
            </p>
          </div>
          <div className="bg-green-100 p-4 rounded">
            <p className="text-lg font-medium">Avg Points/Game</p>
            <p className="text-2xl font-bold">
              {(
                players.reduce((sum, p) => sum + p.points, 0) / players.length
              ).toFixed(1)}
            </p>
          </div>
          <div className="bg-yellow-100 p-4 rounded">
            <p className="text-lg font-medium">Top Performer</p>
            <p className="text-2xl font-bold">
              {
                players.sort((a, b) => b.fantasyPoints - a.fantasyPoints)[0]
                  ?.name
              }
            </p>
          </div>
        </div>
      </section>

      {/* Individual Player Stats */}
      <section className="bg-white p-6 rounded shadow">
        <h2 className="text-xl font-semibold mb-4">Player Stats</h2>
        <div className="overflow-x-auto">
          <table className="w-full table-auto border-collapse">
            <thead>
              <tr className="border-b">
                <th className="p-2 text-left">Player</th>
                <th className="p-2 text-left">Team</th>
                <th className="p-2 text-left">Pos</th>
                <th className="p-2 text-left">Opponent</th>
                <th className="p-2 text-right">PTS</th>
                <th className="p-2 text-right">Fantasy Points</th>
              </tr>
            </thead>
            <tbody>
              {players.map((player) => (
                <tr key={player.name} className="border-t hover:bg-gray-50">
                  <td className="p-2">{player.name}</td>
                  <td className="p-2">{player.team}</td>
                  <td className="p-2">{player.position}</td>
                  <td className="p-2">{player.opponent}</td>
                  <td className="p-2 text-right">{player.points}</td>
                  <td className="p-2 text-right font-semibold">
                    {player.fantasyPoints.toFixed(1)}
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>
      </section>
    </div>
  );
}
