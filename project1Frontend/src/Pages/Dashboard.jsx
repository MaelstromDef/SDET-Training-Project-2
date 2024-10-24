import { useEffect, useContext } from "react";
import { Navigate, useNavigate } from "react-router-dom";
import { UserContext } from "../App";
import WarehouseTable from "../components/Warehouses/subcomponents/ReadOnlyWarehouseTable";

import styles from './Dashboard.module.css'

export default function Home(){
    const navigate = useNavigate();
    const {user} = useContext(UserContext)
    useEffect(() =>{
        if(user.authorization === null || user.authorization === "") navigate('/')
    }, [])

    // Navigates to warehouses
    const btnWarehouse_Handler = () =>{
        navigate('/warehouses');
    }

    // Navigates to account
    const btnAccount_Handler = () =>{
        navigate('/account');
    }

    return <>
        <h1>Dashboard</h1>
        <div className={styles.HorizontalFlexBox + ' ' + styles.FlexGap}>
            {/* Actions */}
            <div className={styles.ActionsBox}>
                <h2>Quick Access</h2>
                <button id='btnAccount' className={styles.Button} onClick={btnAccount_Handler}>Modify Account</button>
                <button id='btnWarehouse' className={styles.Button} onClick={btnWarehouse_Handler}>Manage Warehouses</button>
            </div>

            {/* Warehouses */}
            <div className={styles.WarehousesBox}>
                <h2>Warehouses Overview</h2>
                <WarehouseTable />
            </div>
        </div>
    </>
}