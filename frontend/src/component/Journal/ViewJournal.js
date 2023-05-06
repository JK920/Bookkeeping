import React,{Component} from "react";

export default class ViewJournal extends Component{

    render(){
        return(
            <div className="container-fluid">
                
                <table className="table">
                    <thead>
                        <tr>
                            <th scope="col">Journal Id</th>
                            <th scope="col">Date</th>
                            <th scope="col">Account</th>
                            <th scope="col">Reference</th>
                            <th scope="col">Description</th>
                            <th scope="col">Invoice Id</th>
                            <th scope="col">Customer Id</th>
                            <th scope="col">Vendor Id</th>
                            <th scope="col">Debit</th>
                            <th scope="col">Credit</th>
                        </tr>
                    </thead>
                    <tbody>
                        <tr>
                            <td>I0001</td>
                            <td>Customer</td>
                            <td>5</td>
                            <td>50000</td>
                            <td>False</td>
                            <td>I0001</td>
                            <td>Customer</td>
                            <td>5</td>
                            <td>50000</td>
                            <td>False</td>
                        </tr>
                    </tbody>
                </table>
            </div>
        );
    }
}