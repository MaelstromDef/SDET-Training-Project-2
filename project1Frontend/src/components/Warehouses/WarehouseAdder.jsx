import { useState } from 'react'
import WarehouseForm from './WarehouseForm';
import '../components.css'

// Provides a table row for adding warehouses
export default function WarehouseAdder(){
    const [showForm, setShowForm] = useState(false);

    return <div className='Adder'>
        {showForm ? 
            <>
                <WarehouseForm />
                <button onClick={() =>{setShowForm(false);}}>Cancel.</button>
            </> :
            <button onClick={() => {setShowForm(true);}}>Add warehouse.</button>
        }
    </div>
}