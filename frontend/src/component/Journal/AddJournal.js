import React, {Component} from "react";
import AccountService from "../../service/AccountService";
import CustomerService from "../../service/CustomerService";
import InvoiceService from "../../service/InvoiceService";
import VendorService from "../../service/VendorService";
import JournalService from "../../service/JournalService";

export default class AddJournal extends Component{

    constructor(){
        super();
        this.state={
            rows:[1,1],
            accL:[],
            custL:[],
            invL:[],
            venL:[],
            date:"",
            reference:"",
            invoiceId:""
        };
        this.handleDateChange = this.handleDateChange.bind(this);
        this.handleReferenceChange = this.handleReferenceChange.bind(this);
        this.handleInvoiceIdChange = this.handleInvoiceIdChange.bind(this);
        this.handleRowIncrement = this.handleRowIncrement.bind(this);
        this.handleRowDecrement = this.handleRowDecrement.bind(this);
        this.handleSubmitJournal = this.handleSubmitJournal.bind(this);
    }

    handleDateChange(event){
        this.setState({date:event.target.value});
    }
    handleReferenceChange(event){
        this.setState({reference:event.target.value});
    }
    handleInvoiceIdChange(event){
        this.setState({invoiceId:event.target.value});
    }

    handleRowDecrement(event){
        event.preventDefault();
        const t = this.state.rows;
        t.pop();
        this.setState({rows:t});
    }
    handleRowIncrement(event){
        event.preventDefault();
        const t = this.state.rows;
        t.push(1);
        this.setState({rows:t});
    }

    handleSubmitJournal(event){
        event.preventDefault();
        const l=[];
        const r=this.state.rows.length;
        for(let i=0;i<r;i++){
            const j={
                userId:localStorage.getItem("userId"),
                date:this.state.date,
                reference:this.state.reference,
                invoiceId:this.state.invoiceId,
                accountId:document.getElementById(i+"-acc").value,
                description:document.getElementById(i+"-desc").value,
                customerId:document.getElementById(i+"-cust").value,
                vendorId:document.getElementById(i+"-ven").value,
                credit:document.getElementById(i+"-credit").value,
                debit:document.getElementById(i+"-debit").value
            }
            l.push(j);
        }
        JournalService.addJournalEntries(l)
        .then(res=>{
            alert("Journal Entries Added");
        });
    }

    componentDidMount(){
        AccountService.viewAccount(localStorage.getItem("userId"))
        .then(res=>{
            this.setState({accL:res.data});
        }).catch(err=>{
            console.log(err.response.data.message);
        });

        CustomerService.viewCustomers(localStorage.getItem("userId"))
        .then(res=>{
            this.setState({custL:res.data});
        }).catch(err=>{
            console.log(err.response.data.message);
        });
        
        InvoiceService.viewInvoices(localStorage.getItem("userId"))
        .then(res=>{
            this.setState({invL:res.data})
        }).catch(err=>{
            console.log(err.response.data.message);
        })

        VendorService.viewVendors(localStorage.getItem("userId"))
        .then(res=>{
            this.setState({venL:res.data})
        })
    }
    render(){
        return(
            <div className="container-fluid">
                <h2>Add Journal Entries</h2>
                <form style={{width:'inherit'}} >
                    <label className="form-label">Date</label>
                    <input onChange={this.handleDateChange} className="form-control" type="date"/>
                    <label className="form-label">Reference</label>
                    <input onChange={this.handleReferenceChange} className="form-control" type="text"/>
                    <label className="form-label">InvoiceId</label>
                    <select onChange={this.handleInvoiceIdChange} className="form-control" type="text">
                        <option value="">No invoice Selected</option>
                        {
                            this.state.invL.map(inv=>{
                                return(<option value={inv.invoiceId}>{inv.invoiceId}</option>)
                            })
                        }
                    </select>
                 <table className="table">
                    <thead>
                        <tr>
                            <th className="col-2">Account Name</th>
                            <th className="col-3">Description</th>
                            <th className="col-2">Customer Name</th>
                            <th className="col-2">Vendor Name</th>
                            <th className="col-1">Debit</th>
                            <th className="col-1">Credit</th>
                        </tr>
                    </thead>
                    <tbody>
                        {this.state.rows.map((r,i)=>{return(
                            <tr>
                                <td>
                                    <select id={i+"-acc"} placeholder="Select" className="form-select form-select-sm">
                                        {this.state.accL.map((acc)=>{
                                            return(
                                                <option value={acc.accountId}>{acc.accountName}</option>
                                            )}
                                            )
                                        }
                                    </select>
                                </td>
                                <td><input id={i+"-desc"} className="form-control form-control-sm" type="text"/></td>
                                <td>
                                    <select id={i+"-cust"} className="form-select form-select-sm">
                                        <option value="">Select Customer</option>
                                        {this.state.custL.map((c)=>{
                                            return(
                                                <option value={c.customerId}>{c.name}</option>
                                            )}
                                            )
                                        }
                                    </select>
                                </td>
                                <td>
                                    <select id={i+"-ven"} className="form-select form-select-sm">
                                        <option value="">Select Vendor</option>
                                        {this.state.venL.map((v)=>{
                                            return(
                                                <option value={v.vendorId}>{v.name}</option>
                                            )}
                                            )
                                        }
                                    </select>
                                </td>
                                <td><input id={i+"-debit"} className="form-control form-control-sm"  type="text"/></td>
                                <td><input id={i+"-credit"} className="form-control form-control-sm"  type="text"/></td>    
                            </tr>
                        )}
                        )}
                    </tbody>
                    </table>
                    <button onClick={this.handleRowIncrement} className="btn btn-sm btn-info">Add New Line</button>
                    <button onClick={this.handleRowDecrement} className="btn btn-sm btn-info mx-5">Remove Line</button>
                    <br/>
                    <button onClick={this.handleSubmitJournal} type="submit" className="btn btn-primary my-5">Submit</button>             
                </form>
                <div id="res"></div>
            </div>
        );
    }
}
