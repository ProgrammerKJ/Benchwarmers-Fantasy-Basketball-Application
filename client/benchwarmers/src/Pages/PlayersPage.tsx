import { useEffect, useState } from "react";
import { checkResStatus } from "../UtilityFunctions/api";
import PlayerCard from "../Components/PlayerCard";
import Image from "../Components/Image";

interface PlayersPageProps {
    appApi: string,
    radarApi: string,
    playerApi?: string,
    isLoggedIn: boolean
}

interface PlayerObj {
    id: number,
    firstName: string,
    lastName: string,
    position: string,
    jerseyNumber: string,
    fantasyPoints: number,
    teamId: number,
    teamName: string
}

interface InjuryReport {
    id: number,
    playerName: string,
    teamName: string,
    injury: string,
    status: string,
    expectedReturn: string
}

// Dummy injury report Data
const INJURY_REPORTS: InjuryReport[] = [
    {
        id: 1,
        playerName: "Clint Capela",
        teamName: "Atlanta Hawks",
        injury: "Hand Injury",
        status: "Questionable",
        expectedReturn: "April 18"
    },
    {
        id: 2,
        playerName: "Jaylen Brown",
        teamName: "Boston Celtics",
        injury: "Knee Injury",
        status: "Day-to-Day",
        expectedReturn: "April 20"
    },
    {
        id: 3,
        playerName: "Lonzo Ball",
        teamName: "Chicago Bulls",
        injury: "Wrist Injury",
        status: "Questionable",
        expectedReturn: "Game-Time Decision"
    },
    {
        id: 4,
        playerName: "Donovan Mitchell",
        teamName: "Cleveland Cavaliers",
        injury: "Ankle Injury",
        status: "Out",
        expectedReturn: "Day-to-Day"
    },
    {
        id: 5,
        playerName: "Kyrie Irving",
        teamName: "Dallas Mavericks",
        injury: "Knee Injury",
        status: "Out",
        expectedReturn: "January 1st"
    }
];


export default function PlayersPage(props: PlayersPageProps) {
    const { appApi, radarApi, playerApi, isLoggedIn } = props;
    const [playerArr, setPlayerArr] = useState<PlayerObj[]>([]);
    const [topPerformer, setTopPerformer] = useState<PlayerObj | null>(null);
    const [loading, setLoading] = useState<boolean>(true);
    const [error, setError] = useState<string | null>(null);

    // Authenticating the user or giving an error
    useEffect(() => {
        const token = localStorage.getItem("jwt_token");
        
        if (!isLoggedIn) {
            setError("Authentication required. Please log in.");
            setLoading(false);
            return;
        }
        
        fetchPlayers(token ?? "");
    }, [playerApi, isLoggedIn]);

    const fetchPlayers = async (token: string) => {
        try {
            const options = {
                method: "GET",
                headers: {
                    "Authorization": `Bearer ${token.slice(1,token.length-1)}`,
                    "Content-Type": "application/json"
                }
            };
            
            if (playerApi) {
                const endpoint = `${playerApi}/top-leaders`;
                const response = await fetch(endpoint, options);
                
                if (response.status === 200) {
                    const data = await response.json();
                    
                    if (data && Array.isArray(data) && data.length > 0) {
                        setTopPerformer(data[0]);
                        setPlayerArr(data);
                        setLoading(false);
                        return;
                    }
                }
            }
            
            setError("Failed to load players. Please try again later.");
            setLoading(false);
            
        } catch (err) {
            console.error("Error fetching players:", err);
            setError("Failed to load players. Please try again later.");
            setLoading(false);
        }
    };

    if (loading) {
        return <div className="flex justify-center items-center h-screen">Loading player data...</div>;
    }

    // CThis will be our injury report section
    const renderInjuryCard = (report: InjuryReport) => {
        return (
            <div key={report.id} className="mb-4 p-4 bg-gray-100 rounded shadow-sm">
                <div className="flex items-center">
                    <div className="flex-shrink-0 mr-3">
                        <Image 
                            dynamicSourcePath={"Teams"} 
                            dynamicSource={`${report.teamName.toLowerCase().replace(/\s/g, "_")}.png`} 
                            className={"h-12 w-12 object-contain"}
                            alt={`${report.teamName} logo`}
                        />
                    </div>
                    <div>
                        <p className="font-bold text-lg">{report.playerName}</p>
                        <p className="text-sm text-gray-600">{report.teamName}</p>
                    </div>
                </div>
                <div className="mt-2">
                    <p><span className="font-semibold">Injury:</span> {report.injury}</p>
                    <p>
                        <span className="font-semibold">Status:</span> 
                        <span className={
                            report.status === "Out" ? "text-red-500 ml-1 font-bold" : 
                            report.status === "Questionable" ? "text-yellow-500 ml-1 font-bold" : 
                            "text-green-500 ml-1 font-bold"
                        }>
                            {report.status}
                        </span>
                    </p>
                    <p><span className="font-semibold">Expected Return:</span> {report.expectedReturn}</p>
                </div>
            </div>
        );
    };

    return (
        <div className="flex mt-4 h-[calc(100vh-120px)]">
            {/* We will display our top performers here */}
            <section className="basis-1/4 flex flex-col ml-2 mr-2 bg-white shadow-md rounded px-8 pt-6 pb-6">
                <h1 className="text-3xl font-bold mb-4">
                    Top Performer:
                </h1>
                {topPerformer ? (
                    <div className="bg-gray-100 p-4 rounded shadow-sm">
                        <PlayerCard 
                            firstName={topPerformer.firstName} 
                            lastName={topPerformer.lastName} 
                            jerseyNumber={topPerformer.jerseyNumber} 
                            teamName={topPerformer.teamName} 
                            teamId={topPerformer.teamId} 
                            fantasyPoints={topPerformer.fantasyPoints} 
                            position={topPerformer.position}
                        />
                    </div>
                ) : (
                    <p>No top performers available</p>
                )}
            </section>

            {/* This will be our player list section */}
            <section className="flex flex-col ml-2 mr-2 bg-white shadow-md rounded px-8 pt-6 pb-6 basis-2/4 overflow-hidden">
                <h1 className="text-3xl font-bold mb-4">
                    Player List:
                </h1>
                {error && (
                    <div className="bg-yellow-50 p-2 mb-4 text-sm text-red-500">
                        {error}
                    </div>
                )}
                <div className="overflow-y-auto pr-4">
                    {playerArr.length > 0 ? (
                        playerArr.map((player, index) => (
                            <PlayerCard 
                                key={player.id || index}
                                firstName={player.firstName} 
                                lastName={player.lastName} 
                                jerseyNumber={player.jerseyNumber} 
                                teamName={player.teamName} 
                                teamId={player.teamId} 
                                fantasyPoints={player.fantasyPoints} 
                                position={player.position}
                            />
                        ))
                    ) : (
                        <p>No players available</p>
                    )}
                </div>
            </section>

            {/* This is will be our injury report section */}
            <section className="basis-1/4 flex flex-col ml-2 mr-2 bg-white shadow-md rounded px-8 pt-6 pb-6 overflow-hidden">
                <h1 className="text-3xl font-bold mb-4">
                    Injury Reports:
                </h1>
                <div className="overflow-y-auto">
                    {INJURY_REPORTS.map(report => renderInjuryCard(report))}
                </div>
            </section>
        </div>
    );
}