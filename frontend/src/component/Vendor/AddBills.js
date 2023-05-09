import React, {Component} from "react";
import InvoiceService from "../../service/InvoiceService";
import VendorService from "../../service/VendorService";

export default class AddBills extends Component{

    constructor(){
        super();
        this.state = {
            billId:"",
            billDate:"",
            dueDate:"",
            billedBy:"",
            tax:0,
            totalAmount:"",
            isPaid:false,
            venL:[]
        }
        this.handleBillIdChange = this.handleBillIdChange.bind(this);
        this.handleBillDateChange = this.handleBillDateChange.bind(this);
        this.handleDueDateChange = this.handleDueDateChange.bind(this);
        this.handleBilledByChange = this.handleBilledByChange.bind(this);
        this.handleTaxChange = this.handleTaxChange.bind(this);
        this.handleTotalChange = this.handleTotalChange.bind(this);
        this.submitBillHandler = this.submitBillHandler.bind(this);
    }

    handleBillDateChange(event){
        this.setState({billDate:event.target.value});
    }
    handleDueDateChange(event){
        this.setState({dueDate:event.target.value});
    }
    handleBillIdChange(event){
        this.setState({billId:event.target.value});
    }
    handleBilledByChange(event){
        this.setState({billedBy:event.target.value});
    }
    handleTaxChange(event){
        this.setState({tax:event.target.value});
    }
    handleTotalChange(event){
        this.setState({totalAmount:event.target.value});
    }

    submitBillHandler(event){
        event.preventDefault();
        const bill = {
            userId:localStorage.getItem("userId"),
            invoiceId:this.state.billId,
            invoiceDate:this.state.billDate,
            dueDate:this.state.dueDate,
            billedBy:this.state.billedBy,
            billedTo:localStorage.getItem("company"),
            taxPercentage:this.state.tax,
            totalAmount:this.state.totalAmount,
            costPrice:0,
            paid:this.state.isPaid
        }
        InvoiceService.addInvoice(bill)
        .then(()=>{
            document.getElementById('res').innerHTML= "Bill Added";
        })
        .catch(err=>{
            console.log(err.data.message);
        })
    }
    componentDidMount(){ 
        VendorService.viewVendors(localStorage.getItem("userId"))
            .then(res=>{
                this.setState({venL:res.data});
            })
            .catch(err=>{
                console.log(err.response.data.message);
            })   
    }

    render(){
        return(
            <div className=" container-fluid">
                    <form className=" p-5" onSubmit={this.submitBillHandler}>
                        <label className='form-label'>Bill Id</label>
                        <input type='text' onChange={this.handleBillIdChange} className='form-control' required/>
                        <label className='form-label'>Bill Date</label>
                        <input type='date' onChange={this.handleBillDateChange} className='form-control'required/>
                        <label className='form-label'>Due Date</label>
                        <input type='date' onChange={this.handleDueDateChange} className='form-control' required />
                        <label className='form-label'  >Billed By</label>
                        <select onChange={this.handleBilledByChange} id="billedBy" value={this.state.billedBy} className='form-control' required>
                            <option>Select Billed By</option>
                            {
                                this.state.venL.map(vendor=>{
                                    return( <option key={vendor.vendorId} value={vendor.name}>{vendor.name}</option>)
                                })
                            }
                        </select>
                        <label className='form-label' >Tax</label>
                        <input type='text' onChange={this.handleTaxChange} className='form-control'/>
                        <label className='form-label' >Total Amount</label>
                        <input type='text' onChange={this.handleTotalChange} className='form-control' required/>
                        <button type='submit' className='btn btn-primary'>Add Bills</button>
                    </form>
                    <div id ='res'></div>
            </div>
        );
    }
}