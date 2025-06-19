import {Link} from "react-router-dom";
import {useEffect, useState} from "react";
import {checkAuthToken} from "../UtilityFunctions/api";

interface NavBarProps{
    isLoggedIn: boolean,
    setIsLoggedIn: Function
}
export default function NavBar(props: NavBarProps) {

    const {isLoggedIn, setIsLoggedIn} = props;



    return (
      <>
     <nav>
    <div className="">
      <div className="flex justify-between h-16 px-10 shadow items-center">
        <div className="flex items-center space-x-8">
          <Link to={"/"}>
            <h1 className="text-xl lg:text-2xl font-bold cursor-pointer">Benchwarmers</h1>
          </Link>
          <div className="hidden md:flex space-x-4 justify-bewtween">
            <Link to={"/"} className="hover:text-indigo-600 text-gray-700">Home</Link>
            <Link to={"/team"} className="hover:text-indigo-600 text-gray-700">Teams</Link>
            <Link to={"/player"} className="hover:text-indigo-600 text-gray-700">Players</Link>
            <Link to={"/league"} className="hover:text-indigo-600 text-gray-700">Fantasy</Link>
          </div>
        </div>
        <div className="flex space-x-4 items-center">
            {!isLoggedIn &&
                <>
                <Link to={"/login"} className="text-gray-800 text-sm">LOGIN</Link>
                <Link to={"/signup"} className="bg-indigo-600 px-4 py-2 rounded text-white hover:bg-indigo-500 text-sm">SIGNUP</Link>
                </>
            }
            {isLoggedIn &&
                <>
                    <button onClick={() => {setIsLoggedIn(false); localStorage.clear();
                    }} className="text-gray-800 text-sm">LOG OUT</button>
                    <Link to={"/editAccount"} className="bg-indigo-600 px-4 py-2 rounded text-white hover:bg-indigo-500 text-sm">PROFILE</Link>
                </>
            }

        </div>
      </div>
    </div>
  </nav>
      </>  
    )
}