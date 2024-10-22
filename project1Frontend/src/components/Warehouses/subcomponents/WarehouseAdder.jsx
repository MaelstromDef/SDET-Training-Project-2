import { useState } from 'react'
import WarehouseForm from './WarehouseForm';

// Provides a table row for adding warehouses
export default function WarehouseAdder(){
    const [showForm, setShowForm] = useState(false);

    return <div className='Adder'>
        {showForm ? 
            <>
                <WarehouseForm setShowForm={setShowForm} />
            </> :
            <button id='btnOpenAddWarehouse' onClick={() => {setShowForm(true);}}>Add Warehouse</button>
        }
    </div>
}