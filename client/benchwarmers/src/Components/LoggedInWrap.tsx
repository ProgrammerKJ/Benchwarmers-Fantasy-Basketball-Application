import { Link } from "react-router-dom";

interface LoggedInWrapProps {
  isLoggedIn: boolean;
  setIsLoggedIn: Function;
  children?: React.ReactNode;
}

function LoggedInWrap(props: LoggedInWrapProps) {
  const { isLoggedIn, setIsLoggedIn } = props;

  return (
    <>
      {!isLoggedIn && (
        <>
          <div className="flex items-center justify-center min-h-screen bg-gray-100">
            <section className="bg-white p-6 rounded shadow text-center">
              <h1 className="text-3xl font-bold mb-2">
                Must be Logged In to Interact!
              </h1>
              <p className="text-gray-700 mb-6">Would you like to sign up?</p>
              <div className="flex justify-center gap-4">
                <Link
                  to={"/signup"}
                  className="bg-indigo-600 px-4 py-2 rounded text-white hover:bg-indigo-500 text-sm"
                >
                  SIGNUP
                </Link>
                <Link
                  to={"/login"}
                  className="bg-indigo-600 px-4 py-2 rounded text-white hover:bg-indigo-500 text-sm"
                >
                  LOGIN
                </Link>
              </div>
            </section>
          </div>
        </>
      )}
      {isLoggedIn && props.children}
    </>
  );
}

export default LoggedInWrap;
