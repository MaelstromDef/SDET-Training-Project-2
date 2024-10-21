import styles from './Account.module.css'

import { useState } from 'react';
import { baseUrl } from '../../App';

export default function DeleteAccount(){
    const [showDel, setShowDel] = useState(false);

    // Attempts an account delete.
    const btnDelete_Handler = () =>{
        setShowDel(false);

        axios.delete(baseUrl)
        .then(() =>{
            navigate('/')
        })
        .catch(handleResponseError);
    }

    return <>
        {
            showDel ?
                <div className={styles.DeleteSection}>
                    <p><b>WARNING!</b></p>
                    <p>This action will result in the deletion of all data associated with this account. 
                        Are you sure you want to continue with your account deletion?</p>
                    <div className={styles.HorizontalFlex}>
                        <button id='btnDeleteAccount' className={styles.Button} onClick={btnDelete_Handler}>Yes, delete this account.</button>
                        <button id='btnCancelDelete' className={styles.Button} onClick={() => {setShowDel(false)}}>Cancel</button>
                    </div>
                </div> :
            <button id='btnOpenDelete' className={styles.Button} onClick={() => {setShowDel(true);}}>Delete Account</button>
        }
    </>
}