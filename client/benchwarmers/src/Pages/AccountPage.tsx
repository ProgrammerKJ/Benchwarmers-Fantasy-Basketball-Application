import FormInput from "../Components/FormInput";
import AppForm from "../Components/AppForm";
import { use, useEffect, useState } from "react";
import { checkAuthToken, checkResStatus } from "../UtilityFunctions/api";
import ErrorBlock from "../Components/ErrorBlock";
import { useNavigate } from "react-router-dom";

interface AccountPageProps {
  appApi: string;
  radarApi: string;
  appUser: string;
  type: string;
  isLoggedIn: boolean;
  setIsLoggedIn: Function;
}

interface accountForm {
  id?: string;
  email: string;
  password: string;
  username: string;
  favoriteTeam: string;
}

const typeToButton: Map<string, string> = new Map([
  ["signup", "Sign Up"],
  ["login", "Login"],
  ["edit", "Submit"],
]);

const typeToTitle: Map<string, string> = new Map([
  ["signup", "Sign Up"],
  ["login", "Login"],
  ["edit", "Edit/View Account"],
]);

let ACCOUNT_DEFAULT: accountForm = {
  email: "",
  password: "",
  username: "",
  favoriteTeam: "",
};

const teamOptions: Record<string, string> = {
  HAWKS: "Atlanta Hawks",
  CELTICS: "Boston Celtics",
  NETS: "Brooklyn Nets",
  HORNETS: "Charlotte Hornets",
  BULLS: "Chicago Bulls",
  CAVALIERS: "Cleveland Cavaliers",
  MAVERICKS: "Dallas Mavericks",
  NUGGETS: "Denver Nuggets",
  PISTONS: "Detroit Pistons",
  WARRIORS: "Golden State Warriors",
  ROCKETS: "Houston Rockets",
  PACERS: "Indiana Pacers",
  CLIPPERS: "Los Angeles Clippers",
  LAKERS: "Los Angeles Lakers",
  GRIZZLIES: "Memphis Grizzlies",
  HEAT: "Miami Heat",
  BUCKS: "Milwaukee Bucks",
  TIMBERWOLVES: "Minnesota Timberwolves",
  PELICANS: "New Orleans Pelicans",
  KNICKS: "New York Knicks",
  THUNDER: "Oklahoma City Thunder",
  MAGIC: "Orlando Magic",
  SIXERS: "Philadelphia 76ers",
  SUNS: "Phoenix Suns",
  BLAZERS: "Portland Trail Blazers",
  KINGS: "Sacramento Kings",
  SPURS: "San Antonio Spurs",
  RAPTORS: "Toronto Raptors",
  JAZZ: "Utah Jazz",
  WIZARDS: "Washington Wizards",
};

