interface AppFormProps{
    onSubmit?: React.FormEventHandler,
    children?: React.ReactNode;
}


function AppForm(props : AppFormProps){
    const {onSubmit} = props;


    return(
        <form className={"bg-white shadow-md rounded px-8 pt-6 pb-8 mb-4"} onSubmit={onSubmit}>
            {props.children}
        </form>
    );
}

export default AppForm;