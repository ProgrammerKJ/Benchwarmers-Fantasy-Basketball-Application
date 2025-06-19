import { Link } from "react-router-dom";
import NickYoungGif from "../Assets/NotFoundPageIMG/NickYoungConfused.jpg"

function NotFoundPage() {
  return (
    <div className="min-h-screen flex flex-col items-center justify-center bg-gray-100 p-4">
      <div className="bg-white rounded-lg shadow-lg p-8 max-w-md w-full text-center">
        <h1 className="text-6xl font-bold text-gray-800 mb-2">404</h1>
        <h2 className="text-2xl font-bold text-gray-700 mb-6">Page Not Found</h2>
        
        <div className="mb-6">
          <img 
            src={NickYoungGif}
            alt="Nick Young Confused" 
            className="mx-auto rounded-lg w-full max-w-sm"
          />
        </div>
        
        <p className="text-gray-600 mb-8">
          Looks like you've stumbled upon a page that doesn't exist... 
          Go ahead and head back to home court.
        </p>
        
        <Link to={"/"} className="inline-block bg-blue-600 hover:bg-blue-700 text-white font-bold py-3 px-6 rounded-lg transition duration-300">
          Back to Home Court
        </Link>
      </div>
    </div>
  );
}

export default NotFoundPage;