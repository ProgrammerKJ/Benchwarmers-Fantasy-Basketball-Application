import { Link } from "react-router-dom";
import Image from "./Image";
interface TeamCardProps{
    teamName: string,
    teamId: number,
    className: string
}

function TeamCard(props: TeamCardProps) {
    const { teamName, teamId, className } = props;
    
    const imageFilename = teamName.toLowerCase().replace(/\s/g, "_");
    
    return (
        <Link to={`/team/${teamId}`} className={className}>
            <figure className="flex flex-col m-1.5 items-center bg-white shadow-md rounded px-8 pt-6 pb-8 mb-4 hover:shadow-lg transition-shadow duration-300">
                <div className="w-32 h-32 flex items-center justify-center">
                    <Image 
                        dynamicSourcePath="Teams" 
                        dynamicSource={`${imageFilename}.png`} 
                        alt={`${teamName} logo`}
                        className="max-w-full max-h-full object-contain"
                    />
                </div>
                <figcaption className="text-xl font-bold mt-4 text-center">
                    {teamName}
                </figcaption>
            </figure>
        </Link>
    );
}

export default TeamCard;