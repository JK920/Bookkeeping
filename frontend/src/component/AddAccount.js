import React, {Component} from "react";

export default class AddAccount extends Component{

    render(){
        return(
            <div className=" container-fluid">
                    <form className=" p-5">
                        <label className='from-label'>Account Name</label>
                        <input type='text'  className='form-control' />
                        <label className='from-label'>Account Type</label>
                        <select className="form-control">
                            <option>ASSET</option>
                            <option>EXPENSE</option>
                            <option>EQUITY</option>
                            <option>LIABILITIY</option>
                            <option>REVENUE</option>
                        </select>
                        <label className='from-label'>Balance</label>
                        <input type='number' className='form-control'/>
                        <button type='submit' className='btn btn-primary'>Add Account</button>
                    </form>
            </div>
        );
    }
}