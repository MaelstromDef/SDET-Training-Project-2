import { createContext, useContext, useEffect, useState } from "react"
import { UserContext } from "../App"
import Warehouse from "../components/Warehouses/Warehouse";
import axios from "axios";
import WarehouseAdder from "../components/Warehouses/WarehouseAdder";

import { baseUrl } from "../App";
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
    const {user, setUser} = useContext(UserContext);
    const [warehouses, setWarehouses] = useState([]);

    const navigate = useNavigate();

    const handleResponseSuccess = (res) =>{
        setWarehouses([...res.data]);
    }

    const handleResponseError = (error) =>{
        if(error.response.status === 302){
            handleResponseSuccess(error.response);
            return;
        }

        console.log("ERROR :(\n" + JSON.stringify(error));
    }

    useEffect(() =>{
        if(user.authorization === null || user.authorization === "") {
            navigate('/')
            return;
        }

        // Retrieve warehouses from backend
        let getUrl = baseUrl + '/' + user.adminInfo.id;
        
        axios.get(getUrl)
        .then(handleResponseSuccess)
        .catch(handleResponseError);
    }, [])

    return <WarehousesContext.Provider value={{warehouses, setWarehouses}}>
        <h1>Warehouses</h1>
        <WarehouseAdder />
        <table>
            <thead>
                <tr>
                    <th>Name</th>
                    <th>Location</th>
                    <th>Size</th>
                    <th>Actions</th>
                </tr>
            </thead>
            <tbody>
                {
                    warehouses.map((warehouse, index) =>{
                        return <Warehouse index={index} warehouse={warehouse}/>
                    })
                }
            </tbody>
        </table>
    </WarehousesContext.Provider>
}