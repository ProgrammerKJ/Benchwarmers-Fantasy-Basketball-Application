import TeamCard from "../Components/TeamCard";

interface TeamObj {
    id: number,
    name: string
}

interface TeamPageProps{
    appApi: string,
    radarApi: string,
}

const NBA_TEAMS: TeamObj[] = [
    {id: 1, name: "Atlanta Hawks"},
    {id: 2, name: "Boston Celtics"},
    {id: 3, name: "Brooklyn Nets"},
    {id: 4, name: "Charlotte Hornets"},
    {id: 5, name: "Chicago Bulls"},
    {id: 6, name: "Cleveland Cavaliers"},
    {id: 7, name: "Dallas Mavericks"},
    {id: 8, name: "Denver Nuggets"},
    {id: 9, name: "Detroit Pistons"},
    {id: 10, name: "Golden State Warriors"},
    {id: 11, name: "Houston Rockets"},
    {id: 12, name: "Indiana Pacers"},
    {id: 13, name: "Los Angeles Clippers"},
    {id: 14, name: "Los Angeles Lakers"},
    {id: 15, name: "Memphis Grizzlies"},
    {id: 16, name: "Miami Heat"},
    {id: 17, name: "Milwaukee Bucks"},
    {id: 18, name: "Minnesota Timberwolves"},
    {id: 19, name: "New Orleans Pelicans"},
    {id: 20, name: "New York Knicks"},
    {id: 21, name: "Oklahoma City Thunder"},
    {id: 22, name: "Orlando Magic"},
    {id: 23, name: "Philadelphia 76ers"},
    {id: 24, name: "Phoenix Suns"},
    {id: 25, name: "Portland Trail Blazers"},
    {id: 26, name: "Sacramento Kings"},
    {id: 27, name: "San Antonio Spurs"},
    {id: 28, name: "Toronto Raptors"},
    {id: 29, name: "Utah Jazz"},
    {id: 30, name: "Washington Wizards"}
];

export default function TeamsPage(props: TeamPageProps) {
    return (
        <section className="flex flex-wrap justify-center">
            {NBA_TEAMS.map(team => (
                <TeamCard 
                    key={team.id} 
                    className="basis-1/6 min-w-64 p-2" 
                    teamName={team.name} 
                    teamId={team.id}
                />
            ))}
        </section>
    );
}