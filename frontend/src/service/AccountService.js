import React ,{Component} from "react";
import axios from "axios";

class AccountService extends Component{

    addAccount(acc){
        return axios.post("http://localhost:8080/create/account",acc);
    }
    viewAccount(userId){
        return axios.get("http://localhost:8080/get/users/"+userId+"/accounts");
    }

    updateOpeningBalance(userId,map){
        return axios.put("http://localhost:8080/update/users/"+userId+"/openingbalances",map);
    }

    accountByName(userId,accName){
        return axios.get("http://localhost:8080/get/users/"+userId+"/accountsbyname/"+accName);
    }

    totalByAccount(userId,type){
        return axios.get("http://localhost:8080/get/users/"+userId+"/accountbalance/"+type);
    }
}
export default new AccountService()