import React, { Component } from "react";
import './Logout.css'
export default class Logout extends Component{

    render(){
        return( 
            <div className="signout">
                <h4> You have been logged out. Go to Home Page to Login.</h4>
            </div>
        );
    }
}