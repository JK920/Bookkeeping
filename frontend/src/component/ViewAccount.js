import React, {Component} from "react";

export default class ViewAccount extends Component{
    
    render(){
        return(
            <div className="container-fluid">
                
                <table className="table">
                    <thead>
                        <tr>
                            <th scope="col">ID</th>
                            <th scope="col">Account Name</th>
                            <th scope="col">Balance</th>
                        </tr>
                    </thead>
                    <tbody>
                        <tr>
                            <td>A0001</td>
                            <td>Cash</td>
                            <td>50000</td>
                        </tr>
                    </tbody>
                </table>
            </div>

        );
    }
}