export default function AccountPage(props: AccountPageProps) {
  const { appApi, appUser, type, isLoggedIn, setIsLoggedIn } = props;
  const [accountDetails, setAccountDetails] = useState(ACCOUNT_DEFAULT);
  const [errorList, setErrorList] = useState<string[]>([]);
  const nav = useNavigate();

  //Form Handlers
  function handleChange(
    event: React.ChangeEvent<HTMLInputElement | HTMLSelectElement>
  ) {
    const { name, value } = event.target;
    const modAccountDetails: accountForm = { ...accountDetails };
    modAccountDetails[name as keyof accountForm] = value;
    setAccountDetails(modAccountDetails);
  }

  function handleSubmit(event: React.FormEvent) {
    event.preventDefault();

    if (type === "login") return loginAccount();
    if (type === "signup") return createAccount();
    if (type === "edit") return modifyAccount();

    console.error(`Unhandled form type: ${type}`);
  }

  // Effects
  async function prefillIfSignedIn() {
    const user = await checkAuthToken();
    if (user) {
      const modAccountDetails = {...accountDetails};
      modAccountDetails["id"] = user.id.toString();
      modAccountDetails["email"] = user.email;
      modAccountDetails["username"] = user.username;
      modAccountDetails["favoriteTeam"] = user.favoriteTeam;
      setAccountDetails(modAccountDetails);
    }
  }
  useEffect(() => {
    if (type === "edit") {
      prefillIfSignedIn();
    }
  }, []);

  //Submission Handlers
  function createAccount() {
    let options = {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify(accountDetails),
    };

    fetch(`${appApi}/register`, options)
      .then((res) => {
        return checkResStatus(res, [201, 400]);
      })
      .then((data) => {
        console.log("Signup response data:", data);
        if (data.appUserId) {
          nav("/");
        } else {
          setErrorList(data);
        }
      })
      .catch(console.log);
  }

  function modifyAccount() {
    const token = localStorage.getItem("jwt_token");
    const username = accountDetails.username;

  if (!token || !username) {
    console.error("Missing token or username.");
    return;
  }

  const options = {
    method: "PUT",
    headers: {
      "Content-Type": "application/json",
      "Authorization": `Bearer ${token.slice(1, token.length - 1)}`
    },
    body: JSON.stringify({
      email: accountDetails.email,
      favorite_team: accountDetails.favoriteTeam,
      password: accountDetails.password,
      disabled: false,
      role: "ADMIN"
    })
  };


    fetch(`${appUser}/${username}`, options)
    .then(res => {
      if (res.status === 204 || res.status === 200) {
        nav("/"); 
      } else if (res.status === 400) {
        return res.json().then(data => setErrorList(data));
      } else {
        return Promise.reject(`Unexpected Status Code: ${res.status}`);
      }
    })
    .catch(console.log);
}
  

  function deleteAccount(event: React.MouseEvent) {
    const token = localStorage.getItem("jwt_token");
    const userId = accountDetails.id;
  
    if (!token || !userId) {
      console.error("Missing token or user ID for account deletion.");
      return;
    }
  
    if (!window.confirm("Are you sure you want to delete your account?")) {
      return;
    }
  
    fetch(`${appUser}/${userId}`, {
      method: "DELETE",
      headers: {
        "Authorization": `Bearer ${token.slice(1, token.length - 1)}`
      }
    })
      .then((res) => {
        if (res.status === 204) {
          localStorage.removeItem("jwt_token");
          localStorage.removeItem("username");
          setIsLoggedIn(false);
          nav("/");
        } else {
          return Promise.reject(`Unexpected Status Code: ${res.status}`);
        }
      })
      .catch(console.log);
  }

  function loginAccount() {
    const options = {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify(accountDetails),
    };

    fetch(`${appApi}/authenticate`, options)
      .then((res) => checkResStatus(res, [200, 401, 403, 400]))
      .then((data) => {
        console.log(data);
        if (data.jwt_token) {
          localStorage.setItem("jwt_token", JSON.stringify(data.jwt_token));
          localStorage.setItem("username", accountDetails.username);
          setIsLoggedIn(true);
          console.log(" Login successful, navigating to /league");
          nav("/league");
        } else {
          setErrorList(data.messages || ["Invalid username or password"]);
        }
      })
      .catch(err => {
        console.log(err);
        setErrorList(["Login has failed. Check your username or password"])
      });
  }

  return (
    <>
      <ErrorBlock errorList={errorList} />
      <div className={"flex items-center justify-center"}>
        <h1 className="text-3xl font-bold mb-4">{typeToTitle.get(type)} </h1>
      </div>
      <hr />
      <div className="flex items-center justify-center w-full max-w pt-6">
        <AppForm onSubmit={handleSubmit}>
          {/* Username for all types */}
          <FormInput
            id="username"
            type="text"
            onChange={handleChange}
            value={accountDetails.username}
          />

          {/* Favorite Team for signup and edit */}
          {["signup", "edit"].includes(type) && (
            <>
              <label
                htmlFor="favoriteTeam"
                className="block mb-2 text-sm font-medium text-gray-700"
              >
                Favorite Team
              </label>
              <select
                id="favoriteTeam"
                name="favoriteTeam"
                value={accountDetails.favoriteTeam}
                onChange={handleChange}
                className="block w-full p-2 border rounded shadow-sm text-sm"
              >
                {Object.entries(teamOptions).map(([enumName, label]) => (
                  <option key={enumName} value={enumName}>
                    {label}
                  </option>
                ))}
              </select>
            </>
          )}

          {/* Email only for signup */}
          {type === "signup" && (
            <FormInput
              id="email"
              type="email"
              onChange={handleChange}
              value={accountDetails.email}
            />
          )}

          {/* Password for login and signup */}
          {["signup", "login"].includes(type) && (
            <FormInput
              id="password"
              type="password"
              onChange={handleChange}
              value={accountDetails.password}
            />
          )}

          <div className="flex items-center justify-between mt-4">
            <button
              className="bg-indigo-600 px-4 py-2 rounded text-white hover:bg-indigo-500 text-sm"
              type="submit"
            >
              {typeToButton.get(type) || "Submit"}
            </button>
            {type === "edit" && (
              <button
                className="bg-red-500 px-4 py-2 rounded text-white hover:bg-red-400 text-sm"
                onClick={deleteAccount}
              >
                Delete Account
              </button>
            )}
          </div>
        </AppForm>
      </div>
    </>
  );
}
