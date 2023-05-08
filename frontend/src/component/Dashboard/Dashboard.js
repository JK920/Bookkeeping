import React,{Component} from "react";

export default class Dashboard extends Component{

    render(){
        return(
            <div className="container-fluid">
                dashboard
                <div className="row">
                    <div className="col">
                        <h1>TotalPayable</h1>
                    </div>
                    <div className="col">
                        <h1>TotalReceivable</h1>
                    </div>
                </div>
                <div className="row">
                    <div className="col">
                        <h1>TotalIncome</h1>
                    </div>
                    <div className="col">
                        <h1>TotalExpense</h1>
                    </div>
                </div>

            </div>
        );
    }
}