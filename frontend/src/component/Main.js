import React, {Component} from "react";
import './Main.css'
import NavbarMain from "./NavbarMain";
import { Link, Outlet } from "react-router-dom";
import Dash from './images/dash.svg'
import Coin from './images/coin.svg'
import Cust from './images/customer.svg'
import Vendor from './images/vendor.svg'
import Journal from './images/journal.svg'

export default class Main extends Component{

    constructor(){
        super();
    }
    
    render(){

        return(
            <>
            <NavbarMain/>
            <div className="container-fluid " id='main'>
                <div >
                    <div className="sideMenu">
                        <ul className="list-unstyled ps-0">
                            <li className="section1">
                                <Link style={{ color: 'inherit', textDecoration: 'inherit'}} to='/main/dashboard'>
                                    <button className="btn btn-toggle btn-outline-dark text-start rounded collapsed" >
                                    <img src={Dash} alt="dashboard"/>
                                    Dashboard
                                    </button>
                                </Link>
                            </li>
                            <li className="section1">
                                <button className="btn btn-toggle btn-outline-dark text-start rounded collapsed" data-bs-toggle="collapse" data-bs-target="#acc-collapse" aria-expanded="false">
                                    <img src={Coin} alt="account"/>
                                    Account
                                </button>
                                <div className="collapse " id="acc-collapse" >
                                    <ul className="btn-toggle-nav list-unstyled fw-normal pb-1 small">
                                        <li><Link style={{ color: 'inherit', textDecoration: 'inherit'}} to='/main/accountschart' ><span className="btn btn-outline text-start btn-sm my-0">View Accounts</span></Link></li>
                                        <li><Link style={{ color: 'inherit', textDecoration: 'inherit'}} to='/main/addaccount' ><span className="btn btn-outline text-start btn-sm my-0">Add Account</span></Link></li>
                                        <li><Link style={{ color: 'inherit', textDecoration: 'inherit'}} to='/main/openingbalances' ><span className="btn btn-outline text-start btn-sm my-0">Update Opening Balances</span></Link></li>     
                                    </ul>
                                </div>
                            </li>
                            <li className="section1">
                                <button className="btn btn-toggle btn-outline-dark text-start rounded collapsed" data-bs-toggle="collapse" data-bs-target="#customer-collapse" aria-expanded="false">
                                    <img src={Cust} alt="customer"/>
                                    Customer
                                </button>
                                <div className="collapse" id="customer-collapse">
                                    <ul className="btn-toggle-nav list-unstyled fw-normal pb-1 small">
                                        <li><Link style={{ color: 'inherit', textDecoration: 'inherit'}} to='/main/viewcustomer' ><span className="btn btn-outline text-start btn-sm my-0">View Customer</span></Link></li>
                                        <li><Link style={{ color: 'inherit', textDecoration: 'inherit'}} to='/main/addcustomer' ><span className="btn btn-outline text-start btn-sm my-0">Add Customer</span></Link></li>
                                        {/* <li><Link style={{ color: 'inherit', textDecoration: 'inherit'}} to='/main/modifycustomer' ><span className="btn btn-outline text-start btn-sm my-0">Modify Customer</span></Link></li> */}
                                        <li><Link style={{ color: 'inherit', textDecoration: 'inherit'}} to='/main/viewinvoice' ><span className="btn btn-outline text-start btn-sm my-0">View Invoice</span></Link></li>
                                        <li><Link style={{ color: 'inherit', textDecoration: 'inherit'}} to='/main/addinvoice' ><span className="btn btn-outline text-start btn-sm my-0">Add Invoice</span></Link></li>
                                    </ul>       
                                </div>
                            </li>
                            <li className="section1">
                                <button className="btn btn-toggle btn-outline-dark text-start rounded collapsed" data-bs-toggle="collapse" data-bs-target="#vendor-collapse" aria-expanded="false">
                                    <img src={Vendor} alt="vendor"/>
                                    Vendors
                                </button>
                                <div className="collapse" id="vendor-collapse">
                                    <ul className="btn-toggle-nav list-unstyled fw-normal pb-1 small">
                                        <li><Link style={{ color: 'inherit', textDecoration: 'inherit'}} to='/main/viewvendors' ><span className="btn btn-outline text-start btn-sm my-0">View Vendors</span></Link></li>
                                        <li><Link style={{ color: 'inherit', textDecoration: 'inherit'}} to='/main/addvendor' ><span className="btn btn-outline text-start btn-sm my-0">Add Vendor</span></Link></li>
                                        {/* <li><Link style={{ color: 'inherit', textDecoration: 'inherit'}} to='/main/modifyvendor' ><span className="btn btn-outline text-start btn-sm my-0">Modify Vendor</span></Link></li> */}
                                        <li><Link style={{ color: 'inherit', textDecoration: 'inherit'}} to='/main/viewbills' ><span className="btn btn-outline text-start btn-sm my-0">View Bills</span></Link></li>
                                        <li><Link style={{ color: 'inherit', textDecoration: 'inherit'}} to='/main/addbills' ><span className="btn btn-outline text-start btn-sm my-0">Add Bill</span></Link></li>
                                    </ul>
                                </div>
                            </li>
                            <li className="section1">
                                <button className="btn btn-toggle btn-outline-dark text-start rounded collapsed" data-bs-toggle="collapse" data-bs-target="#journal-collapse" aria-expanded="false">
                                    <img src={Journal} alt="journal"/>
                                    Journals
                                </button>
                                <div className="collapse " id="journal-collapse">
                                    <ul className="btn-toggle-nav list-unstyled fw-normal pb-1 small">
                                        <li><Link style={{ color: 'inherit', textDecoration: 'inherit'}} to='/main/viewjournal' ><span className="btn btn-outline text-start btn-sm">View Journal</span></Link></li>
                                        <li><Link style={{ color: 'inherit', textDecoration: 'inherit'}} to='/main/addjournal' ><span className="btn btn-outline text-start btn-sm my-0">Add Journal</span></Link></li>

                                    </ul>
                                </div>
                            </li>

                        </ul>
                    </div>
                    <div className="dash">
                        <Outlet/>
                    </div>
                </div>
            </div>
            </>
        );
    }
}