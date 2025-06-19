import {Form} from "react-router-dom";

interface FormInputProps{
    id: string,
    type: string,
    value?: string,
    onChange?: React.ChangeEventHandler,
    children?: React.ReactNode
}


function FormInput(props: FormInputProps){

    const {id, type ,value, onChange} = props;

    function labelize(name: string){
        name = name[0].toUpperCase() + name.slice(1);
        name =  name.replace(/([A-Z])/g, ' $1').trim();
        return name;
    }


    return(
        <div className={"mb-6"}>
            <label className={"block text-grey-700 text-mb font-bold mb-2"} htmlFor={id}>
                {props.children || labelize(id)}
            </label>
            <input type={type} id={id} className={"shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline"} name={id} value={value} onChange={onChange}/>
        </div>
    );
}

export default FormInput;