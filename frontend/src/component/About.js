import React, { Component } from "react";

export default class About extends Component{
    render(){
        return(
            <div className="container-fluid my-5" style={{textAlign:'left',lineHeight:"2rem"}}>
                <h2>About</h2>
                <p>This is a demo project developed to simplify and digitize the age old method of bookkeeping using books. 
                    <br/>
                    Every small business owner cannot afford a high end accounting software or resource management tool.
                    <br/> This is a small scale application that can be usefull for small scale business to keep track of their accounts and transactions.
                </p>
            </div>
        );
    }
}