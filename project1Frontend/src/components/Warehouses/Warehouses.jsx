import { createContext, useContext, useEffect, useState } from "react"
import { UserContext } from "../../App"
import WarehouseAdder from "./subcomponents/WarehouseAdder";
import WarehouseTable from "./subcomponents/InteractiveWarehouseTable";

import { useNavigate } from "react-router-dom";

import styles from './Warehouses.module.css'

export const WarehousesContext = createContext();

// Shows a list of all of the warehouses and allows you to navigate into one of them, and also provides the ability to add, remove,
// and change warehouses.
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
export default function Warehouses(){
    const { user } = useContext(UserContext);
    const [warehouses, setWarehouses] = useState([]);

    const navigate = useNavigate();

    useEffect(() =>{
        if(user.authorization === null || user.authorization === "") {
            navigate('/')
            return;
        }
    }, [])

    return <WarehousesContext.Provider value={{warehouses, setWarehouses}}>
        <h1>Warehouses</h1>

        <div className={styles.HorizontalFlexBox + ' ' + styles.FlexGap}>
            {/* Left side */}
            <div className={styles.ActionsBox}>
                <WarehouseAdder />
            </div>

            {/* Right side */}
            <div className={styles.WarehousesBox}>
                <WarehouseTable/>
            </div>
        </div>
    </WarehousesContext.Provider>
}