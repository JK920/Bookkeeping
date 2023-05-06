import React, {Component} from "react";
import "bootstrap/js/src/collapse.js";
import './Navbar.css';
export default class Navbar extends Component{
    render(){
        return(
            <nav className="navbar navbar-expand-lg navbar-dark fixed-top" id="homeNav">
                <span className="navbar-brand" >BOOKS</span>
                <button className="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarResponsive" aria-controls="navbarResponsive" aria-expanded="false" aria-label="Toggle navigation">
                    <span className="navbar-toggler-icon"></span>
                </button>
                <div className="collapse navbar-collapse" id="navbarResponsive">
                    <ul className="navbar-nav ms-auto">
                        <li className="nav-item"><a className="nav-link" href="/">Home</a></li>
                        <li className="nav-item"><a className="nav-link" href="#About">About</a></li>
                        <li className="nav-item"><a className="nav-link" href="#Help">Help</a></li>
                        <li className="nav-item"><a className="nav-link" href="/login">Login</a></li>
                        <li className="nav-item"><a className="nav-link" href="/register">Register</a></li>
                    </ul>
                </div>
        </nav>
        );
    }
}