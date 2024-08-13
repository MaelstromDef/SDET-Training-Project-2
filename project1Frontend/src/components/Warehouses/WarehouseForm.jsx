import { useContext, useState } from "react";
import { baseUrl, UserContext } from "../../App";
import axios from "axios";
import { WarehousesContext } from "../../Pages/Warehouses";

// Form to add a new warehouse.
export default function WarehouseForm(props){
    const {user, setUser} = useContext(UserContext)
    const {warehouses, setWarehouses} = useContext(WarehousesContext);

    const [feedback, setFeedback] = useState("");

    const handleResponseSuccess = (res) =>{
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

    return <form className="VerticalForm" onSubmit={handleSubmit}>
        <label>Name</label>
        <input type='text' name='name'/>

        <label>Location</label>
        <input type='text' name='location'/>

        <label>Size (Decimals are rounded.)</label>
        <input type='number' name='size'/>

        <input type='submit' value='Add warehouse.'/>
        <label>{feedback}</label>
    </form>
}