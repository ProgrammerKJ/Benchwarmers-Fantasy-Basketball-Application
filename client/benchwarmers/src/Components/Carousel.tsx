import Slider from 'react-slick';
import 'slick-carousel/slick/slick.css';
import 'slick-carousel/slick/slick-theme.css';
import Image from "./Image";

interface CarouselProps {
    images: string[];
}

const Carousel: React.FC<CarouselProps> = ({ images }) => {
    const settings = {
      dots: true,
      infinite: true,
      speed: 500,
      slidesToShow: 1,
      slidesToScroll: 1,
      autoplay: true,
      autoplaySpeed: 3500,
    };
  
    return (
      <Slider {...settings}>
        {images.map((img, index) => (
          <div key={index} className="px-2">
            <Image
              src={img}
              alt={`Slide ${index + 1}`}
              className="rounded-lg w-full h-[500px] object-cover"
            />
          </div>
        ))}
      </Slider>
    );
  };
  
  export default Carousel;