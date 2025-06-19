import Image from "./Image";

// player name to team mapping
const PLAYER_TEAM_MAPPING: Record<string, string> = {
    "Shai Gilgeous-Alexander": "oklahoma_city_thunder",
    "Anthony Edwards": "minnesota_timberwolves",
    "Nikola Jokić": "denver_nuggets",
    "Giannis Antetokounmpo": "milwaukee_bucks",
    "Jayson Tatum": "boston_celtics",
    "Devin Booker": "phoenix_suns",
    "Trae Young": "atlanta_hawks",
    "Tyler Herro": "miami_heat",
    "Cade Cunningham": "detroit_pistons",
    "James Harden": "los_angeles_clippers",
    "Karl-Anthony Towns": "new_york_knicks",
    "Zach LaVine": "sacramento_kings",
    "Jalen Green": "houston_rockets",
    "DeMar DeRozan": "sacramento_kings",
    "Stephen Curry": "golden_state_warriors",
    "LeBron James": "los_angeles_lakers",
    "Donovan Mitchell": "cleveland_cavaliers",
    "Jalen Brunson": "new_york_knicks",
    "Kevin Durant": "phoenix_suns",
    "Jaren Jackson": "memphis_grizzlies",
    "Pascal Siakam": "indiana_pacers",
    "Darius Garland": "cleveland_cavaliers",
    "Coby White": "chicago_bulls",
    "Jalen Williams": "oklahoma_city_thunder",
    "Austin Reaves": "los_angeles_lakers"
  };

interface PlayerCardProps{
    firstName: string,
    lastName: string,
    position: string,
    jerseyNumber: string,
    fantasyPoints: number,
    teamId?: number,
    teamName?: string
    addButtonFunc?: React.MouseEventHandler
}

function PlayerCard(props: PlayerCardProps){
    const {firstName, lastName, position, jerseyNumber, fantasyPoints, teamId, teamName, addButtonFunc} = props;

    const getImageFilename = () => {
        const fullName = `${firstName} ${lastName}`;
        
        // Chscking if we can match off of full name
        if (PLAYER_TEAM_MAPPING[fullName]) {
            return PLAYER_TEAM_MAPPING[fullName];
        }
        return "placeholder";
    };
    
    const imageFilename = getImageFilename();

    return(
        <figure className={"flex flex-row m-1.5 items-center bg-gray-200 shadow-md rounded px-8 pt-6 pb-8 mb-4"}>
            {/* We are going to display team logos here */}
            <div className="mr-4 flex-shrink-0 w-20 h-20 flex items-center justify-center">
                <Image 
                    dynamicSourcePath={"Teams"} 
                    dynamicSource={`${imageFilename}.png`} 
                    className={"max-h-20 max-w-20 object-contain"}
                    alt={teamName ? `${teamName} logo` : "Team logo"}
                />
            </div>
            
            <div className={"flex flex-grow justify-start items-center"}>
                <section className={"text-2xl"}>
                    <p>
                        <span className={"font-bold"}>
                            Name:
                        </span>
                        <span>
                            {` ${firstName} ${lastName} `}
                        </span>
                        {teamName &&
                            <>
                                <span className={"font-bold"}>
                                Team:
                                </span>
                                <span>
                                    {` ${teamName}`}
                                </span>
                            </>
                        }
                    </p>
                    <p>
                        <span className={"font-bold"}>
                            Position:
                        </span>
                        <span>
                            {` ${position} `}
                        </span>
                        <span className={"font-bold"}>
                            Jersey Number:
                        </span>
                        <span>
                            {` ${jerseyNumber}`}
                        </span>
                    </p>
                    <p>
                        <span className={"font-bold"}>
                            Current Fantasy Points:
                        </span>
                        <span>
                            {` ${fantasyPoints} `}
                        </span>
                    </p>
                </section>
                {addButtonFunc &&
                    <button 
                        onClick={addButtonFunc}
                        className="ml-4 bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded"
                    >
                        Add
                    </button>
                }
            </div>
        </figure>
    );
}

export default PlayerCard;