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
import { baseUrl, UserContext } from "../../../../App";
import { useNavigate } from "react-router-dom";
import { WarehousesContext } from "../../Warehouses";
import axios from "axios";

import styles from '../../Warehouses.module.css'
import WarehouseActions from "./WarehouseActions";

export default function Warehouse(props){
    const {user, setUser} = useContext(UserContext);

    const navigate = useNavigate();

    let warehouse = props.warehouse;

    // BUTTON HANDLERS

    // Navigates to the warehouse's items page
    const btnSelect_Handler = (event) =>{
        // Set warehouse appropriately
        let newUser = user;
        newUser.currentWarehouse = warehouse;
        setUser(newUser);

        // Navigate to warehouse's items page
        navigate('/items');
    }

    return <tr key={warehouse.name}>
        <td id={'warehouse_name_' + warehouse.name}>{warehouse.name}</td>
        <td id={'warehouse_location_' + warehouse.name}>{warehouse.location}</td>
        <td id={'warehouse_size_' + warehouse.name}>{warehouse.size}</td>
        <td className={styles.TableData + ' ' + styles.VerticalFlexBox + ' ' + styles.FlexGap}>
            <button id={'warehouse_manage_' + warehouse.name} className={styles.TableButton} onClick={btnSelect_Handler}>Manage Items</button>
            {
                !props.readOnly && 
                <WarehouseActions warehouse={warehouse} />
            }
        </td>
    </tr>
}