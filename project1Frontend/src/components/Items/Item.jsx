import { useContext, useState, createRef } from "react"
import { logout, UserContext } from "../../App"
import { ItemsContext } from "../../Pages/Items";
import axios from "axios";

import { baseUrl } from "../../App";

/*  Item Schema
{
    id: INTEGER
    name: STRING
    quantity: INTEGER
}
*/
export default function Item(props){
    const {user, setUser} = useContext(UserContext);
    const {storedItems, setStoredItems} = useContext(ItemsContext);

    const [modify, setModify] = useState(false);
    const inQuantity = createRef()

    let storedItem = props.storedItem;

    // RESPONSE HANDLERS

    // Updates visual table
    const handleUpdateSuccess = (res) =>{
        const newItems = storedItems.map((s) =>{
            if(s.item.id === storedItem.item.id){
                return storedItem;
            }

            return s;
        })

        setStoredItems(newItems);
        setModify(false);
    }

    // Handles update errors
    const handleUpdateError = (error) =>{
        if(error.response.status === 401){
            logout();
            return;
        }else if(error.response.status === 404){
            console.log("ERROR: item " + storedItem.item.name + " was not found in this warehouse.");
        }

        console.log("ERROR\n" + JSON.stringify(error))
    }

    // Updates visual table
    const handleDeleteSuccess = (res) => {
        const newItems = storedItems.filter(i => i.item.id !== storedItem.item.id);

        if(newItems.length !== 0) setStoredItems(newItems);
        else setStoredItems([]);
    }

    // Handles delete errors
    const handleDeleteError = (error) =>{
        if(Object.keys(error).length === 0){
            handleDeleteSuccess();
            return;
        }else if(error.response.status === 401){
            logout();
            return;
        }

        console.log(JSON.stringify(error));
    }

    // BUTTON HANDLERS

    // Allows modification of item quantity
    const btnUpdate_Handler = (event) =>{
        const putUrl = baseUrl + '/' + user.adminInfo.id + '/' + user.warehouse.id + '/items';
        let newItem = storedItem;
        newItem.quantity = inQuantity.current.value;

        axios.put(putUrl, newItem)
        .then(handleUpdateSuccess)
        .catch(handleUpdateError)
    }

    // Deletes the item
    const btnDelete_Handler = (event) =>{
        const delUrl = baseUrl + '/' + user.adminInfo.id + '/' + user.warehouse.id + '/items/' + storedItem.item.id;
        axios.delete(delUrl)
        .then(handleDeleteSuccess)
        .catch(handleDeleteError);
    }

    return <tr key={props.index}>
        <td>{storedItem.item.name}</td>
        <td>
            {
                modify ? 
                <div>
                    <input type="number" ref={inQuantity} defaultValue={storedItem.quantity}/>
                </div> :
                <div>{storedItem.quantity}</div>
            }
        </td>
        <td>
            {
                modify ?
                <>
                    <button onClick={btnUpdate_Handler}>Update</button> 
                    <button onClick={() => {setModify(false);}}>Cancel</button>
                </>:
                <button onClick={() => {setModify(true);}}>Manage</button>
            }
            <button onClick={btnDelete_Handler}>Delete</button>
        </td>
    </tr>
}