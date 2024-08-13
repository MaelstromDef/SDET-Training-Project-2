/* Warehouse schema:
{
    id: INTEGER
    name: STRING
    location: STRING
    size: INTEGER
    administrator: ADMINISTRATOR
    storedItems: List<STORED_ITEM>
}
*/

import { useContext } from "react";
import { baseUrl, UserContext } from "../../App";
import { useNavigate } from "react-router-dom";
import { WarehousesContext } from "../../Pages/Warehouses";
import axios from "axios";

export default function Warehouse(props){
    const {user, setUser} = useContext(UserContext);
    const {warehouses, setWarehouses} = useContext(WarehousesContext);

    const navigate = useNavigate();

    let warehouse = props.warehouse;

    // RESPONSE HANDLERS

    const handleResponseSuccess = (res) =>{
        const newWarehouses = warehouses.filter(w => w.id !== warehouse.id);

        if(newWarehouses.length !== 0) setWarehouses(newWarehouses);
        else setWarehouses([]);
    }

    const handleResponseError = (error) =>{
        if(Object.keys(error).length === 0) {
            handleResponseSuccess();
            return;
        }
        console.log(JSON.stringify(error));
    }

    // BUTTON HANDLERS

    // Navigates to the warehouse's items page
    const btnSelect_Handler = (event) =>{
        // Set warehouse appropriately
        let newUser = user;
        newUser.warehouse = warehouse;
        setUser(newUser);

        // Navigate to warehouse's items page
        navigate('/items');
    }

    // Deletes the warehouse
    const btnDelete_Handler = (event) =>{
        const delUrl = baseUrl + '/' + user.adminInfo.id + '/' + warehouse.id;
        axios.delete(delUrl)
        .then(handleResponseSuccess)
        .catch(handleResponseError);
    }

    return <tr key={props.index}>
        <td>{warehouse.name}</td>
        <td>{warehouse.location}</td>
        <td>{warehouse.size}</td>
        <td>
            <button onClick={btnSelect_Handler}>Manage</button>
            <button onClick={btnDelete_Handler}>Delete</button>
        </td>
    </tr>
}