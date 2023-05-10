import React, {Component} from "react";
import VendorService from "../../service/VendorService";

export default class AddVendor extends Component{
    constructor(){
        super();
        this.state={
            name:"",
            address:"",
            phone:"",
            email:""
        }
        this.handleNameChange = this.handleNameChange.bind(this);
        this.handleAddressChange = this.handleAddressChange.bind(this);
        this.handlePhoneChange = this.handlePhoneChange.bind(this);
        this.handleEmailChange = this.handleEmailChange.bind(this);
        this.submitVendorHanlder = this.submitVendorHanlder.bind(this);
    }
    handleNameChange(event){
        this.setState({name:event.target.value});
    }
    handleAddressChange(event){
        this.setState({address:event.target.value});
    }
    handlePhoneChange(event){
        this.setState({phone:event.target.value});
    }
    handleEmailChange(event){
        this.setState({email:event.target.value});
    }
    submitVendorHanlder(event){
        event.preventDefault();
        const vendor={
            userId:localStorage.getItem("userId"),
            name:this.state.name,
            address:this.state.address,
            phoneNo:this.state.phone,
            email:this.state.email
        }
        VendorService.addVendor(vendor)
        .then(()=>{
            document.getElementById('res').innerHTML="Vendor Added";
        })
        .catch(err=>{
            console.log(err.response.data.message);
        })
    }

    render(){
        return(
            <div className=" container-fluid">
                <h2>Add Vendor</h2>
                <form className=" p-5" onSubmit={this.submitVendorHanlder}>
                    <label className='form-label'>Vendor Name</label>
                    <input type='text' onChange={this.handleNameChange} className='form-control' required/>
                    <label className='form-label'>Address</label>
                    <input type='text' onChange={this.handleAddressChange} className='form-control' required/>
                    <label className='form-label'>Phone Number</label>
                    <input type='tel' onChange={this.handlePhoneChange} className='form-control' pattern="[6-9]{1}[0-9]{9}"/>
                    <label className='form-label' required>Email</label>
                    <input type='email' onChange={this.handleEmailChange} className='form-control'required/>
                    <button type='submit' className='btn btn-primary'>Add Vendor</button>
                </form>
                <div id='res'></div>
            </div>
        );
    }
}