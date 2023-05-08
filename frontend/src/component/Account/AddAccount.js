import React, {Component} from "react";
import AccountService from "../../service/AccountService";

export default class AddAccount extends Component{

    constructor(){
        super();
        this.state={
            accountName:"",
            type:"ASSET",
            balance:""
        }
        this.handleAccountName = this.handleAccountName.bind(this);
        this.handleTypeSelect = this.handleTypeSelect.bind(this);
        this.handleBalanceChange = this.handleBalanceChange.bind(this);
        this.accountSubmitHandler = this.accountSubmitHandler.bind(this);
    }

    handleAccountName=(event)=>{
        this.setState({accountName:event.target.value});
        document.getElementById('res').innerHTML="";
    }
    handleBalanceChange=(event)=>{
        this.setState({balance:event.target.value});
        document.getElementById('res').innerHTML="";
    }
    handleTypeSelect=(event)=>{
        this.setState({type:event.target.value});
        document.getElementById('res').innerHTML="";
    }
    accountSubmitHandler=(event)=>{
        event.preventDefault();
        const account={
            userId:localStorage.getItem("userId"),
            accountName : this.state.accountName,
            type: this.state.type,
            balance:this.state.balance
        }
        
        AccountService.addAccount(account)
            .then(res =>{
                document.getElementById('res').innerHTML="Account Added";
            })
            .catch(err=>{
                console.log(err.response.data.message)
            })
            this.setState({accountName:""});
            this.setState({type:"ASSET"})
            this.setState({balance:""})
            
    }

    render(){
        return(
            <div className=" container-fluid">
                    <form className=" p-5" onSubmit={this.accountSubmitHandler}>
                        <label className='form-label'>Account Name</label>
                        <input type='text' value={this.state.accountName} onChange={this.handleAccountName}  className='form-control' />
                        <label className='form-label'>Account Type</label>
                        <select onChange={this.handleTypeSelect} value={this.state.type} placeholder="Select Type" className="form-control">
                            <option>ASSET</option>
                            <option>EXPENSE</option>
                            <option>EQUITY</option>
                            <option>LIABILITY</option>
                            <option>REVENUE</option>
                        </select>
                        <label className='form-label'>Balance</label>
                        <input type='number'value={this.state.balance} onChange={this.handleBalanceChange} className='form-control'/>
                        <button type='submit' className='btn btn-primary'>Add Account</button>
                        <div id='res'></div>
                    </form>
            </div>
        );
    }
}