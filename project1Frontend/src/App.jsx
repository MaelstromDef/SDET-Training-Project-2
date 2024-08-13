import { createContext, useState } from 'react'
import reactLogo from './assets/react.svg'
import viteLogo from '/vite.svg'
import './App.css'

import Navbar from './components/Navbar'
import { BrowserRouter, Link, Navigate, Route, Routes } from 'react-router-dom'

import Landing from './Pages/Landing'
import Account from './Pages/Account'
import Home from './Pages/Home'
import Items from './Pages/Items'
import Login from './Pages/Login'
import Signup from './Pages/Signup'
import Warehouses from './Pages/Warehouses'


export const UserContext = createContext();
export const baseUrl = "http://localhost:8080";
export const logout = () =>{
  setUser({
    // Admin information
    adminInfo:{
      id: null,
      companyName: null
    },

    authorization: null,
    warehouse: {
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
    warehouse: {
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
