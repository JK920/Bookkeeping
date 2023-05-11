import React,{Component} from "react";
import InvoiceService from "../../service/InvoiceService";
import Delete from "../images/trash.svg";

export default class ViewBills extends Component{

    constructor(){
        super();
        this.state={
            billsL:[]
        };
        this.deletebillHandler = this.deletebillHandler.bind(this);
        this.payHandler = this.payHandler.bind(this);
    }

    payHandler(id){
        InvoiceService.invoicePaid(localStorage.getItem("userId") ,id)
        .then(()=>{
            this.componentDidMount();
        })
    }

    deletebillHandler(id){
        InvoiceService.deleteInvoice(id,localStorage.getItem("userId"))
        .then(()=>{
            this.componentDidMount();
        }).catch(err=>{
            console.log(err.response.data.message);
        })
    }

    componentDidMount(){
        InvoiceService.viewInvoices(localStorage.getItem("userId"))
        .then((res)=>{
            const list=[];
            res.data.map(bill=>{
                if(bill.billedTo===localStorage.getItem("company")){
                    list.push(bill);
                }
            });
            this.setState({billsL:list});
        })
        .catch(err=>{
            console.log(err.response.data.message);
        });
    }
    render(){
        return(
            <div className="container-fluid">
                <h2>List Of Bills</h2>
                <table className="table" style={{textAlign:'center'}}>
                    <thead>
                        <tr>
                            <th scope="col">Bill Id</th>
                            <th scope="col">Vendor Id</th>
                            <th scope="col">Tax</th>
                            <th scope="col">Amount</th>
                            <th scope="col">Paid</th>
                            <th scope="col">Delete</th>
                        </tr>
                    </thead>
                    <tbody>
                        {this.state.billsL.map(bill=>{
                            console.log(bill);
                            let p="Mark as payed";
                            if(bill.paid===true){
                                p="Payed";
                            }
                            return(<tr key={bill.invoiceId}>
                                <td>{bill.invoiceId}</td>
                                <td>{bill.billedBy}</td>
                                <td>{bill.taxPercentage}</td>
                                <td>{bill.totalAmount}</td>
                                <td><button className="btn btn-outline-dark btn-sm" style={{margin:'0'}} onClick={()=>this.payHandler(bill.invoiceId)}>{p}</button></td>
                                <td><button className="btn btn-outline-light btn-sm" style={{margin:'0'}} onClick={()=>this.deletebillHandler(bill.invoiceId)}><img style={{margin:'0'}} src={Delete}/></button></td>
                            </tr>)
                        })}
                        
                    </tbody>
                </table>
            </div>
        );
    }
}