import {useEffect, useState} from "react";
import notFound from "../Assets/image-not-found-icon.png";

interface ImageProps{
    src?: string,
    alt?: string,
    className?: string,
    dynamicSource?: string
    dynamicSourcePath?: string,

}


function Image(props: ImageProps){
    const {src, alt, className, dynamicSource, dynamicSourcePath} = props
    const [hasError, setHasError] = useState(false);
    const [imageSrc, setImageSrc] = useState(src);

    function generateAlt() {
        const path = src?.split("/") || [""];
        return path[path.length - 1]

    }

    function handleImageError(event: React.SyntheticEvent){
        event.preventDefault();
        if (event.target && !hasError){
            setImageSrc(notFound);
            setHasError(true);
        }
    }

    function dynamicSourceAttach(){
        if (dynamicSource && dynamicSourcePath) {
            try {

                setImageSrc(require(`../Assets/${dynamicSourcePath}/${dynamicSource}`));

            } catch {
                setHasError(true);
                setImageSrc(notFound);
            }
        }
    }
    useEffect(() => dynamicSourceAttach(),[])

    return(
        <img src={imageSrc} alt={alt || generateAlt()} className={className} onError={handleImageError}/>
    );
}

export default Image;