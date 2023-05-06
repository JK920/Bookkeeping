import React, {Component} from "react";

export default class AddBills extends Component{

    render(){
        return(
            <div className=" container-fluid">
                    <form className=" p-5">
                        <label className='form-label'>Invoice Id</label>
                        <input type='text'  className='form-control' required/>
                        <label className='form-label'>Invoice Date</label>
                        <input type='date' className='form-control'/>
                        <label className='form-label'>Due Date</label>
                        <input type='date'  className='form-control' />
                        <label className='form-label' required>Billed To</label>
                        <input type='text' className='form-control'/>
                        <label className='form-label' required>Tax</label>
                        <input type='text' className='form-control'/>
                        <label className='form-label' required>Total Amount</label>
                        <input type='text' className='form-control'/>
                        <button type='submit' className='btn btn-primary'>Add Bills</button>
                    </form>
            </div>
        );
    }
}