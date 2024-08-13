import { createContext, createRef, useContext, useEffect, useState } from "react";
import Item from "../components/Items/Item";
import { UserContext } from "../App";
import axios from "axios";

import { baseUrl } from "../App";
import ItemAdder from "../components/Items/ItemAdder";
import { useNavigate } from "react-router-dom";

export const ItemsContext = createContext();

export default function Items(){
    const {user, setUser} = useContext(UserContext);
    const [storedItems, setStoredItems] = useState([]);

    const navigate = useNavigate();

    // Handlers
    const handleResponseSuccess = (res) =>{
        setStoredItems([...res.data]);
    }

    const handleResponseError = (error) => {
        console.log("ERROR\n" + JSON.stringify(error));
        return;
        
        // if(error.response.status === 302){  // Found code, not an error.
        //     handleResponseSuccess(error.response);
        //     return;
        // }
    }

    // Grab stored items on load
    useEffect(()=>{
        if(user.authorization === null || user.authorization === "") {
            navigate('/')
            return;
        }

        const getUrl = baseUrl + '/' + user.adminInfo.id + '/' + user.warehouse.id + '/items';

        axios.get(getUrl)
        .then(handleResponseSuccess)
        .catch(handleResponseError);
    }, [])

    return <ItemsContext.Provider value={{storedItems, setStoredItems}}>
        <h1>{user.warehouse.name}</h1>
        <ItemAdder />
        <table>
            <thead>
                <tr>
                    <th>Name</th>
                    <th>Quantity</th>
                    <th>Actions</th>
                </tr>
            </thead>
            <tbody>
                {
                    storedItems.map((storedItem, index) =>{
                        return <Item index={index} storedItem={storedItem}/>
                    })
                }
            </tbody>
        </table>
    </ItemsContext.Provider>
}