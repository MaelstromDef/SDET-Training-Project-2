import { useEffect, useContext } from "react";
import { Navigate, useNavigate } from "react-router-dom";
import { UserContext } from "../App";
import WarehouseTable from "../components/Warehouses/ReadOnlyWarehouseTable";

import styles from './Dashboard.module.css'

export default function Home(){
    const navigate = useNavigate();
    const {user} = useContext(UserContext)
    useEffect(() =>{
        if(user.authorization === null || user.authorization === "") navigate('/')
    }, [])

    const btnWarehouse_Handler = () =>{
        navigate('/warehouses');
    }

    const btnAccount_Handler = () =>{
        navigate('/account');
    }

    return <>
        <h1>Dashboard</h1>
        <div className="HorizontalFlexBox">
            {/* Actions */}
            <div className="ActionsBox">
                <h2>Navigation</h2>
                <button id='btnWarehouse' className={styles.Button} onClick={btnWarehouse_Handler}>Manage Warehouses</button>
                <button id='btnAccount' className={styles.Button} onClick={btnAccount_Handler}>Modify Account</button>
            </div>

            {/* Warehouses */}
            <div className="WarehousesBox">
                <h2>Warehouses Overview</h2>
                <WarehouseTable />
            </div>
        </div>
    </>
}