import React,{Component} from "react";
import InvoiceService from "../../service/InvoiceService";
import Delete from "../images/trash.svg";

export default class ViewInvoice extends Component{

    constructor(){
        super();
        this.state={
            invL:[]
        };
        this.deleteInvoiceHandler = this.deleteInvoiceHandler.bind(this);
    }
    payHandler(id){
        InvoiceService.invoicePaid(localStorage.getItem("userId") ,id)
        .then(()=>{
            this.componentDidMount();
        })
    }
    deleteInvoiceHandler(id){
        InvoiceService.deleteInvoice(id,localStorage.getItem("userId"))
        .then(()=>{
            this.componentDidMount();
        })
        .catch(err=>{
            console.log(err.response.data.message);
        })
    }
    componentDidMount(){
        InvoiceService.viewInvoices(localStorage.getItem("userId"))
            .then(res=>{
                console.log(res.data)
                const list=[];
                res.data.map(inv=>{
                if(inv.billedBy===localStorage.getItem("company")){
                    list.push(inv);
                }
            });
            this.setState({invL:list});
            })
            .catch(err=>{
                console.log(err.response.data.message);
            });
    }
    render(){
        
        return(
            <div className="container-fluid">
                
                <table style={{textAlign:'center'}} className="table">
                    <thead>
                        <tr>
                            <th scope="col">Invoice Id</th>
                            <th scope="col">Customer Name</th>
                            <th scope="col">Tax</th>
                            <th scope="col">Amount</th>
                            <th scope="col">Paid</th>
                            <th scope="col">Delete</th>
                        </tr>
                    </thead>
                    <tbody>
                        {
                            this.state.invL.map((inv)=>{
                                let p="Mark as payed"
                                if(inv.paid===true){
                                    p="Payed"
                                }
                                return(
                                    <tr  key={inv.invoiceId}>
                                        <td>{inv.invoiceId}</td>
                                        <td>{inv.billedTo}</td>
                                        <td>{inv.taxPercentage}</td>
                                        <td>{inv.totalAmount}</td>
                                        <td><button className="btn btn-outline-light btn-sm" style={{margin:'0'}} onClick={()=>this.payHandler(inv.invoiceId)}>{p}</button></td>
                                        <td ><button className="btn btn-outline-light btn-sm" style={{margin:'0'}} onClick={()=>this.deleteInvoiceHandler(inv.invoiceId)}><img style={{margin:'0'}} src={Delete}/></button></td>
                                    </tr>
                                )
                            })
                        }
                        
                    </tbody>
                </table>
            </div>
        );
    }
}