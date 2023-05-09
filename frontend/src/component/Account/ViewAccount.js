import React, {Component} from "react";
import AccountService from "../../service/AccountService";

export default class ViewAccount extends Component{
    constructor(){
        super();
        this.state={
            accList : [{}]
        }

    }

    componentDidMount(){
        AccountService.viewAccount(localStorage.getItem("userId"))
            .then(res=>{
                this.setState({accList:res.data});
            })
    }

    render(){
        return(
            <div className="container-fluid">
                
                <table className="table">
                    <thead>
                        <tr key="1">
                            <th scope="col">ID</th>
                            <th scope="col">Account Name</th>
                            <th scope="col">Account Type</th>
                            <th scope="col">Balance</th>
                        </tr>
                    </thead>
                    <tbody>
                        {
                        this.state.accList.map(account =>{
                            return(
                            <tr key={account.accountId}>
                                <td>{account.accountId}</td>
                                <td>{account.accountName}</td>
                                <td>{account.type}</td>
                                <td>{account.balance}</td>
                            </tr>);
                        })}
                        
                    </tbody>
                </table>
            </div>

        );
    }
}