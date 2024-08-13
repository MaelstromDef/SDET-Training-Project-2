import { Outlet, Link } from "react-router-dom";
import './Navbar.css';
import { useContext } from "react";
import { logout, UserContext } from "../App";

export default function Navbar(){
    const {user, setUser} = useContext(UserContext);

    return (<div className="Navbar">
        {
            (user.authorization === null || user.authorization === "") ?
                <>
                    <Link className="NavLink" to='/'>Landing</Link>
                    <Link className="NavLink" to='/login'>Login</Link>
                    <Link className="NavLink" to='/signup'>Signup</Link>
                </> :
                <>
                    <Link className="NavLink" to='/home'>Home</Link>
                    <Link className="NavLink" to='/account'>Account</Link>
                    <Link className="NavLink" to='/warehouses'>Warehouses</Link>
                    <Link className="NavLink" onClick={logout}>Log out</Link>
                </>
        }
    </div>);
}