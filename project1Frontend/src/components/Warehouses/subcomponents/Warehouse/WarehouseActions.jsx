import styles from '../../Warehouses.module.css'

import { useContext } from 'react';
import axios from 'axios';
import { WarehousesContext } from '../../Warehouses';

import { baseUrl, UserContext } from '../../../../App';

export default function WarehouseActions(props){
    const {user, setUser} = useContext(UserContext);
    const {warehouses, setWarehouses} = useContext(WarehousesContext);
    let warehouse = props.warehouse;

    const handleResponseSuccess = (res) =>{
        let newWarehouses = warehouses.filter(w => w.id !== warehouse.id);
        let newUser = user;
        newUser.warehouses = newWarehouses;

        if(newWarehouses.length !== 0) {
            setWarehouses(newWarehouses);
            setUser(newUser)
        }else {
            setWarehouses([]);

            newUser.warehouses = [];
            setUser(newUser);
        }
    }

    const handleResponseError = (error) =>{
        if(Object.keys(error).length === 0) {
            handleResponseSuccess();
            return;
        }
        console.log(JSON.stringify(error));
    }    

    // Navigates to the warehouse's items page
    const btnSelect_Handler = (event) =>{
        // Set warehouse appropriately
        let newUser = user;
        newUser.currentWarehouse = warehouse;
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

    return <div className={styles.HorizontalFlexBox + ' ' + styles.FlexGap}>
        <button id={'warehouse_modify_' + warehouse.name}
        className={styles.TableButton}
        onClick={btnSelect_Handler}>
            Modify Warehouse
        </button>
        <button id={'warehouse_delete_' + warehouse.name} 
        className={styles.TableButton}
        onClick={btnDelete_Handler}>Remove Warehouse</button>
    </div>
}