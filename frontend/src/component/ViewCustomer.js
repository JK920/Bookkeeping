import React, {Component} from "react";

export default class ViewCustomer extends Component{
    
    render(){
        return(
            <div className="container-fluid">
                
                <table className="table">
                    <thead>
                        <tr>
                            <th scope="col">ID</th>
                            <th scope="col">Customer Name</th>
                            <th scope="col">Address</th>
                            <th scope="col">Phone Number</th>
                            <th scope="col">Email</th>
                        </tr>
                    </thead>
                    <tbody>
                        <tr>
                            <td>C0001</td>
                            <td>Customer</td>
                            <td>KOC</td>
                            <td>88888888</td>
                            <td>email@email.com</td>
                        </tr>
                    </tbody>
                </table>
            </div>

        );
    }
}