import axios from "axios";
import { useState } from "react";
import { useNavigate } from "react-router-dom";
import { baseUrl } from "../App";

import styles from './Signup.module.css'

// Allows a user to enter company name and password in order to create an administrator acconut.
export default function Signup(){
    const url = baseUrl + "/signup";

    const [err, setErr] = useState("");

    const navigate = useNavigate();

    const handleResponseSuccess = (res) => {
        setErr('Success! "' + res.data.companyName + '" was successfully registered. Please log in. (You will be redirected in 3 seconds.)')

        setTimeout(() =>{
            navigate('/login');
        }, 3000)
    }

    const handleResponseError = (error) => {
        if(error.response.status == 409) setErr("That company is already signed up.");
    };

    const handleSubmit = (event) =>{
        event.preventDefault();
        setErr("");

        // ATTEMPT SIGN UP

        // Gather form details
        const companyName = event.target.companyName.value.trim();
        const password = event.target.password.value.trim();
        const confirmPass = event.target.confirmPass.value.trim();

        // Validate information
        if(password !== confirmPass){
            setErr("Passwords don't match.");
            return;
        }
        
        if(companyName === "" || password === ""){
            setErr("Please fill in all fields.");
            return;
        }

        // Form the request
        const administrator = {
            companyName: companyName,
            password: password
        }

        // Request a signup
        axios.post(url, administrator)
        .then(handleResponseSuccess)
        .catch(handleResponseError);
    }

    return <>
        <h1>Signup</h1>
        <form className={styles.VerticalForm} onSubmit={handleSubmit}>
            <label>Company Name</label>
            <input id='inCompanyName'
                type="text"
                name="companyName"/>

            <label>Password</label>
            <input id='inPassword'
                type="password"
                name="password"/>

            <label>Confirm Password</label>
            <input id='inConfirmPassword'
                type="password"
                name="confirmPass"/>

            <input className={styles.Button}
                id='btnSignUp'
                type="submit"
                value="Sign up"/>
        </form>
        <p>{err}</p>
    </>
}