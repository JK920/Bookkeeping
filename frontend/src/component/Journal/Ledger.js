import React, { Component } from "react";
import AccountService from "../../service/AccountService";

export default class Ledger extends Component{

    constructor(){
        super();
        this.state={
            acc:[]
        }
    }
    componentDidMount(){
        AccountService.generateLedger(localStorage.getItem("userId"))
        .then(res=>{
            this.setState({acc:Object.entries(res.data)});
            });
        
    }
    render(){
        return(
            <div className="container-fluid">
                <h2>General Ledger</h2>
                {
                    this.state.acc.map((acc)=>{
                        if(acc[1].length>0){
                        return(
                            <div>
                                <h5>{acc[0]}</h5>
                                <table className="table">
                                    <thead>
                                        <tr>
                                            <th className="col-2">Date</th>
                                            <th className="col-2" >Reference</th>
                                            <th className="col-2">Description</th>
                                            <th className="col-2" >Debit</th>
                                            <th className="col-2">Credit</th>
                                            <th className="col-2">Balance</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                    {
                                        acc[1].map(l=>{
                                            const led = Object.values(l);
                                            return(
                                
                                                <tr>
                                                    <th>{led[0]}</th>
                                                    <th>{led[1]}</th>
                                                    <th>{led[2]}</th>
                                                    <th>{led[3]}</th>
                                                    <th>{led[4]}</th>
                                                    <th>{led[5]}</th>
                                                </tr>
                                        
                                            );
                                        })
                                    }
                                    </tbody>
                                </table>
                            </div>
                            )
                        }
                    })
                }
          </div>

        );
    }
}