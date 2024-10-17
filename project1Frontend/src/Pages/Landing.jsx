import './page.css';

import { useNavigate } from "react-router-dom"
import LandingHero from "../components/Landing/Hero/LandingHero";
import LandingImageCard from "../components/Landing/LandingImageCard";

import warehouseAdmin from '/src/assets/WarehouseAdminSmall.jpg';
import warehouseBoxes from '/src/assets/WarehouseBoxes.jpg';
import warehouseWorker from '/src/assets/WarehouseWorkerSmall.jpg';

export default function Landing(){
    return <>
        <LandingHero/>
        <div id='divImageCards' className='ImageCards'>
            <LandingImageCard background={warehouseBoxes}>
                <p>Access inventory across multiple warehouses.</p>
                <p>Move key items across locations.</p>
                <p>Know what is where, when it's there.</p>
            </LandingImageCard>
            <LandingImageCard background={warehouseWorker}>
                <p>Add employees.</p>
                <p>Allocate manpower.</p>
                <p>Track employee count.</p>
            </LandingImageCard>
            <LandingImageCard background={warehouseAdmin} textAlign={'right'} >
                <p>Less hassle.</p>
                <p>More time.</p>
                <p>More money.</p>
            </LandingImageCard>
        </div>
    </>
}