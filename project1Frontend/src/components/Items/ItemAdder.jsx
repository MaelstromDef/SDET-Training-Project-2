import { useState } from "react";
import ItemForm from "./ItemForm";
import '../components.css'

export default function ItemAdder(){
    const [showForm, setShowForm] = useState(false);

    return <div className="Adder">
        {showForm ?
            <>
                <ItemForm />
                <button onClick={() =>{setShowForm(false)}}>Cancel.</button>
            </> :
            <button onClick={() =>{setShowForm(true)}}>Add item.</button>
            
        }
    </div>
}