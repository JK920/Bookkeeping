import React from 'react';
import { BrowserRouter, Routes, Route } from "react-router-dom";
import './App.css';

import Home from './component/Home';
import HomeContent from './component/HomeContent';
import Login from './component/Login';
import Register from './component/Register';
import Logout from './component/Logout';

import Main from './component/Main';
import Dashboard from './component/Dashboard/Dashboard';
import AddAccount from './component/Account/AddAccount';
import ViewAccount from './component/Account/ViewAccount';
import OpeningBalances from './component/Account/OpeningBalances';

import AddCustomer from './component/Customer/AddCustomer';
import AddInvoice from './component/Customer/AddInvoice';
import ModifyCustomer from './component/Customer/ModifyCustomer';
import ViewCustomer from './component/Customer/ViewCustomer';
import ViewInvoice from './component/Customer/ViewInvoice';

import AddBills from './component/Vendor/AddBills';
import AddVendor from './component/Vendor/AddVendor';
import ModifyVendor from './component/Vendor/ModifyVendor';
import ViewBills from './component/Vendor/ViewBills';
import ViewVendors from './component/Vendor/ViewVendors';

import AddJournal from './component/Journal/AddJournal';
import ViewJournal from './component/Journal/ViewJournal';

function App() {
  return (

              <BrowserRouter>
                    <Routes>
                      <Route path="/" element={<Home/>}>
                        <Route index element={<HomeContent />} />
                        <Route path="/register" element={<Register/>} />
                        <Route path="/login" element={<Login/>} />
                        <Route path='/logout' element={<Logout/>}/> 
                      </Route>
                      <Route path="/main" element={<Main/>}>
                        <Route index  element={<Dashboard/>} />
                        <Route path='/main/dashboard' element={<Dashboard/>} />

                        <Route path="/main/accountschart" element={<ViewAccount/>} />
                        <Route path="/main/addaccount" element={<AddAccount/>} />
                        <Route path="/main/openingbalances" element={<OpeningBalances/>} />

                        <Route path='/main/addcustomer' element={<AddCustomer/>} />
                        <Route path="/main/addinvoice" element={<AddInvoice/>} />
                        <Route path="/main/modifycustomer" element={<ModifyCustomer/>} />
                        <Route path="/main/viewcustomer" element={<ViewCustomer/>} />
                        <Route path='/main/viewinvoice' element={<ViewInvoice/>} />

                        <Route path="/main/addbills" element={<AddBills/>} />
                        <Route path="/main/addvendor" element={<AddVendor/>} />
                        <Route path="/main/modifyvendor" element={<ModifyVendor/>} />
                        <Route path="/main/viewbills" element={<ViewBills/>} />
                        <Route path="/main/viewvendors" element={<ViewVendors/>} />
                        
                        <Route path="/main/addjournal" element={<AddJournal/>} />
                        <Route path="/main/ViewJournal" element={<ViewJournal/>} />
                      </Route>
                    </Routes>
                </BrowserRouter>

    // <div className="App">
    //   <div className='row'>
    //     <div className='col'>
    //       <NavbarMain/>
    //     </div>
    //   </div>
    //   <div className='row'>
    //     <div className='col'>
    //       <Main/>
    //     </div>
    //   </div>
    // </div>
  );
}

export default App;
