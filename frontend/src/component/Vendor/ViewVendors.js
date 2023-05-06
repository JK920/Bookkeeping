import React, {Component} from "react";

export default class ViewVendors extends Component{
    
    render(){
        return(
            <div className="container-fluid">
                <table className="table">
                    <thead>
                        <tr>
                            <th scope="col">ID</th>
                            <th scope="col">Vendor Name</th>
                            <th scope="col">Address</th>
                            <th scope="col">Phone Number</th>
                            <th scope="col">Email</th>
                        </tr>
                    </thead>
                    <tbody>
                        <tr>
                            <td>V0001</td>
                            <td>Vendor</td>
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