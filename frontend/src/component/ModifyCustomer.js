import React, {Component} from "react";

export default class ModifyCustomer extends Component{

    render(){
        return(
            <div className=" container-fluid">
                    <form className=" p-5">
                        <label className='from-label'>Customer Name</label>
                        <input type='text'  className='form-control' required/>
                        <label className='from-label'>Address</label>
                        <input type='text' className='form-control'/>
                        <label className='from-label'>Phone Number</label>
                        <input type='tel'  className='form-control' pattern="[6-9]{1}[0-9]{9}"/>
                        <label className='from-label' required>Email</label>
                        <input type='email' className='form-control'/>
                        <button type='submit' className='btn btn-primary'>Add Account</button>
                    </form>
            </div>
        );
    }
}