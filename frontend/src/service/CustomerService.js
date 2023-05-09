import React ,{Component} from "react";
import axios from "axios";

class CustomerService extends Component{

    viewCustomers(userId){
        return axios.get("http://localhost:8080/get/user/"+userId+"/customers");
    }

    modifyCustomer(custId,cust){
        return axios.put("http://localhost:8080/update/user/customers/"+custId,cust);
    }

    addCustomer(cust){
        return axios.post("http://localhost:8080/create/customer",cust);
    }
}
export default new CustomerService()