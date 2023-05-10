import React,{Component} from "react";
import AccountService from "../../service/AccountService";

export default class Dashboard extends Component{

    constructor(){
        super();
        this.state={
            asset:"",
            liability:"",
            revenue:"",
            equity:"",
            expense:"",
            payable:"",
            receivable:"",
            income:""
        };
    }

    componentDidMount(){
        AccountService.totalByAccount(localStorage.getItem("userId"),'ASSET')
        .then((res)=>{
            this.setState({asset:res.data});
        });
        AccountService.totalByAccount(localStorage.getItem("userId"),'LIABILITY')
        .then((res)=>{
            this.setState({liability:res.data});
        });
        AccountService.totalByAccount(localStorage.getItem("userId"),'REVENUE')
        .then((res)=>{
            this.setState({revenue:res.data});
        });
        AccountService.totalByAccount(localStorage.getItem("userId"),'EQUITY')
        .then((res)=>{
            this.setState({equity:res.data});
        });
        AccountService.totalByAccount(localStorage.getItem("userId"),'EXPENSE')
        .then((res)=>{
            this.setState({expense:res.data});
        });

        AccountService.accountByName(localStorage.getItem("userId"),"Accounts Payable")
        .then(res=>{
            this.setState({payable:res.data.balance});
        })

        AccountService.accountByName(localStorage.getItem("userId"),"Accounts Receivable")
        .then(res=>{
            this.setState({receivable:res.data.balance});
        })
        AccountService.generateIncome(localStorage.getItem("userId"))
        .then(res=>{
            this.setState({income:res.data});
        })
        

    }
    render(){
        return(
            <div className="container-fluid">
                <h2>Dashboard</h2>
                <div className="row my-5">
                    <div className="col">
                        <div className="card">
                            <div className="card-header">
                                <h3 className="card-title">Total Payables</h3>
                            </div>
                            <div className="card-body">
                                <h4 className="card-title">{this.state.payable}</h4>
                            </div>
                        </div>
                    </div>
                    <div className="col">
                        <div className="card">
                            <div className="card-header">
                                <h3 className="card-title">Total Receivables</h3>
                            </div>
                            <div className="card-body">
                                <h4 className="card-title">{this.state.receivable}</h4>
                            </div>
                        </div>
                    </div>
                    <div className="col">
                        <div className="card">
                            <div className="card-header">
                                <h3 className="card-title">Income</h3>
                            </div>
                            <div className="card-body">
                                <h4 className="card-title">{this.state.income}</h4>
                            </div>
                        </div>
                    </div>
                </div>
                <div className="row my-5">
                    <div className="col">
                        <div className="card">
                            <div className="card-header">
                                <h3 className="card-title">Total Asset</h3>
                            </div>
                            <div className="card-body">
                                <h4 className="card-title">{this.state.asset}</h4>
                            </div>
                        </div>
                    </div>
                    <div className="col">
                        <div className="card">
                            <div className="card-header">
                                <h3 className="card-title">Total Liability</h3>
                            </div>
                            <div className="card-body">
                                <h4 className="card-title">{this.state.liability}</h4>
                            </div>
                        </div>
                    </div>
                </div>
                <div className="row my-5">
                    <div className="col">
                        <div className="card">
                            <div className="card-header">
                                <h3 className="card-title">Total Revenue</h3>
                            </div>
                            <div className="card-body">
                                <h4 className="card-title">{this.state.revenue}</h4>
                            </div>
                        </div>
                    </div>
                    <div className="col">
                        <div className="card">
                            <div className="card-header">
                                <h3 className="card-title">Total Equity</h3>
                            </div>
                            <div className="card-body">
                                <h4 className="card-title">{this.state.equity}</h4>
                            </div>
                        </div>
                    </div>
                    <div className="col">
                        <div className="card">
                            <div className="card-header">
                                <h3 className="card-title">Total Expense</h3>
                            </div>
                            <div className="card-body">
                                <h4 className="card-title">{this.state.expense}</h4>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            
        );
    }
}