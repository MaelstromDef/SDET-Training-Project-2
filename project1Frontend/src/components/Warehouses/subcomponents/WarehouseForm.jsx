import { useContext, useState } from "react";
import { baseUrl, UserContext } from "../../../App";
import axios from "axios";
import { WarehousesContext } from "../Warehouses";

import styles from '../Warehouses.module.css'

// Form to add a new warehouse.
export default function WarehouseForm(props){
    const {user, setUser} = useContext(UserContext)
    const {warehouses, setWarehouses} = useContext(WarehousesContext);

    const [feedback, setFeedback] = useState("");

    const handleResponseSuccess = (res) =>{
        let updatedUser = {...user};
        updatedUser.warehouses = [...warehouses, res.data];
        setUser(updatedUser);

        setWarehouses([
            ...warehouses,
            res.data
        ])
    }

    const handleResponseError = (error) =>{
        setFeedback("Something went wrong, please try again.");
    }

    const handleSubmit = (event) =>{
        event.preventDefault();

        // Gather fields
        const warehouseName = event.target.name.value.trim();
        const location = event.target.location.value.trim();
        const size = Math.round(event.target.size.value);

        // Data validation
        if(warehouseName === "" || location === ""){
            setFeedback("Fields cannot be empty.");
            return;
        }

        if(size < 1) {
            setFeedback("Size must be greater than 0.");
            return;
        }

        // Request construction.
        const postUrl = baseUrl + '/' + user.adminInfo.id;
        const warehouse = {
            name: warehouseName,
            location: location,
            size: size,
            administrator: user.adminInfo
        }

        axios.post(postUrl, warehouse)
        .then(handleResponseSuccess)
        .catch(handleResponseError);
    }

    return <div>
        <h2>Add Warehouse</h2>
        <form className={styles.VerticalForm} onSubmit={handleSubmit}>
            {/* Input fields */}
            <input id='inWarehouseName' type='text' name='name' placeholder='Name' />
            <input id='inWarehouseLocation' type='text' name='location' placeholder='Location' />
            <input id='inWarehouseSize' type='number' name='size' placeholder='Size' />

            <div className={styles.HorizontalFlexBox + ' ' + styles.FlexGap}>
                <input id='btnCancelAddWarehouse' type='button' onClick={() => props.setShowForm(false)} value='Cancel' />
                <input id='btnAddWarehouse' type='submit' value='Create'/>
            </div>
            <label>{feedback}</label>
        </form>
    </div>
}