import styles from './Account.module.css'

import { useState, useContext } from "react";
import { UserContext } from '../../App'

export default function UpdateCompanyName(){
    const {user, setUser} = useContext(UserContext);
    const [allowCompanyNameChange, setAllowCompanyNameChange] = useState(false);

    // Changes 'allowCompanyNameChange' based on checkbox.
    const chkChangeCompany_Handler = (cb) => {
        setAllowCompanyNameChange((cb.target.checked));
    }

    // Toggles disabled state of company name input field
    const toggleModifiable = (e) => {
        e.preventDefault();
        setAllowCompanyNameChange(!allowCompanyNameChange);
    }

    return <form id='formUpdateCompanyName' className={styles.VerticalForm}>
        <label>Company Name</label>

        <div className={styles.HorizontalFlex}>
            {/* Company name input. */}
            <input  id='inCompanyName'
                className={styles.Input}
                type="text" 
                name="companyName" 
                defaultValue={user.adminInfo.companyName} 
                placeholder='Company Name'
                disabled={!allowCompanyNameChange}/>

            {/* Allow company name change. */}
            {
                !allowCompanyNameChange ?
                <button
                    className={styles.Button}
                    id='btnAllowCompanyNameChange'
                    onClick={toggleModifiable}>
                        Update Company Name
                </button> :
                <>
                    <button
                        className={styles.Button}
                        id='btnAllowCompanyNameChange'
                        onClick={toggleModifiable}>
                            Cancel Update
                    </button>
                    <button
                        className={styles.Button}
                        id='btnAllowCompanyNameChange'
                        onClick={toggleModifiable}>
                            Confirm Update
                    </button>
                </>
            }
        </div>
    </form>
}