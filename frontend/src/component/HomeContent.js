import React, { Component } from "react";
import { Outlet, Link } from "react-router-dom";

export default class HomeContent extends Component{
    render(){
        return(
            <div className="mainContent">
                    <h1 className="mainH1">Books</h1>
                    <h4>Books is an application that aims at simplifying the continuos process of bookkeeping for small business.</h4>
                    <div>
                        <Link to="/login" style={{ color: 'inherit', textDecoration: 'inherit'}}><button className="btn btn-lg btn-dark mainBtn">Login</button></Link>
                        <Link to="/register" style={{ color: 'inherit', textDecoration: 'inherit'}}><button className="btn btn-lg btn-light mainBtn"> Register</button></Link>
                    </div>
            </div>
        );
    }
}