import React, {Component} from "react";
import CustomerService from "../../service/CustomerService";

export default class ModifyCustomer extends Component{

    constructor(){
        super();
        this.state = {
            id: "",
            name: "",
            email: "",
            address: "",
            phone: ""
        }
        this.handleIdChange = this.handleIdChange.bind(this);
        this.handleNameChange = this.handleNameChange.bind(this);
        this.handleEmailChange = this.handleEmailChange.bind(this);
        this.handleAddressChange = this.handleAddressChange.bind(this);
        this.handlePhoneChange = this.handlePhoneChange.bind(this);
        this.submitModifyHandler = this.submitModifyHandler.bind(this);
    }
    handleIdChange=(event)=>{
        this.setState({id:event.target.value});
    }
    handleNameChange=(event)=>{
        this.setState({name:event.target.value});
    }
    handleEmailChange=(event)=>{
        this.setState({email:event.target.value});
    }
    handleAddressChange=(event)=>{
        this.setState({address:event.target.value});
    }
    handlePhoneChange=(event)=>{
        this.setState({phone:event.target.value});
    }
    submitModifyHandler=(event)=>{
        event.preventDefault();
        const id = this.state.id;
        const cust={
            userId : localStorage.getItem("userId"),
            name:this.state.name,
            email:this.state.email,
            address:this.state.address,
            phoneNo:this.state.phone
        }
        console.log(cust);
        CustomerService.modifyCustomer(id,cust)
        .then(res=>{
            console.log(res.data);
        })
        .catch(err=>{
            console.log(err.response.data.message);
        })
    }
    render(){
        return(
            <div className=" container-fluid" onSubmit={this.submitModifyHandler}>
                    <form className=" p-5">
                        <label className='form-label'>Customer Id</label>
                        <input type='text' onChange={this.handleIdChange} className='form-control' required/>
                        <label className='form-label'>Customer Name</label>
                        <input type='text' onChange={this.handleNameChange} className='form-control' required/>
                        <label className='form-label'>Address</label>
                        <input type='text' onChange={this.handleAddressChange} className='form-control'/>
                        <label className='form-label'>Phone Number</label>
                        <input type='tel' onChange={this.handlePhoneChange}  className='form-control' pattern="[6-9]{1}[0-9]{9}"/>
                        <label className='form-label' required>Email</label>
                        <input type='email' onChange={this.handleEmailChange} className='form-control'/>
                        <button type='submit' className='btn btn-primary'>Modify Customer</button>
                    </form>
            </div>
        );
    }
}