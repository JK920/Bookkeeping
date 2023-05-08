import React, {Component} from "react";
import './Home.css';
import Navbar from "./Navbar";
import { Outlet } from "react-router-dom";

export default class Home extends Component{
    render(){
        return(
            <div className="container-fluid top">
                <Navbar/>
                <Outlet/>
            </div>
        );
    }
}