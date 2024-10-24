import { useContext, useEffect, useState } from "react"
import { WarehousesContext } from "../Warehouses"
import { UserContext } from "../../../App";

import styles from '../Warehouses.module.css'

export default function WarehouseSorter(){
    const {user} = useContext(UserContext);
    const {warehouses, setWarehouses} = useContext(WarehousesContext);

    const [sortBy, setSortBy] = useState();

    // Sorts the warehouses based on the value of sortBy
    const sortWarehouses = () => {
        let sortedWarehouses = user.warehouses;

        switch(sortBy){
            case 'name':
                sortedWarehouses = warehouses.sort((a, b) => a.name.localeCompare(b.name))
                break;
            case 'location':
                sortedWarehouses = warehouses.sort((a, b) => a.location.localeCompare(b.location))
                break;
            case 'size':
                sortedWarehouses = warehouses.sort((a, b) => a.size - b.size);
                break;
        }

        setWarehouses([...sortedWarehouses]);
    }

    // Sorts the warehouses whenever theres a change to user or sortBy
    useEffect(() => {
        sortWarehouses();
    }, [user, sortBy])

    return <>
        <div className={styles.HorizontalFlexBox + ' ' + styles.FlexGap + ' ' + styles.FlexEnd}>
            <button id='btnSortByName' 
                className={styles.TableButton + ' ' + styles.FitContent}
                onClick={() => setSortBy('name')}>
                <img src='/src/assets/AlphabeticalIcon.png' style={{height: '16px'}}/>
            </button>
            <button id='btnSortByLocation' 
                className={styles.TableButton + ' ' + styles.FitContent}
                onClick={()=> setSortBy('location')}>
                <img src='/src/assets/LocationIcon.png' style={{height: '16px'}}/>
            </button>
            <button id='btnSortBySize' 
                className={styles.TableButton + ' ' + styles.FitContent}
                onClick={()=> setSortBy('size')}>
                <img src='/src/assets/NumbersIcon.png' style={{height: '16px'}}/>
            </button>
        </div>
    </>
}