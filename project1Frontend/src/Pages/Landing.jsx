import { useNavigate } from "react-router-dom"

export default function Landing(){
    const navigate = useNavigate();

    const btnLogin_Handler = () =>{
        navigate('/login');
    }

    const btnSignup_Handler = () =>{
        navigate('/signup');
    }

    return <>
        <h1>Warehouse Manager</h1>
        <h3>Property of Aaron Huggins Version 1.3.1</h3>
        <button id='btnLogin' onClick={btnLogin_Handler}>Login</button>
        <button id='btnSignUp' onClick={btnSignup_Handler}>Sign up</button>
    </>
}