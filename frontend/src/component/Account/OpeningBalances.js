import React, {Component} from "react";
import AccountService from "../../service/AccountService";

export default class OpeningBalances extends Component{

    constructor(){
        super();
        this.state={
            accL :[],
            bal :""
        }
        this.updateBalanceHandler = this.updateBalanceHandler.bind(this);
        this.handleBalanceChange = this.handleBalanceChange.bind(this);
    }
    componentDidMount(){
        AccountService.viewAccount(localStorage.getItem("userId"))
            .then(res=>{
                this.setState({accL:res.data});
            })
    }
    handleBalanceChange = (event)=>{
        this.setState({bal:event.target.data})
    }

    updateBalanceHandler=(event)=>{
        event.preventDefault();
        let map= {}
        for(let i=0;i<this.state.accL.length;i++){
            const id = this.state.accL[i].accountId;
            const name = this.state.accL[i].accountName;
            let balance = document.getElementById(id).value;
            if(balance===""){
                continue;
            }
            map[id]=balance;
        }
        console.log(map);
        AccountService.updateOpeningBalance(localStorage.getItem("userId"),map)
        .then(res=>{
            console.log(res.data)
        })
        .catch(err =>{
            console.log(err.resposnse.data.messgae);
        })
    }

    render(){
        return(
            <div className="container-fluid">
                <h2>Opening Balances</h2>
                <table className="table">
                    <thead>
                        <tr>
                            <th className="col-6" scope="col">Account Name</th>
                            <th className="col-4" scope="col">Balance</th>
                        </tr>
                    </thead>
                    <tbody>
                        {this.state.accL.map((account,i) =>{
                            return(
                                <tr key={account.accountId}>
                            <td>{account.accountName}</td>
                            <td>
                                <input type="number" placeholder={account.balance} value={this.bal} onChange={this.handleBalanceChange} className="form-control" id={account.accountId} />
                            </td>
                  
                            </tr>
                            );
                        })}
                        
                    </tbody>
                </table>
                    <button className="btn btn-primary" onClick={this.updateBalanceHandler}>Update Balances</button>
            </div>
        );
    }
}