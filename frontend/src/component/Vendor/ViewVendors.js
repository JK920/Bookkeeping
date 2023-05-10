import React, {Component} from "react";
import VendorService from "../../service/VendorService";

export default class ViewVendors extends Component{
    
    constructor(){
        super();
        this.state={
            venL:[]
        }
    }
    componentDidMount(){
        VendorService.viewVendors(localStorage.getItem("userId"))
        .then(res=>{
            this.setState({venL:res.data})
        }).catch(err=>{
            console.log(err.response.data.message);
        })
    }
    render(){
        return(
            <div className="container-fluid">
                <h2>Vendors List</h2>
                <table className="table">
                    <thead>
                        <tr>
                            <th scope="col">Vendor Id</th>
                            <th scope="col">Vendor Name</th>
                            <th scope="col">Address</th>
                            <th scope="col">Phone Number</th>
                            <th scope="col">Email</th>
                        </tr>
                    </thead>
                    <tbody>
                        {this.state.venL.map(ven=>{
                            return(
                                <tr key={ven.vendorId}>
                                    <td>{ven.vendorId}</td>
                                    <td>{ven.name}</td>
                                    <td>{ven.address}</td>
                                    <td>{ven.phoneNo}</td>
                                    <td>{ven.email}</td>
                                </tr>
                            )

                        })}
                        
                    </tbody>
                </table>
            </div>
        );
    }
}