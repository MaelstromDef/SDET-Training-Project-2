import styles from '../Warehouses.module.css'

import { WarehousesContext } from '../Warehouses'
import { UserContext } from '../../../App'
import { useContext } from 'react'

export default function WarehouseFilterer(){
    const {warehouses, setWarehouses} = useContext(WarehousesContext)
    const {user} = useContext(UserContext)

    // Sorts and filters the table results
    const filterWarehouses = (e) => {
        e.preventDefault();

        let sortedWarehouses = user.warehouses;

        // Name
        let name = e.target.name.value.trim();
        if(name !== ''){
            sortedWarehouses = sortedWarehouses.filter((warehouse) => warehouse.name.includes(name));
        }

        // Location
        let location = e.target.location.value.trim();
        if(location !== ''){
            sortedWarehouses = sortedWarehouses.filter((warehouse) => warehouse.location === location)
        }

        // Size
        let size = e.target.size.value.trim();
        if(size > 0){
            sortedWarehouses = sortedWarehouses.filter((warehouse) => warehouse.size >= size);
        }

        setWarehouses(sortedWarehouses)
    }

    return <>
        <form className={styles.HorizontalFlexBox + ' ' + styles.FlexGap} onSubmit={filterWarehouses}>
            <input style={{flex: 5}} 
                id='inWarehouseName'
                name='name'
                type='text' 
                placeholder='Name' />
            <input style={{flex: 1}}
                id='inWarehouseLocation'
                name='location'
                type='text' 
                placeholder='Location' />
            <input style={{flex: 1}}
                id='idWarehouseSize'
                name='size'
                type='number' 
                placeholder='Size' />
            <input style={{flex: 1}}
                id='btnSubmitSearch'
                type='submit' 
                value='Search' />
        </form>
    </>
}