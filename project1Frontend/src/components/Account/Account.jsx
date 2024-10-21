import styles from './Account.module.css'
import { baseUrl, logout, UserContext } from '../../App';
import { useContext, useEffect, useState } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';

import UpdateCompanyName from './UpdateCompanyName';
import UpdatePassword from './UpdatePassword';
import DeleteAccount from './DeleteAccount';

// Account shows current account information (company name) and allows information to be changed (company name and password)
export default function Account(){
    const {user, setUser} = useContext(UserContext);

    const [feedback, setFeedback] = useState("");
    

    const navigate = useNavigate();
    useEffect(() =>{
        if(user.authorization === null || user.authorization === "") navigate('/')
    }, [])

    // FORM SUBMISSION

    // Records new user info.
    const handleResponseSuccess = (res) =>{
        // Update context info
        let adminInfo = user.adminInfo;
        adminInfo.id = res.data.id;
        adminInfo.companyName = res.data.adminInfo;

        let newUser = user;
        newUser.adminInfo = adminInfo;

        // Show feedback
        setUser(newUser);
        setFeedback("Account updated.");
    }

    // Outputs error messages to user.
    const handleResponseError = (error) =>{
        if(Object.keys(error).length === 0){
            navigate('/')
            return;
        }

        if(error.response.status === 401) {
            navigate('/')
            return;
        }
        setFeedback("Something went wrong, please try again.");
    }

    // Requests a modification to the user's stored information
    const handleSubmit = (event) =>{
        event.preventDefault();
        setFeedback("");

        // Set up details
        const companyName = event.target.companyName.value.trim();
        const password = event.target.password.value.trim();

        if(companyName === '' || password === ''){
            setFeedback('Company name and password must be filled in.');
            return;
        }

        const newAdminInfo = {
            companyName: companyName,
            password: password
        }

        axios.put(baseUrl, newAdminInfo)
        .then(handleResponseSuccess)
        .catch(handleResponseError);
    }

    return (<>
        <h1>Account</h1>
        <UpdateCompanyName />
        <UpdatePassword />
        <DeleteAccount />
        
        <p>{feedback}</p>
    </>);
}