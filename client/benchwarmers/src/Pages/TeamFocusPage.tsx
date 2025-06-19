import { useParams } from "react-router-dom";
import { useEffect, useState } from "react";
import Image from "../Components/Image";
import TeamPlayerCard from "../Components/TeamPlayerCard";

interface TeamFocusPageProps {
    appApi: string,
    radarApi: string,
}

interface Player {
    id: number,
    firstName: string,
    lastName: string,
    position: string,
    jerseyNumber: string,
}

interface Team {
    id: number,
    name: string,
    players: Player[]
}

export default function TeamFocusPage(props: TeamFocusPageProps) {
    const { teamId } = useParams<{ teamId: string }>();
    const [selectedTeam, setSelectedTeam] = useState<Team | null>(null);
    
    
    const NBA_TEAMS: Team[] = [
        {
            id: 1,
            name: "Atlanta Hawks",
            players: [
                { id: 1, firstName: "Trae", lastName: "Young", position: "PG", jerseyNumber: "11"},
                { id: 2, firstName: "Dyson", lastName: "Daniels", position: "SG", jerseyNumber: "5"},
                { id: 3, firstName: "Zaccharie", lastName: "Risacher", position: "SF", jerseyNumber: "12"},
                { id: 4, firstName: "Jalen", lastName: "Johnson", position: "PF", jerseyNumber: "1" },
                { id: 5, firstName: "Clint", lastName: "Capela", position: "C", jerseyNumber: "15"}
            ]
        },
        {
            id: 2,
            name: "Boston Celtics",
            players: [
                { id: 6, firstName: "Derrick", lastName: "White", position: "PG", jerseyNumber: "9"},
                { id: 7, firstName: "Jrue", lastName: "Holiday", position: "SG", jerseyNumber: "4"},
                { id: 8, firstName: "Jaylen", lastName: "Brown", position: "SF", jerseyNumber: "7"},
                { id: 9, firstName: "Jayson", lastName: "Tatum", position: "PF", jerseyNumber: "0"},
                { id: 10, firstName: "Kristaps", lastName: "Porzingis", position: "C", jerseyNumber: "8"}
            ]
        },
        {
            id: 3,
            name: "Brooklyn Nets",
            players: [
                { id: 11, firstName: "Dennis", lastName: "Schröder", position: "PG", jerseyNumber: "17"},
                { id: 12, firstName: "Cam", lastName: "Thomas", position: "SG", jerseyNumber: "24"},
                { id: 13, firstName: "Cameron", lastName: "Johnson", position: "SF", jerseyNumber: "2"},
                { id: 14, firstName: "Dorian", lastName: "Finney-Smith", position: "PF", jerseyNumber: "28"},
                { id: 15, firstName: "Nic", lastName: "Claxton", position: "C", jerseyNumber: "33" }
            ]
        },
        {
            id: 4,
            name: "Charlotte Hornets",
            players: [
                { id: 16, firstName: "LaMelo", lastName: "Ball", position: "PG", jerseyNumber: "1"},
                { id: 17, firstName: "Brandon", lastName: "Miller", position: "SG", jerseyNumber: "24" },
                { id: 18, firstName: "Josh", lastName: "Green", position: "SF", jerseyNumber: "8"},
                { id: 19, firstName: "Miles", lastName: "Bridges", position: "PF", jerseyNumber: "0" },
                { id: 20, firstName: "Mark", lastName: "Williams", position: "C", jerseyNumber: "5" }
            ]
        },
        {
            id: 5,
            name: "Chicago Bulls",
            players: [
                { id: 21, firstName: "Josh", lastName: "Giddey", position: "PG", jerseyNumber: "3"},
                { id: 22, firstName: "Coby", lastName: "White", position: "SG", jerseyNumber: "0"},
                { id: 23, firstName: "Zach", lastName: "LaVine", position: "SF", jerseyNumber: "8"},
                { id: 24, firstName: "Patrick", lastName: "Williams", position: "PF", jerseyNumber: "44"},
                { id: 25, firstName: "Nikola", lastName: "Vucevic", position: "C", jerseyNumber: "9" }
            ]
        },
        {
            id: 6,
            name: "Cleveland Cavaliers",
            players: [
                { id: 26, firstName: "Darius", lastName: "Garland", position: "PG", jerseyNumber: "10" },
                { id: 27, firstName: "Donovan", lastName: "Mitchell", position: "SG", jerseyNumber: "45" },
                { id: 28, firstName: "Isaac", lastName: "Okoro", position: "SF", jerseyNumber: "35" },
                { id: 29, firstName: "Evan", lastName: "Mobley", position: "PF", jerseyNumber: "4" },
                { id: 30, firstName: "Jarrett", lastName: "Allen", position: "C", jerseyNumber: "31" }
            ]
        },
        {
            id: 7,
            name: "Dallas Mavericks",
            players: [
                { id: 31, firstName: "Luka", lastName: "Doncic", position: "PG", jerseyNumber: "77"},
                { id: 32, firstName: "Kyrie", lastName: "Irving", position: "SG", jerseyNumber: "11" },
                { id: 33, firstName: "Klay", lastName: "Thompson", position: "SF", jerseyNumber: "31" },
                { id: 34, firstName: "P.J.", lastName: "Washington", position: "PF", jerseyNumber: "25" },
                { id: 35, firstName: "Daniel", lastName: "Gafford", position: "C", jerseyNumber: "21" }
            ]
        },
        {
            id: 8,
            name: "Denver Nuggets",
            players: [
                { id: 36, firstName: "Jamal", lastName: "Murray", position: "PG", jerseyNumber: "27" },
                { id: 37, firstName: "Christian", lastName: "Braun", position: "SG", jerseyNumber: "0" },
                { id: 38, firstName: "Michael", lastName: "Porter Jr.", position: "SF", jerseyNumber: "1"},
                { id: 39, firstName: "Aaron", lastName: "Gordon", position: "PF", jerseyNumber: "50" },
                { id: 40, firstName: "Nikola", lastName: "Jokic", position: "C", jerseyNumber: "15" }
            ]
        },
        {
            id: 9,
            name: "Detroit Pistons",
            players: [
                { id: 41, firstName: "Cade", lastName: "Cunningham", position: "PG", jerseyNumber: "2" },
                { id: 42, firstName: "Jaden", lastName: "Ivey", position: "SG", jerseyNumber: "23" },
                { id: 43, firstName: "Tim", lastName: "Hardaway Jr.", position: "SF", jerseyNumber: "10" },
                { id: 44, firstName: "Tobias", lastName: "Harris", position: "PF", jerseyNumber: "12" },
                { id: 45, firstName: "Jalen", lastName: "Duren", position: "C", jerseyNumber: "0" }
            ]
        },
        {
            id: 10,
            name: "Golden State Warriors",
            players: [
                { id: 46, firstName: "Stephen", lastName: "Curry", position: "PG", jerseyNumber: "30" },
                { id: 47, firstName: "De'Anthony", lastName: "Melton", position: "SG", jerseyNumber: "8" },
                { id: 48, firstName: "Andrew", lastName: "Wiggins", position: "SF", jerseyNumber: "22" },
                { id: 49, firstName: "Draymond", lastName: "Green", position: "PF", jerseyNumber: "23" },
                { id: 50, firstName: "Trayce", lastName: "Jackson-Davis", position: "C", jerseyNumber: "32" }
            ]
        },
        
        {
            id: 11,
            name: "Houston Rockets",
            players: [
                { id: 51, firstName: "Fred", lastName: "VanVleet", position: "PG", jerseyNumber: "5" },
                { id: 52, firstName: "Jalen", lastName: "Green", position: "SG", jerseyNumber: "4" },
                { id: 53, firstName: "Dillon", lastName: "Brooks", position: "SF", jerseyNumber: "9" },
                { id: 54, firstName: "Jabari", lastName: "Smith Jr.", position: "PF", jerseyNumber: "10" },
                { id: 55, firstName: "Alperen", lastName: "Sengun", position: "C", jerseyNumber: "28" }
            ]
        },
        {
            id: 12,
            name: "Indiana Pacers",
            players: [
                { id: 56, firstName: "Tyrese", lastName: "Haliburton", position: "PG", jerseyNumber: "0" },
                { id: 57, firstName: "Andrew", lastName: "Nembhard", position: "SG", jerseyNumber: "2" },
                { id: 58, firstName: "Bennedict", lastName: "Mathurin", position: "SF", jerseyNumber: "00" },
                { id: 59, firstName: "Pascal", lastName: "Siakam", position: "PF", jerseyNumber: "43" },
                { id: 60, firstName: "Myles", lastName: "Turner", position: "C", jerseyNumber: "33" }
            ]
        },
        {
            id: 13,
            name: "Los Angeles Clippers",
            players: [
                { id: 61, firstName: "James", lastName: "Harden", position: "PG", jerseyNumber: "1" },
                { id: 62, firstName: "Norman", lastName: "Powell", position: "SG", jerseyNumber: "24" },
                { id: 63, firstName: "Terance", lastName: "Mann", position: "SF", jerseyNumber: "14" },
                { id: 64, firstName: "Kawhi", lastName: "Leonard", position: "PF", jerseyNumber: "2" },
                { id: 65, firstName: "Ivica", lastName: "Zubac", position: "C", jerseyNumber: "40" }
            ]
        },
        {
            id: 14,
            name: "Los Angeles Lakers",
            players: [
                { id: 66, firstName: "D'Angelo", lastName: "Russell", position: "PG", jerseyNumber: "1" },
                { id: 67, firstName: "Austin", lastName: "Reaves", position: "SG", jerseyNumber: "15" },
                { id: 68, firstName: "Rui", lastName: "Hachimura", position: "SF", jerseyNumber: "28" },
                { id: 69, firstName: "LeBron", lastName: "James", position: "PF", jerseyNumber: "23" },
                { id: 70, firstName: "Anthony", lastName: "Davis", position: "C", jerseyNumber: "3" }
            ]
        },
        {
            id: 15,
            name: "Memphis Grizzlies",
            players: [
                { id: 71, firstName: "Ja", lastName: "Morant", position: "PG", jerseyNumber: "12" },
                { id: 72, firstName: "Desmond", lastName: "Bane", position: "SG", jerseyNumber: "22" },
                { id: 73, firstName: "Ziaire", lastName: "Williams", position: "SF", jerseyNumber: "8" },
                { id: 74, firstName: "Jaren", lastName: "Jackson Jr.", position: "PF", jerseyNumber: "13" },
                { id: 75, firstName: "Brandon", lastName: "Clarke", position: "C", jerseyNumber: "15" }
            ]
        },
        {
            id: 16,
            name: "Miami Heat",
            players: [
                { id: 76, firstName: "Tyler", lastName: "Herro", position: "PG", jerseyNumber: "14" },
                { id: 77, firstName: "Terry", lastName: "Rozier", position: "SG", jerseyNumber: "2" },
                { id: 78, firstName: "Jimmy", lastName: "Butler", position: "SF", jerseyNumber: "22" },
                { id: 79, firstName: "Nikola", lastName: "Jovic", position: "PF", jerseyNumber: "5" },
                { id: 80, firstName: "Bam", lastName: "Adebayo", position: "C", jerseyNumber: "13" }
            ]
        },
        {
            id: 17,
            name: "Milwaukee Bucks",
            players: [
                { id: 81, firstName: "Damian", lastName: "Lillard", position: "PG", jerseyNumber: "0" },
                { id: 82, firstName: "Gary", lastName: "Trent Jr.", position: "SG", jerseyNumber: "33" },
                { id: 83, firstName: "Khris", lastName: "Middleton", position: "SF", jerseyNumber: "22" },
                { id: 84, firstName: "Giannis", lastName: "Antetokounmpo", position: "PF", jerseyNumber: "34" },
                { id: 85, firstName: "Brook", lastName: "Lopez", position: "C", jerseyNumber: "11" }
            ]
        },
        {
            id: 18,
            name: "Minnesota Timberwolves",
            players: [
                { id: 86, firstName: "Mike", lastName: "Conley", position: "PG", jerseyNumber: "10" },
                { id: 87, firstName: "Anthony", lastName: "Edwards", position: "SG", jerseyNumber: "5" },
                { id: 88, firstName: "Jaden", lastName: "McDaniels", position: "SF", jerseyNumber: "3" },
                { id: 89, firstName: "Julius", lastName: "Randle", position: "PF", jerseyNumber: "30" },
                { id: 90, firstName: "Rudy", lastName: "Gobert", position: "C", jerseyNumber: "27" }
            ]
        },
        {
            id: 19,
            name: "New Orleans Pelicans",
            players: [
                { id: 91, firstName: "Dejounte", lastName: "Murray", position: "PG", jerseyNumber: "5" },
                { id: 92, firstName: "CJ", lastName: "McCollum", position: "SG", jerseyNumber: "3" },
                { id: 93, firstName: "Brandon", lastName: "Ingram", position: "SF", jerseyNumber: "14" },
                { id: 94, firstName: "Zion", lastName: "Williamson", position: "PF", jerseyNumber: "1" },
                { id: 95, firstName: "Daniel", lastName: "Theis", position: "C", jerseyNumber: "27" }
            ]
        },
        {
            id: 20,
            name: "New York Knicks",
            players: [
                { id: 96, firstName: "Jalen", lastName: "Brunson", position: "PG", jerseyNumber: "11" },
                { id: 97, firstName: "Mikal", lastName: "Bridges", position: "SG", jerseyNumber: "1" },
                { id: 98, firstName: "OG", lastName: "Anunoby", position: "SF", jerseyNumber: "8" },
                { id: 99, firstName: "Julius", lastName: "Randle", position: "PF", jerseyNumber: "30" },
                { id: 100, firstName: "Mitchell", lastName: "Robinson", position: "C", jerseyNumber: "23" }
            ]
        },
        {
            id: 21,
            name: "Oklahoma City Thunder",
            players: [
                { id: 101, firstName: "Shai", lastName: "Gilgeous-Alexander", position: "PG", jerseyNumber: "2" },
                { id: 102, firstName: "Jalen", lastName: "Williams", position: "SG", jerseyNumber: "8" },
                { id: 103, firstName: "Lu", lastName: "Dort", position: "SF", jerseyNumber: "5" },
                { id: 104, firstName: "Chet", lastName: "Holmgren", position: "PF", jerseyNumber: "7" },
                { id: 105, firstName: "Isaiah", lastName: "Hartenstein", position: "C", jerseyNumber: "55" }
            ]
        },
        {
            id: 22,
            name: "Orlando Magic",
            players: [
                { id: 106, firstName: "Jalen", lastName: "Suggs", position: "PG", jerseyNumber: "4" },
                { id: 107, firstName: "Gary", lastName: "Harris", position: "SG", jerseyNumber: "14" },
                { id: 108, firstName: "Franz", lastName: "Wagner", position: "SF", jerseyNumber: "22" },
                { id: 109, firstName: "Paolo", lastName: "Banchero", position: "PF", jerseyNumber: "5" },
                { id: 110, firstName: "Wendell", lastName: "Carter Jr.", position: "C", jerseyNumber: "34" }
            ]
        },
        {
            id: 23,
            name: "Philadelphia 76ers",
            players: [
                { id: 111, firstName: "Tyrese", lastName: "Maxey", position: "PG", jerseyNumber: "0" },
                { id: 112, firstName: "Kelly", lastName: "Oubre Jr.", position: "SG", jerseyNumber: "9" },
                { id: 113, firstName: "Paul", lastName: "George", position: "SF", jerseyNumber: "8" },
                { id: 114, firstName: "Caleb", lastName: "Martin", position: "PF", jerseyNumber: "16" },
                { id: 115, firstName: "Joel", lastName: "Embiid", position: "C", jerseyNumber: "21" }
            ]
        },
        {
            id: 24,
            name: "Phoenix Suns",
            players: [
                { id: 116, firstName: "Tyus", lastName: "Jones", position: "PG", jerseyNumber: "21" },
                { id: 117, firstName: "Devin", lastName: "Booker", position: "SG", jerseyNumber: "1" },
                { id: 118, firstName: "Bradley", lastName: "Beal", position: "SF", jerseyNumber: "3" },
                { id: 119, firstName: "Kevin", lastName: "Durant", position: "PF", jerseyNumber: "35" },
                { id: 120, firstName: "Jusuf", lastName: "Nurkic", position: "C", jerseyNumber: "20" }
            ]
        },
        {
            id: 25,
            name: "Portland Trail Blazers",
            players: [
                { id: 121, firstName: "Scoot", lastName: "Henderson", position: "PG", jerseyNumber: "00" },
                { id: 122, firstName: "Anfernee", lastName: "Simons", position: "SG", jerseyNumber: "1" },
                { id: 123, firstName: "Toumani", lastName: "Camara", position: "SF", jerseyNumber: "33" },
                { id: 124, firstName: "Jerami", lastName: "Grant", position: "PF", jerseyNumber: "9" },
                { id: 125, firstName: "Deandre", lastName: "Ayton", position: "C", jerseyNumber: "2" }
            ]
        },
        {
            id: 26,
            name: "Sacramento Kings",
            players: [
                { id: 126, firstName: "De'Aaron", lastName: "Fox", position: "PG", jerseyNumber: "5" },
                { id: 127, firstName: "Kevin", lastName: "Huerter", position: "SG", jerseyNumber: "9" },
                { id: 128, firstName: "DeMar", lastName: "DeRozan", position: "SF", jerseyNumber: "10" },
                { id: 129, firstName: "Keegan", lastName: "Murray", position: "PF", jerseyNumber: "13" },
                { id: 130, firstName: "Domantas", lastName: "Sabonis", position: "C", jerseyNumber: "11" }
            ]
        },
        {
            id: 27,
            name: "San Antonio Spurs",
            players: [
                { id: 131, firstName: "Chris", lastName: "Paul", position: "PG", jerseyNumber: "3" },
                { id: 132, firstName: "Devin", lastName: "Vassell", position: "SG", jerseyNumber: "24" },
                { id: 133, firstName: "Julian", lastName: "Champagnie", position: "SF", jerseyNumber: "30" },
                { id: 134, firstName: "Jeremy", lastName: "Sochan", position: "PF", jerseyNumber: "10" },
                { id: 135, firstName: "Victor", lastName: "Wembanyama", position: "C", jerseyNumber: "1" }
            ]
        },
        {
            id: 28,
            name: "Toronto Raptors",
            players: [
                { id: 136, firstName: "Immanuel", lastName: "Quickley", position: "PG", jerseyNumber: "5" },
                { id: 137, firstName: "Gradey", lastName: "Dick", position: "SG", jerseyNumber: "1" },
                { id: 138, firstName: "RJ", lastName: "Barrett", position: "SF", jerseyNumber: "9" },
                { id: 139, firstName: "Scottie", lastName: "Barnes", position: "PF", jerseyNumber: "4" },
                { id: 140, firstName: "Jakob", lastName: "Poeltl", position: "C", jerseyNumber: "19" }
            ]
        },
        {
            id: 29,
            name: "Utah Jazz",
            players: [
                { id: 141, firstName: "Keyonte", lastName: "George", position: "PG", jerseyNumber: "3" },
                { id: 142, firstName: "Jordan", lastName: "Clarkson", position: "SG", jerseyNumber: "00" },
                { id: 143, firstName: "Lauri", lastName: "Markkanen", position: "SF", jerseyNumber: "23" },
                { id: 144, firstName: "John", lastName: "Collins", position: "PF", jerseyNumber: "20" },
                { id: 145, firstName: "Walker", lastName: "Kessler", position: "C", jerseyNumber: "24" }
            ]
        },
        {
            id: 30,
            name: "Washington Wizards",
            players: [
                { id: 146, firstName: "Jordan", lastName: "Poole", position: "PG", jerseyNumber: "13" },
                { id: 147, firstName: "Malcolm", lastName: "Brogdon", position: "SG", jerseyNumber: "13" },
                { id: 148, firstName: "Bilal", lastName: "Coulibaly", position: "SF", jerseyNumber: "0" },
                { id: 149, firstName: "Kyle", lastName: "Kuzma", position: "PF", jerseyNumber: "33" },
                { id: 150, firstName: "Jonas", lastName: "Valanciunas", position: "C", jerseyNumber: "17" }
            ]
        }
    ];

    // Here we are setting the team data based on the teamID within the URL
    useEffect(() => {
        if (teamId) {
            const team = NBA_TEAMS.find(team => team.id === parseInt(teamId));
            if (team) {
                setSelectedTeam(team);
            }
        }
    }, [teamId]);

    // This is for the filename that will have the team logo attatched to it
    const getTeamImageFilename = (teamName: string) => {
        return teamName.toLowerCase().replace(/\s/g, "_");
    };

    if (!selectedTeam) {
        return <div className="p-8 text-center">Loading team data...</div>;
    }

    return (
        <div className="container mx-auto p-4">
            <div className="flex flex-col md:flex-row bg-white shadow-md rounded p-6">
                {/* Displaying The Team Logo Here */}
                <div className="md:w-1/3 flex flex-col items-center justify-center p-4">
                    <div className="w-64 h-64 flex items-center justify-center mb-4">
                        <Image 
                            dynamicSourcePath="Teams" 
                            dynamicSource={`${getTeamImageFilename(selectedTeam.name)}.png`} 
                            alt={`${selectedTeam.name} logo`}
                            className="max-w-full max-h-full object-contain"
                        />
                    </div>
                    <h1 className="text-3xl font-bold text-center">{selectedTeam.name}</h1>
                </div>

                {/* Going To Display The Starting 5 Here */}
                <div className="md:w-2/3">
                    <div className="bg-gray-100 rounded-lg p-6">
                        <h2 className="text-2xl font-bold mb-4">Starting Five</h2>
                        <div className="space-y-4">
                            {selectedTeam.players.map(player => (
                                <TeamPlayerCard 
                                    key={player.id}
                                    firstName={player.firstName} 
                                    lastName={player.lastName} 
                                    jerseyNumber={player.jerseyNumber} 
                                    position={player.position}
                                />
                            ))}
                        </div>
                    </div>
                </div>
            </div>
        </div>
    );
}