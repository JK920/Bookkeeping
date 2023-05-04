import React, {Component} from "react";
import './Home.css';
import Navbar from "./Navbar";
export default class Home extends Component{
    render(){
        return(
            <div className="container-fluid top">
                <Navbar/>
                <div className="mainContent">
                    <h1 className="mainH1">Books</h1>
                    < h4>Books is an application that aims at simplifying the continuos process of bookkeeping for small business.</h4>
                    <div>
                        <button className="btn btn-lg btn-dark mainBtn">Login</button>
                        <button className="btn btn-lg btn-light mainBtn">Register</button>
                    </div>
                </div>
            </div>

        );
    }
}