import React from 'react'
import { createContext } from "react";
import { useState } from 'react';

export const AuthContext = createContext()

const AuthComponent = ({children}) => {
    const [user, setUser] = useState(null)
  return <AuthContext.Provider value={{user, setUser}}>
    {children}
  </AuthContext.Provider>
}

export default AuthComponent
