import { Outlet, Link } from "react-router-dom";
import './Navbar.css';
import { useContext } from "react";
import { logout, UserContext } from "../../App";

export default function Navbar(){
    const {user, setUser} = useContext(UserContext);

    return (<div className="Navbar">
        {
            (user.authorization === null || user.authorization === "") ?
                <>
                    <Link id='btnNavbarLanding' className="NavLink" to='/'>
                        <img src='/src/assets/InventoryIcon.png' className='Icon'/>
                    </Link>

                    <div className="Links">
                        <Link id='btnNavbarLogin' className="NavLink" to='/login'>Login</Link>
                        <Link id='btnNavbarSignup' className="NavLink" to='/signup'>Signup</Link>
                    </div>
                </> :
                <>
                    <Link id='btnNavbarHome' className="NavLink" to='/home'>
                        <img src='/src/assets/InventoryIcon.png' className='Icon'/>
                    </Link>

                    <div className="Links">
                        <Link id='btnNavbarAccount' className="NavLink" to='/account'>Account</Link>
                        <Link id='btnNavbarWarehouses' className="NavLink" to='/warehouses'>Warehouses</Link>
                        <Link id='btnNavbarLogOut' className="NavLink" onClick={logout}>Log out</Link>
                    </div>
                </>
        }
    </div>);
}