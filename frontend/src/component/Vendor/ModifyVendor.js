import React, {Component} from "react";
import VendorService from "../../service/VendorService";

export default class ModifyVendor extends Component{

    constructor(){
        super();
        this.state={
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
        const ven={
            userId : localStorage.getItem("userId"),
            name:this.state.name,
            email:this.state.email,
            address:this.state.address,
            phoneNo:this.state.phone
        }
        VendorService.modifyVendor(id,ven)
        .then(()=>{
            document.getElementById("res").innerHTML="Vendor Details Updated";
        })
        .catch(err=>{
            console.log(err.response.data.message);
        })
    }
    render(){
        return(
            <div className=" container-fluid" onSubmit={this.submitModifyHandler}>
                <h2>Modify Vendor Details</h2>
                    <form className=" p-5">
                        <label className='form-label'>Vendor Id</label>
                        <input type='text' onChange={this.handleIdChange} className='form-control' required/>
                        <label className='form-label'>Vendor Name</label>
                        <input type='text' onChange={this.handleNameChange} className='form-control' required/>
                        <label className='form-label'>Address</label>
                        <input type='text' onChange={this.handleAddressChange} className='form-control'/>
                        <label className='form-label'>Phone Number</label>
                        <input type='tel' onChange={this.handlePhoneChange}  className='form-control' pattern="[6-9]{1}[0-9]{9}"/>
                        <label className='form-label' required>Email</label>
                        <input type='email' onChange={this.handleEmailChange} className='form-control'/>
                        <button type='submit' className='btn btn-primary'>Modify Vendor</button>
                    </form>
                    <div id="res"></div>
            </div>
        );
    }
}