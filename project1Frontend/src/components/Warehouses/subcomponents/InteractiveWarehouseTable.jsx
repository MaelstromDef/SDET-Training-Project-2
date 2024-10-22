/**
 * By: Aaron Huggins
 * 
 * Description:
 *      Creates a table of all warehouses owned by the logged-in account.
 */

import { useEffect, useContext, useState } from "react";
import { baseUrl } from "../../../App";
import { UserContext } from "../../../App";

import Warehouse from "./Warehouse/Warehouse";
import axios from "axios";
import { WarehousesContext } from "../Warehouses";
import WarehouseFilterer from "./WarehouseFilterer";

export default function WarehouseTable(){
    const { user, setUser } = useContext(UserContext);
    const {warehouses, setWarehouses} = useContext(WarehousesContext);

    // Loads the warehouse table with data
    const handleResponseSuccess = (res) =>{
        let updatedUser = user;
        updatedUser.warehouses = res.data;
        setUser(updatedUser);

        setWarehouses(res.data)
    }

    // Shows that an error occurred.
    const handleResponseError = (error) =>{
        if(error.response.status === 302){
            handleResponseSuccess(error.response);
            return;
        }

        console.log("ERROR :(\n" + JSON.stringify(error));
    }

    // Sends a request for a list of the warehouses.
    useEffect(() =>{
        // Retrieve warehouses from backend
        let getUrl = baseUrl + '/' + user.adminInfo.id;
        
        axios.get(getUrl)
        .then(handleResponseSuccess)
        .catch(handleResponseError);
    }, [])

    return <>
        <WarehouseFilterer />
        <table>
            {/* Header */}
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


                    // Warehouses
                    warehouses !== undefined && warehouses.map((warehouse, index) =>{
                        return <Warehouse index={index} warehouse={warehouse}/>
                    })
                }
            </tbody>
        </table>
    </>
}