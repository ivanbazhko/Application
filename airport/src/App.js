import React from 'react';
import Header from "./components/Header"
import Footer from "./components/Footer"
import { BrowserRouter, Routes, Route } from 'react-router-dom';
import FlightsPage from './components/FlightsPage';
import MainPage from './components/MainPage';
import AccountPage from './components/AccountPage';
import AboutPage from './components/AboutPage';
import AirportForm from './components/AirportForm.js';

function App() {
  return (
    <BrowserRouter>
      <div className="wrapper">
        <Header />
        <Routes>
          <Route path="/" element={<MainPage />} />
          <Route path="/flights" element={<FlightsPage />} />
          <Route path="/account" element={<AccountPage />} />
          <Route path="/about" element={<AboutPage />} />
        </Routes>
        <Footer />
      </div>
    </BrowserRouter>
  );
}

export default App;
