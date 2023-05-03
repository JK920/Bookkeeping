import React, {Component} from "react";
import './Navbar.css';
export default class Navbar extends Component{
    render(){
        return(
            <nav class="navbar navbar-expand-lg navbar-dark fixed-top" id="mainNav">
                <span class="navbar-brand" >BOOKS</span>
                <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarResponsive" aria-controls="navbarResponsive" aria-expanded="false" aria-label="Toggle navigation">
                    Menu
                    <i class="fas fa-bars ms-1"></i>
                </button>
                <div class="collapse navbar-collapse" id="">
                    <ul class="navbar-nav text-uppercase ms-auto py-4 py-lg-0">
                        <li class="nav-item"><a class="nav-link" href="#services">Home</a></li>
                        <li class="nav-item"><a class="nav-link" href="#about">About</a></li>
                        <li class="nav-item"><a class="nav-link" href="#portfolio">Login</a></li>
                        <li class="nav-item"><a class="nav-link" href="#team">Register</a></li>
                    </ul>
                </div>
        </nav>
        );
    }
}