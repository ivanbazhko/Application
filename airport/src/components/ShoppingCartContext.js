import React, { createContext, useState } from 'react';

export const ShoppingCartContext = createContext()

const ShoppingCartProvider = ({ children }) => {
    const [cartItems, setCartItems] = useState([]);

    const addToCart = (item) => {
        setCartItems((prevItems) => [...prevItems, item]);
    };

    const removeFromCart = (numberToRemove) => {
        setCartItems((prevItems) => prevItems.filter((item) => item.number !== numberToRemove));
    };

    const getCartItems = () => {
        return cartItems;
    };

    return <ShoppingCartContext.Provider value={{ cartItems, addToCart, removeFromCart, getCartItems }}>
        {children}
    </ShoppingCartContext.Provider>
};

export default ShoppingCartProvider
