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
        <button onClick={btnLogin_Handler}>Login</button>
        <button onClick={btnSignup_Handler}>Sign up</button>
    </>
}