import React, { Component } from "react";
import './Logout.css'
export default class Logout extends Component{

    render(){
        localStorage.clear();
        return( 
            <div className="signout">
                <h4> You have been logged out.</h4>
            </div>
        );
    }
}