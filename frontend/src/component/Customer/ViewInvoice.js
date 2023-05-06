import React,{Component} from "react";

export default class ViewInvoice extends Component{

    render(){
        return(
            <div className="container-fluid">
                
                <table className="table">
                    <thead>
                        <tr>
                            <th scope="col">Invoice Id</th>
                            <th scope="col">Customer Name</th>
                            <th scope="col">Tax</th>
                            <th scope="col">Amount</th>
                            <th scope="col">Paid</th>
                        </tr>
                    </thead>
                    <tbody>
                        <tr>
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