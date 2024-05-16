import Table from './Timetable';
import React, { useEffect, useState, useRef, useContext } from 'react'
import axios from 'axios'
import { ShoppingCartProvider, ShoppingCartContext } from './ShoppingCartContext';
import { AuthContext } from './AuthComponent';

const TimetablePage = () => {

    // const { cartItems, addToCart, removeFromCart, getCartItems } = useContext(ShoppingCartContext);

    const [allDeps, setAllDeps] = useState([]);
    const [homeDeps, setHomeDeps] = useState([]);
    const [allArrs, setAllArrs] = useState([]);
    const [homeArrs, setHomeArrs] = useState([]);

    var alldeps;

    const getAllDeps = () => {
        axios
            .get('http://localhost:8080/api/airport/getalldepartures')
            .then(response => {
                alldeps = response.data.map(item => item);
                setAllDeps(response.data[0])
                setHomeDeps(response.data[1])
                setAllArrs(response.data[2])
                setHomeArrs(response.data[3])
                // console.log(response.data)
                // console.log("adding ", response.data[0][0])
                // addToCart(response.data[0][0])
                // console.log("Cart contains: ", cartItems)
            })
            .catch(error => {
                console.error(error);
            });
    };

    useEffect(() => {
        getAllDeps();
    }, []);

    useEffect(() => {
        const interval = setInterval(() => {
            getAllDeps();
            // console.log("UPDATING");
        }, 30000);

        return () => {
            clearInterval(interval);
        };
    }, []);

    return (
        // <ShoppingCartProvider>
        <div className="ttcontainer">
            <div>
                <div className='alldepdiv'>
                    <h1>All Departures</h1>
                    <Table data={allDeps} />
                </div>
                <div className='allarrdiv'>
                    <h1>All Arrivals</h1>
                    <Table data={allArrs} />
                </div>
            </div>
            <div>
                <div className='homedepdiv'>
                    <h1>Home Departures</h1>
                    <Table data={homeDeps} />
                </div>
                <div className='homearrdiv'>
                    <h1>Home Arrivals</h1>
                    <Table data={homeArrs} />
                </div>
            </div>
        </div>
        // </ShoppingCartProvider>
    );

};

export default TimetablePage;
