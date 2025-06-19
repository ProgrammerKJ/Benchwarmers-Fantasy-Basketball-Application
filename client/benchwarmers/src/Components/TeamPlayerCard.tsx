import Image from "./Image";

interface TeamPlayerCardProps {
    firstName: string,
    lastName: string,
    position: string,
    jerseyNumber: string
}

function TeamPlayerCard(props: TeamPlayerCardProps) {
    const {firstName, lastName, position, jerseyNumber} = props;

    return(
        <figure className={"flex flex-row m-1.5 items-center bg-gray-200 shadow-md rounded px-8 pt-6 pb-8 mb-4"}>
            <div className={"flex flex-grow justify-center items-center"}>
                <section className={"text-2xl"}>
                    <p>
                        <span className={"font-bold"}>
                            Name:
                        </span>
                        <span>
                            {` ${firstName} ${lastName} `}
                        </span>
                    </p>
                    <p>
                        <span className={"font-bold"}>
                            Position:
                        </span>
                        <span>
                            {` ${position} `}
                        </span>
                        <span className={"font-bold"}>
                            Jersey Number:
                        </span>
                        <span>
                            {` ${jerseyNumber}`}
                        </span>
                    </p>
                </section>
            </div>
        </figure>
    );
}

export default TeamPlayerCard;