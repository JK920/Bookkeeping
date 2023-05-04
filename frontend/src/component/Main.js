import React, {Component} from "react";
import './Main.css'
import ViewAccount from "./ViewAccount";
import AddAccount from "./AddAccount";
import OpeningBalances from "./OpeningBalances";
import ViewCustomer from "./ViewCustomer";
import AddCustomer from "./AddCustomer";
import AddInvoice from "./Addinvoice";
export default class Main extends Component{

    render(){
        return(
            <div className="container-fluid " id='main'>
                <div >
                    <div className="sideMenu">
                        <ul className="list-unstyled ps-0">
                            <li className="section1">
                                <button className="btn btn-toggle align-items-center rounded collapsed" >
                                    Dashboard
                                </button>
                            </li>
                            <li className="section1">
                                <button className="btn btn-toggle align-items-center rounded collapsed" data-bs-toggle="collapse" data-bs-target="#acc-collapse" aria-expanded="false">
                                    Account
                                </button>
                                <div className="collapse show" id="acc-collapse" >
                                    <ul className="btn-toggle-nav list-unstyled fw-normal pb-1 small">
                                        <li><span className="link-dark rounded">View Accounts</span></li>
                                        <li><span className="link-dark rounded">Add Account</span></li>
                                        <li><span className="link-dark rounded">Update Opening Balances</span></li>
                                        
                                    </ul>
                                </div>
                            </li>
                            <li className="section1">
                                <button className="btn btn-toggle align-items-center rounded collapsed" data-bs-toggle="collapse" data-bs-target="#customer-collapse" aria-expanded="false">
                                    Customer
                                </button>
                                <div className="collapse show" id="customer-collapse">
                                <ul className="btn-toggle-nav list-unstyled fw-normal pb-1 small">
                                        <li><span className="link-dark rounded">View Customers</span></li>
                                        <li><span className="link-dark rounded">Add Customer</span></li>
                                        <li><span className="link-dark rounded">Modidy Customers</span></li>
                                        <li><span className="link-dark rounded">View Invoices</span></li>
                                        <li><span className="link-dark rounded">Add Invoice</span></li>
                                    </ul>
                                </div>
                            </li>
                            <li className="section1">
                                <button className="btn btn-toggle align-items-center rounded collapsed" data-bs-toggle="collapse" data-bs-target="#vendor-collapse" aria-expanded="false">
                                    Vendors
                                </button>
                                <div className="collapse show" id="vendor-collapse">
                                <ul className="btn-toggle-nav list-unstyled fw-normal pb-1 small">
                                        <li><span className="link-dark rounded">View Vendors</span></li>
                                        <li><span className="link-dark rounded">Add Vendor</span></li>
                                        <li><span className="link-dark rounded">Modidy Vendors</span></li>
                                        <li><span className="link-dark rounded">View Bills</span></li>
                                        <li><span className="link-dark rounded">Add Bills</span></li>
                                    </ul>
                                </div>
                            </li>
                            <li className="section1">
                                <button className="btn btn-toggle align-items-center rounded collapsed" data-bs-toggle="collapse" data-bs-target="#journal-collapse" aria-expanded="false">
                                    Journals
                                </button>
                                <div className="collapse show" id="journal-collapse">
                                <ul className="btn-toggle-nav list-unstyled fw-normal pb-1 small">
                                        <li><span className="link-dark rounded">View Journals</span></li>
                                        <li><span className="link-dark rounded">Add Journal</span></li>
                                    </ul>
                                </div>
                            </li>

                        </ul>
                    </div>
                    <div className="dash">
                        <AddInvoice/>
                    </div>
                </div>
            </div>
        );
    }
}