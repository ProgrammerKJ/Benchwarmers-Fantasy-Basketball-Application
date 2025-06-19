interface ErrorBlockProps{
    errorList: string[]
}



function ErrorBlock(props: ErrorBlockProps){
    const {errorList} = props;


    return(
        <>
            {errorList.length > 0 && (
                <div className="bg-red-100 text-grey-700 font-bold shadow-md rounded px-8 pt-6 pb-8 mb-4">
                    <p>The following errors occurred:</p>
                    <ul>
                        {errorList?.map((error, i) => (
                                <li key={i}>
                                    {`${i + 1}. ${error}`}
                                </li>
                            )
                        )}

                    </ul>
                </div>

            )}
        </>
    );
}

export default ErrorBlock;