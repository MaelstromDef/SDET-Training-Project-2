import { createContext, useContext, useEffect, useState } from "react"
import { UserContext } from "../App"
import WarehouseAdder from "../components/Warehouses/WarehouseAdder";
import WarehouseTable from "../components/Warehouses/InteractiveWarehouseTable";

import { useNavigate } from "react-router-dom";

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
        <WarehouseAdder />
        <WarehouseTable/>
    </WarehousesContext.Provider>
}