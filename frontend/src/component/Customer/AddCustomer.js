import React, {Component} from "react";
import CustomerService from "../../service/CustomerService";

export default class AddCustomer extends Component{

    constructor(){
        super();
        this.state = {
            name:"",
            address:"",
            phoneNo:"",
            email:""
        };
        this.nameChangeHandler = this.nameChangeHandler.bind(this);
        this.addressChangeHandler = this.addressChangeHandler.bind(this);
        this.phoneChangeHandler = this.phoneChangeHandler.bind(this);
        this.emailChangeHandler = this.emailChangeHandler.bind(this);
        this.customerSubmitHandler = this.customerSubmitHandler.bind(this);
    }

    nameChangeHandler=(event)=>{
        this.setState({name:event.target.value});
    }
    addressChangeHandler=(event)=>{
        this.setState({address:event.target.value});
    }
    phoneChangeHandler=(event)=>{
        this.setState({phoneNo:event.target.value});
    }
    emailChangeHandler=(event)=>{
        this.setState({email:event.target.value});
    }
    customerSubmitHandler=(event)=>{
        event.preventDefault();
        const cust={
            userId:localStorage.getItem("userId"),
            name:this.state.name,
            address:this.state.address,
            phoneNo:this.state.phoneNo,
            email:this.state.email
        }
        CustomerService.addCustomer(cust)
            .then(res=>{
                document.getElementById('res').innerHTML="Customer Details Added";
            })
            .catch(err=>{
                console.log(err.response.data.message);
            });
        this.setState({name:""});
        this.setState({address:""});
        this.setState({phoneNo:""});
        this.setState({email:"0"});
    }
    render(){
        return(
            <div className=" container-fluid">
                <h2>Add Customer</h2>
                <form className=" p-5" onSubmit={this.customerSubmitHandler}>
                    <label className='form-label'>Customer Name</label>
                    <input type='text' onChange={this.nameChangeHandler} value={this.state.name} className='form-control' required/>
                    <label className='form-label'>Address</label>
                    <input type='text' onChange={this.addressChangeHandler} value={this.state.address} className='form-control'/>
                    <label className='form-label'>Phone Number</label>
                    <input type='tel' onChange={this.phoneChangeHandler} value={this.state.phoneNo} className='form-control' pattern="[6-9]{1}[0-9]{9}"/>
                    <label className='form-label' >Email</label>
                    <input type='email' onChange={this.emailChangeHandler} value={this.state.email} className='form-control' required/>
                    <button type='submit' className='btn btn-primary'>Add Customer</button>
                    <div id='res'></div>
                </form>
            </div>
        );
    }
}