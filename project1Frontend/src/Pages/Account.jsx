import './page.css'
import { baseUrl, logout, UserContext } from '../App';
import { useContext, useEffect, useState } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';

// Account shows current account information (company name) and allows information to be changed (company name and password)
export default function Account(){
    const {user, setUser} = useContext(UserContext);

    const [feedback, setFeedback] = useState("");
    const [allowCompanyNameChange, setAllowCompanyNameChange] = useState(false);
    const [showDel, setShowDel] = useState(false);

    const navigate = useNavigate();
    useEffect(() =>{
        if(user.authorization === null || user.authorization === "") navigate('/')
    }, [])

    // FORM MODIFICATION

    // Changes 'allowCompanyNameChange' based on checkbox.
    const chkChangeCompany_Handler = (cb) => {
        setAllowCompanyNameChange((cb.target.checked));
    }

    // Attempts an account delete.
    const btnDelete_Handler = () =>{
        setShowDel(false);

        axios.delete(baseUrl)
        .then(() =>{
            navigate('/')
        })
        .catch(handleResponseError);
    }

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
        <form className="VerticalForm" onSubmit={handleSubmit}>
            <label>Company Name</label>
            <div>
                <input type="text" 
                    name="companyName" 
                    defaultValue={user.adminInfo.companyName} 
                    readOnly={!allowCompanyNameChange}/>
                <div>
                <input type="checkbox" 
                    name="chkChangeCompanyName" 
                    defaultValue={allowCompanyNameChange} 
                    onClick={chkChangeCompany_Handler} />
                <span><i>Check the box to change the company's name.</i></span>
                </div>
            </div>

            <label>Password</label>
            <input type="password" name="password"/>

            <input type='submit' value='Update Information'/>
            {
                showDel ?
                    <>
                        <p>This action will result in removing all data regarding this account. Are you sure you want to delete this account? (You will be logged out as soon as the account is deleted)</p>
                        <div>
                            <input type='button' value='Yes' onClick={btnDelete_Handler} />
                            <input type='button' value='No' onClick={() => {setShowDel(false)}} />
                        </div>
                    </> :
                <input type='button' onClick={() => {setShowDel(true);}} value='Delete Account'/>
            }
        </form>
        <p>{feedback}</p>
    </>);
}