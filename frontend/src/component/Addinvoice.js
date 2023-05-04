import React, {Component} from "react";

export default class AddInvoice extends Component{

    render(){
        return(
            <div className=" container-fluid">
                    <form className=" p-5">
                        <label className='from-label'>Invoice Id</label>
                        <input type='text'  className='form-control' required/>
                        <label className='from-label'>Invoice Date</label>
                        <input type='date' className='form-control'/>
                        <label className='from-label'>Due Date</label>
                        <input type='date'  className='form-control' />
                        <label className='from-label' required>Billed To</label>
                        <input type='text' className='form-control'/>
                        <label className='from-label' required>Tax</label>
                        <input type='text' className='form-control'/>
                        <label className='from-label' required>Total Amount</label>
                        <input type='text' className='form-control'/>
                        <label className='from-label' required>Cost Price of Sold Item</label>
                        <input type='text' className='form-control'/>
                        <button type='submit' className='btn btn-primary'>Add Account</button>
                    </form>
            </div>
        );
    }
}