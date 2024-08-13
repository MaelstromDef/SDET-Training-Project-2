import { useEffect, useContext } from "react";
import { Navigate, useNavigate } from "react-router-dom";
import { UserContext } from "../App";

export default function Home(){
    const navigate = useNavigate();
    const {user, setUser} = useContext(UserContext)
    useEffect(() =>{
        if(user.authorization === null || user.authorization === "") navigate('/')
    }, [])

    const btnWarehouse_Handler = () =>{
        navigate('/warehouses');
    }

    const btnAccount_Handler = () =>{
        navigate('/account');
    }

    return <>
        <h1>Hello {user.adminInfo.companyName}</h1>
        <button onClick={btnAccount_Handler}>Account</button>
        <button onClick={btnWarehouse_Handler}>Warehouses</button>
    </>
}