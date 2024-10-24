import { useContext, useState } from 'react';
import styles from './Login.module.css'
import axios from 'axios';
import { baseUrl, UserContext } from '../App';
import { useNavigate } from 'react-router-dom';

// Allows a user to input their username and password to log in.
export default function Login(){
    const url = baseUrl + "/login";

    const [err, setErr] = useState("");
    const {user, setUser} = useContext(UserContext);
    const navigate = useNavigate();

    const handleResponseSuccess = (res) =>{
        // Store user information
        const jwtInfo = JSON.parse(atob(res.data.split('.')[1]));
        let adminInfo = {
            id: jwtInfo.id,
            companyName: jwtInfo.companyName
        }
        
        let newUser = {
            adminInfo: adminInfo,
            authorization: res.data
        };
        setUser(newUser);

        // Set up future requests
        axios.defaults.headers.common['authorization'] = res.data;

        navigate("/home");
    }

    const handleResponseError = (error) =>{
        if(!error.response) {
            console.log(error);
            setErr("Something went wrong, please try again.");
            return;
        }
        if(error.response.status === 401) setErr("Invalid credentials.");
    }

    const handleSubmit = (event) =>{
        event.preventDefault();
        setErr("");

        // ATTEMPT LOGIN

        // Gather details
        const companyName = event.target.companyName.value.trim();
        const password = event.target.password.value.trim();

        // Validate information
        if(companyName === "" || password === ""){
            setErr("Please fill in all fields.");
            return;
        }

        // Form request
        const administrator = {
            companyName: companyName,
            password: password
        };
        axios.post(url, administrator)
        .then(handleResponseSuccess)
        .catch(handleResponseError);
    }

    return <>
        <h1>Login</h1>
        <form className={styles.VerticalForm} onSubmit={handleSubmit}>
            <label>Company Name</label>
            <input id='inCompanyName'
                type="text"
                name="companyName"/>

            <label>Password</label>
            <input id='inPassword'
                type="password"
                name="password"/>

            <input className={styles.Button}
                id='btnLogin'
                type="submit"
                value="Log in"/>
        </form>
        <p id='txtLoginError'>{err}</p>
    </>
}