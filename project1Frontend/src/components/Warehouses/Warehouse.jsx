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

import './Warehouses.css'

export default function Warehouse(props){
    const {user, setUser} = useContext(UserContext);

    const navigate = useNavigate();

    let warehouse = props.warehouse;

    // RESPONSE HANDLERS

    const handleResponseSuccess = (res) =>{
        const {warehouses, setWarehouses} = useContext(WarehousesContext);
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
        if(props.readOnly) return;

        const delUrl = baseUrl + '/' + user.adminInfo.id + '/' + warehouse.id;
        axios.delete(delUrl)
        .then(handleResponseSuccess)
        .catch(handleResponseError);
    }

    return <tr key={warehouse.name}>
        <td id={'warehouse_name_' + warehouse.name}>{warehouse.name}</td>
        <td id={'warehouse_location_' + warehouse.name}>{warehouse.location}</td>
        <td id={'warehouse_size_' + warehouse.name}>{warehouse.size}</td>
        <td>
            <button id={'warehouse_manage_' + warehouse.name} onClick={btnSelect_Handler}>Manage Items</button>
            {
                !props.readOnly && <button id={'warehouse_delete_' + warehouse.name} onClick={btnDelete_Handler}>Delete</button>
            }
        </td>
    </tr>
}