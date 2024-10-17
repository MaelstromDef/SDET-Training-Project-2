import { useState } from "react";
import ItemForm from "./ItemForm";

export default function ItemAdder(){
    const [showForm, setShowForm] = useState(false);

    return <div className="Adder">
        {showForm ?
            <>
                <ItemForm />
                <button id='btnCancelAddItem' onClick={() =>{setShowForm(false)}}>Cancel.</button>
            </> :
            <button id='btnOpenAddItem' onClick={() =>{setShowForm(true)}}>Add item.</button>
            
        }
    </div>
}