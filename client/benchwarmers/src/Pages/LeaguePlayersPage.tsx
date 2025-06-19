import { useState, useEffect } from "react";
import { Bar } from "react-chartjs-2";
import {
  Chart as ChartJS,
  BarElement,
  CategoryScale,
  LinearScale,
  Tooltip,
  Legend,
} from "chart.js";

ChartJS.register(BarElement, CategoryScale, LinearScale, Tooltip, Legend);
interface LeaguePlayersPageProps {
  appApi: string;
  radarApi: string;
  playerApi: string;
}

interface PlayerStats {
  playerId: number;
  firstName: string;
  lastName: string;
  team: string;
  fantasyPoints: number;
  points: number;
  rebounds: number;
  assists: number;
}

export default function LeaguePlayersPage(props: LeaguePlayersPageProps) {
  const [players, setPlayers] = useState<PlayerStats[]>([]);

  useEffect(() => {
    async function fetchTopPlayers() {
      const token = localStorage.getItem("jwt_token");

      if (!token) return;

      try {
        const res = await fetch(`${props.playerApi}/top-leaders`, {
          headers: {
            Authorization: `Bearer ${token.slice(1, token.length - 1)}`,
          },
        });

        if (!res.ok) throw new Error(`Error: ${res.status}`);

        const data: PlayerStats[] = await res.json();

        // Sort players by fantasyPoints descending
        const sorted = [...data].sort(
          (a, b) => b.fantasyPoints - a.fantasyPoints
        );
        setPlayers(sorted.slice(0, 25)); 
      } catch (err) {
        console.error("Failed to fetch top players:", err);
      }
    }

    fetchTopPlayers();
  }, [props.playerApi]);

  const chartData = {
    labels: players.map((p) => `${p.firstName} ${p.lastName}`),
    datasets: [
      {
        label: "Fantasy Points",
        data: players.map((p) => p.fantasyPoints),
        backgroundColor: "rgba(59, 130, 246, 0.6)",
      },
    ],
  };

  const chartOptions = {
    responsive: true,
    plugins: {
      legend: {
        display: false,
      },
    },
    scales: {
      y: {
        beginAtZero: true,
      },
    },
  };

  return (
    <div className="p-6 bg-gray-50 min-h-screen space-y-10">
      <h1 className="text-3xl font-bold text-center">
        Top 25 Fantasy Performers
      </h1>

      {/* Chart Section */}
      <section className="bg-white p-6 rounded shadow">
        <h2 className="text-xl font-semibold mb-4">
          {" "}
          Fantasy Points LeaderBoard
        </h2>
        <div className="overflow-x-auto">
          <Bar data={chartData} options={chartOptions} />
        </div>
      </section>

      {/* Table Section */}
      <section className="bg-white p-6 rounded shadow">
        <h2 className="text-xl font-semibold mb-4">Complete Stat Breakdown</h2>
        <div className="overflow-x-auto">
          <table className="w-full table-auto border-collapse">
            <thead>
              <tr className="border-b bg-gray-100">
                <th className="p-2 text-left">Player</th>
                <th className="p-2 text-left">Team</th>
                <th className="p-2 text-right">Fantasy PTS</th>
              </tr>
            </thead>
            <tbody>
              {players.map((p) => (
                <tr key={p.playerId} className="border-t hover:bg-gray-50">
                  <td className="p-2">{`${p.firstName} ${p.lastName}`}</td>
                  <td className="p-2">{p.team}</td>
                  <td className="p-2 text-right font-semibold">
                    {p.fantasyPoints}
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
