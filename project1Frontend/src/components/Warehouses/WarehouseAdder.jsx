import { useState } from 'react'
import WarehouseForm from './WarehouseForm';

// Provides a table row for adding warehouses
export default function WarehouseAdder(){
    const [showForm, setShowForm] = useState(false);

    return <div className='Adder'>
        {showForm ? 
            <>
                <WarehouseForm />
                <button id='btnCancelAddWarehouse' onClick={() =>{setShowForm(false);}}>Cancel.</button>
            </> :
            <button id='btnOpenAddWarehouse' onClick={() => {setShowForm(true);}}>Add warehouse.</button>
        }
    </div>
}