import styles from './Account.module.css'

import { useState, useContext } from "react";
import { UserContext } from '../../App'

export default function UpdatePassword(){
    const {user, setUser} = useContext(UserContext);
    const [modifiable, setModifiable] = useState(false);

    // Toggles disabled state of company name input field
    const toggleModifiable = (e) => {
        e.preventDefault();
        setModifiable(!modifiable);
    }

    return <form id='formUpdatePassword' className={styles.VerticalForm}>
        <label>Password</label>

        <div className={styles.HorizontalFlex}>
            {/* Company name input. */}
            <input  id='inPassword'
                className={styles.Input}
                type="text" 
                name="password" 
                placeholder='Password'
                disabled={!modifiable}/>

            {/* Allow company name change. */}
            {
                !modifiable ?
                <button
                    className={styles.Button}
                    id='btnTogglePasswordModifiable'
                    onClick={toggleModifiable}>
                        Update Password
                </button> :
                <>
                    <button
                        className={styles.Button}
                        id='btnTogglePasswordModifiable'
                        onClick={toggleModifiable}>
                            Cancel Update
                    </button>
                    <button
                        className={styles.Button}
                        id='btnUpdatePassword'
                        onClick={toggleModifiable}>
                            Confirm Update
                    </button>
                </>
            }
        </div>
    </form>
}