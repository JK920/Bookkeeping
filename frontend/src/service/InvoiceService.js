import React ,{Component} from "react";
import axios from "axios";

class InvoiceService extends Component{

    addInvoice(inv){
        return axios.post("http://localhost:8080/create/invoice",inv);
    }
    invoicePaid(userId,invoiceId){
        return axios.put("http://localhost:8080/update/user/"+userId+"/invoice/"+invoiceId+"/paid");
    }
    viewInvoices(userId){
        return axios.get("http://localhost:8080/get/user/"+userId+"/invoices");
    }
    deleteInvoice(invId,userId){
        return axios.delete("http://localhost:8080/delete/user/"+userId+"/invoice/"+invId);
    }

}
export default new InvoiceService()