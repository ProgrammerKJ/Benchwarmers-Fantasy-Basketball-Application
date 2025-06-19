export function checkResStatus(res: Response, acceptedStatus: number[]){
    if (acceptedStatus.includes(res.status)){
      if (res.status === 204) return Promise.resolve();
        return res.json();
    }
    else {
        return Promise.reject(`Unexpected Status Code: ${res.status}`);
    }
}


interface authUser{
    id: number, email:string, username: string, favoriteTeam: string
}

export async function checkAuthToken(){
    const token = localStorage.getItem("jwt_token");
    //TEST

    if (token) {
        const options = {
            method: "GET",
            headers: {
                "Content-Type": "application/json",
                "Authorization": `Bearer ${token.slice(1,token.length-1)}`
            }
        }


        return fetch(`http://localhost:8080/api/app-user/`, options).then(res => {
            return checkResStatus(res, [200]);
        }).then(data => {
            const selectedUser = data.filter((user: { username: string | null; }) => user.username === localStorage.getItem("username"))[0];
            const returnUser: authUser = {id: selectedUser.userId, email:selectedUser.email, favoriteTeam:selectedUser.favoriteTeam, username:selectedUser.username}

            return returnUser;
        }).catch(console.log);
    }
    else{
        return undefined;
    }
}

interface userLeagues {
    id: number,
    owned?: League,
    partOf?: League
}
interface League{
    fantasyLeagueId: number,
    name: string,
    season: number,
    admin: MemberUser,
    members: MemberUser[]
}

interface MemberUser{
    username: string
}


export async function getLeagueListsFromToken() {
    const authUser = await checkAuthToken();
    const token = localStorage.getItem("jwt_token");


    if (authUser){
        const leagues: userLeagues = {id: authUser.id}

        /* Fetch Member Leagues Using authUserId */
        const options = {
            method: "GET",
            headers: {
                "Content-Type": "application/json",
                "Authorization": `Bearer ${token?.slice(1,token.length-1)}`
            }
        }
        const memberLeagues = await fetch("http://localhost:8080/api/fantasy-league", options).then((res) => {return checkResStatus(res,[200])});

        let test = memberLeagues.filter((league: League) => league.members.filter((member: MemberUser) => member.username === localStorage.getItem("username")).length > 0)[0];

        leagues.partOf = test

        /* Fetch Admin League same thing*/

        leagues.owned = memberLeagues.filter((league: { admin: { username: string | null; }; }) => league.admin.username === localStorage.getItem("username"))[0];



        if (leagues.owned){
            leagues.partOf = leagues.owned;
        }

        return leagues;
    }
}

export default {checkResStatus, checkAuthToken};