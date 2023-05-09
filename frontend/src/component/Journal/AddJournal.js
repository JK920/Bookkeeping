import React, {Component} from "react";

export default class AddJournal extends Component{

    constructor(){
        super();
        this.state={

        };
    }
    render(){
        return(
            <div className=" container-fluid">
                    <form className=" p-5">
                        <label className='form-label'>Date</label>
                        <input type='text' className='form-control' required/>
                        <label className='form-label'>Account Name</label>
                        <select className="form-control" ></select>
                        <label className='form-label'>Reference</label>
                        <input type='date'  className='form-control' />
                        <label className='form-label' required>Description</label>
                        <input type='text' className='form-control'/>
                        <label className='form-label' required>Invoice Id</label>
                        <input type='text' className='form-control'/>
                        <label className='form-label' required>Customer Id</label>
                        <input type='text' className='form-control'/>
                        <label className='form-label' required>Vendor Id</label>
                        <input type='text' className='form-control'/>
                        <label className='form-label' required>Debit</label>
                        <input type='text' className='form-control'/>
                        <label className='form-label' required>Credit</label>
                        <input type='text' className='form-control'/>
                        <button type='submit' className='btn btn-primary'>Add Bills</button>
                    </form>
            </div>
        );
    }
}