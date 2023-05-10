import React, {Component} from "react";
import CustomerService from "../../service/CustomerService";
import InvoiceService from "../../service/InvoiceService";

export default class AddInvoice extends Component{

    constructor(){
        super();
        this.state={
            invoiceId:"",
            invoiceDate:"",
            dueDate:"",
            billedTo:"",
            tax:"",
            totalAmount:"",
            cogs:"",
            isPaid:false,
            custL:[]
        }
        this.handleInvoiceIdChange = this.handleInvoiceIdChange.bind(this);
        this.handleInvoiceDateChange = this.handleInvoiceDateChange.bind(this);
        this.handleDueDateChange = this.handleDueDateChange.bind(this);
        this.handleBilledToChange = this.handleBilledToChange.bind(this);
        this.handleTaxChange = this.handleTaxChange.bind(this);
        this.handleTotalChange = this.handleTotalChange.bind(this);
        this.handleCogsChange = this.handleCogsChange.bind(this);
        this.handlePaid = this.handlePaid.bind(this);
        this.submitInvoiceHandler = this.submitInvoiceHandler.bind(this);

    }

    handleInvoiceIdChange=(event)=>{
        this.setState({invoiceId:event.target.value});
    }
    handleInvoiceDateChange=(event)=>{
        this.setState({invoiceDate:event.target.value});
    }
    handleDueDateChange=(event)=>{
        this.setState({dueDate:event.target.value});
    }
    handleBilledToChange=(event)=>{
        this.setState({billedTo:event.target.value});
    }
    handleTaxChange=(event)=>{
        this.setState({tax:event.target.value});
    }
    handleTotalChange=(event)=>{
        this.setState({totalAmount:event.target.value});
    }
    handleCogsChange=(event)=>{
        this.setState({cogs:event.target.value});
    }
    handlePaid=(event)=>{
        this.setState({isPaid:event.target.checked});
    }
    submitInvoiceHandler=(event)=>{
        event.preventDefault();
        const inv ={
            userId:localStorage.getItem("userId"),
            invoiceId:this.state.invoiceId,
            invoiceDate:this.state.invoiceDate,
            dueDate:this.state.dueDate,
            billedBy:localStorage.getItem("company"),
            billedTo:this.state.billedTo,
            taxPercentage:this.state.tax,
            totalAmount:this.state.totalAmount,
            costPrice:this.state.cogs,
            paid:this.state.isPaid
        }
        InvoiceService.addInvoice(inv)
        .then(res=>{
            document.getElementById('res').innerHTML= "Invoice Added";
        })
        .catch(err=>{
            console.log(err.data.message);
        })
    }
    componentDidMount(){ 
        CustomerService.viewCustomers(localStorage.getItem("userId"))
            .then(res=>{
                this.setState({custL:res.data});
            })
            .catch(err=>{
                console.log(err.response.data.message);
            })   
    }
    render(){
        return(
            <div className=" container-fluid" onSubmit={this.submitInvoiceHandler}>
                <h2>Add Invoice</h2>
                    <form className=" p-5">
                        <label className='form-label'>Invoice Id</label>
                        <input type='text' onChange={this.handleInvoiceIdChange} id="invoiceId" value={this.state.invoiceId} className='form-control' required/>
                        <label className='form-label'>Invoice Date</label>
                        <input type='date' onChange={this.handleInvoiceDateChange} id="invoiceDate" value={this.state.invoiceDate} className='form-control' required/>
                        <label className='form-label'>Due Date</label>
                        <input type='date' onChange={this.handleDueDateChange} id="dueDate" value={this.state.dueDate} className='form-control' required/>
                        <label className='form-label'  >Billed To</label>
                        <select onChange={this.handleBilledToChange} id="billedTo" value={this.state.billedTo} className='form-control' required>
                            <option>Select Billed To</option>
                            {
                                this.state.custL.map(customer=>{
                                    return( <option key={customer.customerId} value={customer.name}>{customer.name}</option>)
                                })
                            }
                        </select>
                        <label className='form-label' >Tax</label>
                        <input type='text' onChange={this.handleTaxChange} id="tax" value={this.state.tax} className='form-control'/>
                        <label className='form-label' >Total Amount</label>
                        <input type='text'  onChange={this.handleTotalChange} id="totalAmount" value={this.state.totalAmount} className='form-control' required/>
                        <label className='form-label' >Cost Price of Sold Item</label>
                        <input type='text' onChange={this.handleCogsChange} id="cogs" value={this.state.cogs} className='form-control' required/>
                        <div className="my-2">
                        <label style={{margin:'5px'}} className='form-check-label' >Paid</label>
                        <input type='checkbox' onChange={this.handlePaid} id="cogs" value={this.state.cogs} className='form-check-input' style={{margin:'10px'}}/>
                        </div>
                        <button type='submit' className='btn btn-primary'>Add Invoice</button>
                    </form>
                    <div id="res"></div>
            </div>
        );
    }
}