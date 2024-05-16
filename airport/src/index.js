import React from 'react';
import ReactDOM from 'react-dom/client';
import './styles/index.css';
import './styles/header.css';
import './styles/footer.css';
import './styles/main.css';
import './styles/flights.css';
import './styles/bookingform.css';
import './styles/loginform.css';
import './styles/account.css';
import './styles/about.css';
import './styles/worldmap.css';
import './styles/adminform.css';
import './styles/custommarker.css';
import './styles/timetablepage.css';
import './styles/cart.css'
import App from './App';
import AuthComponent from './components/AuthComponent';
import ShoppingCartProvider from './components/ShoppingCartContext';

const root = ReactDOM.createRoot(document.getElementById('root'));
root.render(
  <React.StrictMode>
    <AuthComponent>
      <ShoppingCartProvider>
        <App />
      </ShoppingCartProvider>
    </AuthComponent>
  </React.StrictMode>
);
