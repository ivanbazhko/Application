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
import App from './App';
import AuthComponent from './components/AuthComponent';

const root = ReactDOM.createRoot(document.getElementById('root'));
root.render(
  <React.StrictMode>
    <AuthComponent>
      <App />
    </AuthComponent>
  </React.StrictMode>
);
