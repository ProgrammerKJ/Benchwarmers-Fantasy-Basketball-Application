import ErrorBlock from "../Components/ErrorBlock";
import AppForm from "../Components/AppForm";
import FormInput from "../Components/FormInput";
import {useEffect, useState} from "react";
import {checkResStatus, getLeagueListsFromToken} from "../UtilityFunctions/api";
import {useNavigate, useParams} from "react-router-dom";

interface LeagueAccountPageProps {
    appApi: string,
    radarApi: string,
    type: string
    leagueApi: string
}

interface leagueForm{
    adminId:number,
    name: string,
    season: number

}

const LEAGUE_DEFAULT: leagueForm = {
    adminId: 0,
    name: "",
    season: 0
}

export default function LeagueAccountPage(props: LeagueAccountPageProps) {
    const { appApi, leagueApi, radarApi, type} = props;
    const [leagueDetails, setLeagueDetails] = useState(LEAGUE_DEFAULT);
    const [errorList, setErrorList] = useState<string[]>([]);

    const[userList, setUserList] = useState<string[]>([]);
    const[selectedUser, setSelectedUser] = useState<string>("");
    const nav = useNavigate();
    const {leagueId} = useParams();


    function handleChange(event: React.ChangeEvent<HTMLInputElement>){
        const modLeagueDetails: leagueForm = {...leagueDetails};
        switch (event.target.name) {
            case "name":
                modLeagueDetails.name = event.target.value;
                break;
            case "season":
                modLeagueDetails.season = parseInt(event.target.value);
                break;
        }

        setLeagueDetails(modLeagueDetails);
    }

    function handleSubmit(event: React.FormEvent){
        event.preventDefault();

        switch (type){
            case "create":
                createLeague();
                break;
            case "edit":
                modifyLeague();
                break;
            default:
                console.log("Unreachable switch encountered!");
                break;
        }
    }

    // Effects
    async function prefillIfEdit() {
        const adminLeagueUser = await getLeagueListsFromToken();
        const modLeagueDetails = {...leagueDetails};

        if (adminLeagueUser) {

            modLeagueDetails.adminId = adminLeagueUser.id;

            if (adminLeagueUser.owned) {
                modLeagueDetails.name = adminLeagueUser.owned.name;
                modLeagueDetails.season = adminLeagueUser.owned.season;

            }

        }
        setLeagueDetails(modLeagueDetails);
    }
    useEffect(() => {prefillIfEdit()},[]);


    //Submission Handlers
    async function createLeague(){
        const token = localStorage.getItem("jwt_token");
  const username = localStorage.getItem("username");

  if (!token || !username) return;

  const options = {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
      "Authorization": `Bearer ${token.slice(1, token.length - 1)}`
    },
    body: JSON.stringify({
      name: leagueDetails.name,
      season: leagueDetails.season,
      adminName: username
    })
  };

  try {
    const res = await fetch(`${props.leagueApi}`, options);
    const data = await checkResStatus(res, [201, 400]);
    console.log("Created league:", data);
    nav("/league"); 
  } catch (err) {
    console.error(err);
  }
    }

   async function modifyLeague(){
    const token = localStorage.getItem("jwt_token");

  if (!token) return;

  const options = {
    method: "PUT",
    headers: {
      "Content-Type": "application/json",
      "Authorization": `Bearer ${token.slice(1, token.length - 1)}`
    },
    body: JSON.stringify({
      name: leagueDetails.name,
      season: leagueDetails.season
    })
  };

  try {
    const res = await fetch(`${props.leagueApi}/${leagueId}`, options);
    await checkResStatus(res, [200, 204]);
    console.log("League updated.");
    nav("/league");
  } catch (err) {
    console.error(err);
  }
    }

   async function deleteLeague(event: React.MouseEvent){
    const token = localStorage.getItem("jwt_token");

    if (!token || !leagueDetails.adminId) return;
  
    if (!window.confirm("Are you sure you want to delete this league?")) return;
  
    try {
      const res = await fetch(
        `${props.leagueApi}/${leagueDetails.adminId}/admin/${leagueDetails.adminId}`,
        {
          method: "DELETE",
          headers: {
            "Authorization": `Bearer ${token.slice(1, token.length - 1)}`
          }
        }
      );
  
      await checkResStatus(res, [204]);
      console.log("League deleted.");
      nav("/league");
    } catch (err) {
      console.error(err);
    }
    }

    function removeUser(event: React.MouseEvent){

    }



    return (
        <>
            <ErrorBlock errorList={errorList}/>
            <div className={"flex items-center justify-center"}>
                <h1 className="text-3xl font-bold mb-4">{`${type[0].toUpperCase() + type.slice(1)} League`} </h1>
            </div>
            <hr/>
            <div className={"flex flex-col items-center justify-center w-full max-w pt-6"}>

                <AppForm onSubmit={handleSubmit}>
                    <h2 className={"text-xl  font-semibold mb-4"}>League Details</h2>

                    <FormInput id={"name"} type={"text"} onChange={handleChange} value={leagueDetails.name}/>
                    <FormInput id={"season"} type={"number"} onChange={handleChange} value={leagueDetails.season.toString()}/>


                    <div className={"flex items-center justify-between"}>
                        <button className={"bg-indigo-600 px-4 py-2 rounded text-white hover:bg-indigo-500 text-sm"} type={"submit"}>
                            Submit
                        </button>
                        {type === "edit" &&
                            <button className={"bg-red-500 px-4 py-2 rounded text-white hover:bg-red-400 text-sm"} onClick={deleteLeague}>
                                Delete League
                            </button>
                        }
                    </div>
                </AppForm>
            </div>
        </>
    )
}