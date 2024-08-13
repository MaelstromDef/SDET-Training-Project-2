import { useContext, useState } from "react";
import { baseUrl, UserContext } from "../../App";
import { ItemsContext } from "../../Pages/Items";
import axios from "axios";

export default function ItemForm(){
    const {user, setUser} = useContext(UserContext);
    const {storedItems, setStoredItems} = useContext(ItemsContext);

    const [feedback, setFeedback] = useState("");

    const handleResponseSuccess = (res) =>{
        setStoredItems([
            ...storedItems,
            res.data
        ])
    }

    const handleResponseError = (error) =>{
        if(error.response.status === 409){
            setFeedback("That item already exists, please modify the quantity below.");
            return;
        }

        setFeedback("Something went wrong, please try again.");
    }

    const handleSubmit = (event) =>{
        event.preventDefault();

        // Gather fields
        const itemName = event.target.name.value.trim();
        const itemQuantity = Math.round(event.target.quantity.value);

        // Data validation
        if(itemName === ""){
            setFeedback("Name cannot be empty.")
            return;
        }

        if(itemQuantity < 1) {
            setFeedback("Quantity must be greater than 0.");
            return;
        }

        // Request Construction
        const postUrl = baseUrl + '/' + user.adminInfo.id + '/' + user.warehouse.id + '/items';
        const storedItem = {
            item: {
                name: itemName
            },
            quantity: itemQuantity
        }

        console.log(JSON.stringify(storedItem))

        axios.post(postUrl, storedItem)
        .then(handleResponseSuccess)
        .catch(handleResponseError);
    }

    return <form className="VerticalForm" onSubmit={handleSubmit}>
        <label>Name</label>
        <input type='text' name='name'/>

        <label>Quantity</label>
        <input type='number' name='quantity'/>

        <input type='submit' value='Add item.'/>
        <label>{feedback}</label>
    </form>
}