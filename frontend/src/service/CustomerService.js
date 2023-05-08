import React ,{Component} from "react";
import axios from "axios";

class CustomerService extends Component{

    viewCustomers(userId){
        return axios.get("http://localhost:8080/get/user/"+userId+"/customers");
    }

    modifyCustomer(userId,custId,acc){
        return axios.put("http://localhost:8080/update/user/"+userId+"/customers/"+custId,acc);
    }

    addCustomer(acc){
        return axios.post("http://localhost:8080/create/customer",acc);
    }
}
export default new CustomerService()