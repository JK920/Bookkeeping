import React, {Component} from "react";
import CustomerService from "../../service/CustomerService";

export default class ViewCustomer extends Component{
    
    constructor(){
        super();
        this.state={
            custList : []
        };
    }

    componentDidMount(){
        CustomerService.viewCustomers(localStorage.getItem("userId"))
            .then(res=>{
                this.setState({custList:res.data});
            })
            .catch(err=>{
                console.log(err.response.data.message);
            })
    }

    render(){
        return(
            <div className="container-fluid">
                
                <table className="table">
                    <thead>
                        <tr>
                            <th scope="col">Customer Id</th>
                            <th scope="col">Customer Name</th>
                            <th scope="col">Address</th>
                            <th scope="col">Phone Number</th>
                            <th scope="col">Email</th>
                        </tr>
                    </thead>
                    <tbody>
                        {this.state.custList.map(customer=>{
                         return(<tr key={customer.customerId}>
                                    <td>{customer.customerId}</td>
                                    <td>{customer.name}</td>
                                    <td>{customer.address}</td>
                                    <td>{customer.phoneNo}</td>
                                    <td>{customer.email}</td>
                                </tr>);
                            })
                        }   
                    </tbody>
                </table>
            </div>

        );
    }
}