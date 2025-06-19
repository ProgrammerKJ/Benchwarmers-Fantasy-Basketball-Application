import Carousel from "../Components/Carousel";
import First from "../Assets/HomeCar/First.jpg";
import Second from "../Assets/HomeCar/Second.jpg";
import Third from "../Assets/HomeCar/Third.jpg";
import BenchWarmersBanner from "../Assets/Banner/fantasy_league.png"
import {Link} from "react-router-dom";
interface HomePageProps {
  appApi: string;
  radarApi: string;
}
const carouselImages = [First, Second, Third];

function HomePage(props: HomePageProps) {
  return (
    <div className="flex flex-col">
      {/* Add the banner image here */}
      <div className="w-full">
        <img 
          src={BenchWarmersBanner} 
          alt="Benchwarmers Fantasy League" 
          className="w-full object-cover"
        />
      </div>
      
      {/* Hero Section */}
      <section
        className="relative h-[68vh] bg-cover bg-center flex items-center justify-center text-white text-center"
        style={{
          backgroundImage: "linear-gradient(fgba(0, 0, 0, 0.5), rgba(0, 0, 0, 0.7)), url('/api/placeholder/1200/800')",
          backgroundPosition: "center",
          backgroundSize: "cover"
        }}
      >
        <div className="bg-black/40 p-10 rounded-lg border border-gray-700 max-w-2xl mx-auto backdrop-blur-sm">
          <h1 className="text-5xl md:text-6xl font-bold mb-6">
            Draft Your Dream Team
          </h1>
          <p className="mt-6 text-xl font-light mb-8">
            Join the ultimate NBA Fantasy League expierence.
          </p>
          <Link to={"/league"}>
            <button className="mt-6 px-8 py-4 bg-orange-500 hover:bg-orange-600 text-white text-lg font-bold rounded-lg transition duration-300 transform hover:scale-105">
              Get Started
            </button>
          </Link>
        </div>
      </section>

      {/* Highlights Section */}

      <section className="py-12 bg-white">
        <div className="container mx-auto px-4">
          <h2 className="text-2xl font-bold text-center mb-6">
            Game Highlights
          </h2>
          <div className="grid md:grid-cols-3 gap-6">
            {
              gameHighlights.map((highlight) => (
                <div key={highlight.title} className="rounded overflow-hidden shadow-lg bg-gray-50 ">
                    <iframe src={highlight.VideoUrl} title={highlight.title} className="w-full h-96" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture" allowFullScreen>

                    </iframe>
                    <div className="p-4">
                    <h3 className="font-semibold text-lg">{highlight.title}</h3>
                    <p className="text-sm text-gray-600">{highlight.description}</p>
                      </div>
                  </div>
              ))
            }
          </div>
        </div>
      </section>

      {/* Carousel Section */}

      <section className="py-12 bg-gray-100">
        <div className="container mx-auto px-4">
          <h2 className="text-2xl font-bold text-center mb-6">
            Photo Highlights
          </h2>
          <Carousel images={carouselImages}/>
        </div>
      </section>

      {/* CTA Section */}
      <section className="py-16 bg-blue-600 text-white text-center">
        <h2 className="text-3xl font-bold mb-4">Ready to Compete?</h2>
        <button className="px-6 py-3 bg-white text-blue-600 font-semibold rounded-md hover:bg-gray-100">
          Join a League
        </button>
      </section>
    </div>
  );
}

const gameHighlights = [
  {
    title: 'Clippers Vs. Warriors Highlights',
    description: 'The Clippers improve their record to 50-32 with the win, while the Warriors fall to 48-34 for the season',
    VideoUrl: 'https://www.youtube.com/embed/rQ7ONhFsX2k?si=d8x-O50hLhj5mtR7',
  },
  {
    title: 'Luka Dončić DOMINATES In Return To Dallas',
    description: 'Luka Dončić finished with a team-high 45 points along with 8 rebounds and 7 three pointers for the Lakers, while LeBron James tallied 27 points, 7 rebounds and 3 assists in the game.',
    VideoUrl: 'https://www.youtube.com/embed/oq7pb84S_jo?si=NIgEW52hUDqACGqK',
  },
  {
    title: 'Kobe Highlights but they get increasingly more legendary',
    description: "These are some of Kobe's greatest career plays but as the video goes on the plays get more legendary!",
    VideoUrl: 'https://www.youtube.com/embed/adRunnK0muY?si=U4ywx9xN99gAn7Mf',
  },
]

export default HomePage;