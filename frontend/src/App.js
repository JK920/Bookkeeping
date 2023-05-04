import React from 'react';
import './App.css';
import Login from './component/Login';
import Register from './component/Register';
import Navbar from './component/Navbar';
import Home from './component/Home';
import Main from './component/Main';
import NavbarMain from './component/NavbarMain';

function App() {
  return (
    <div className="App">
      <div className='row'>
        <div className='col'>
          <NavbarMain/>
        </div>
      </div>
      <div className='row'>
        <div className='col'>
          <Main/>
        </div>
      </div>
    </div>
  );
}

export default App;
