import { useNavigate } from "react-router-dom";
import styles from "./LandingHero.module.css";

export default function HeroButtons(){
    const navigate = useNavigate();

    const btnLogin_Handler = () =>{
        navigate('/login');
    }

    const btnSignup_Handler = () =>{
        navigate('/signup');
    }

    return <div className={styles.buttonbox}>
        <button 
            id='btnLogin' 
            onClick={btnLogin_Handler}
            className={styles.button}>Login</button>
        <button 
            id='btnSignup' 
            onClick={btnSignup_Handler}
            className={styles.button}>Signup</button>
    </div>
}