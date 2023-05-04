import React, {Component} from "react";

export default class OpeningBalances extends Component{

    render(){
        return(
            <div className="container-fluid">
                <table className="table">
                    <thead>
                        <tr>
                            <th className="col-6" scope="col">Account Name</th>
                            <th className="col-4" scope="col">Balance</th>
                        </tr>
                    </thead>
                    <tbody>
                        <tr>
                            <td>Account</td>
                            <td>
                                <input type="number" className="form-control" id="balance"/>
                            </td>
                  
                        </tr>
                    </tbody>
                </table>
            </div>
        );
    }
}