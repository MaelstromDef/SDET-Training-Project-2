import { createContext, useState } from 'react'
import reactLogo from './assets/react.svg'
import viteLogo from '/vite.svg'
import './App.css'

import Navbar from './components/Navbar/Navbar'
import { BrowserRouter, Link, Navigate, Route, Routes } from 'react-router-dom'

import Landing from './Pages/Landing'
import Account from './components/Account/Account'
import Home from './Pages/Dashboard'
import Items from './Pages/Items'
import Login from './Pages/Login'
import Signup from './Pages/Signup'
import Warehouses from './components/Warehouses/Warehouses'


export const UserContext = createContext();
// export const baseUrl = "http://ahuggins-warehousemanager.us-east-1.elasticbeanstalk.com";

let myUrl = 'http://localhost:8081'

let env = import.meta.env.MODE;
if (env === 'production') {
  myUrl = "http://ahuggins-warehousemanager.us-east-1.elasticbeanstalk.com";
}

export const baseUrl = myUrl;

export const logout = () =>{
  setUser({
    // Admin information
    adminInfo:{
      id: null,
      companyName: null
    },

    authorization: null,
    warehouses: [],
    currentWarehouse: {
      id: null,
      name: null,
      location: null,
      size: null
    }
  })
}

function App() {
  const [user, setUser] = useState({
    // Admin information
    adminInfo:{
      id: null,
      companyName: null
    },

    authorization: null,
    warehouses: [],
    currentWarehouse: {
      id: null,
      name: null,
      location: null,
      size: null
    }
  })

  return (
    <UserContext.Provider value={{user, setUser}}>
      <BrowserRouter>
        <Navbar></Navbar>

        <Routes>
          <Route path="/" element={<Landing/>}/>
          <Route path="/account" element={<Account/>}/>
          <Route path='/home' element={<Home/>}/>
          <Route path='/items' element={<Items/>}/>
          <Route path='/login' element={<Login/>}/>
          <Route path='/signup' element={<Signup/>}/>
          <Route path='/warehouses' element={<Warehouses/>}/>

          <Route path="*" element={<Navigate to="/"/>}/>
        </Routes>
      </BrowserRouter>
    </UserContext.Provider>
  )
}

export default App
