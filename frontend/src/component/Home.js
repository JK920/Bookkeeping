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
                    <p>Books is an application that aims at simplifying the continuos process of bookkeeping for small business. Gone are the days of pen and paper.</p>
                </div>
            </div>

        );
    }
}