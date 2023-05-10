import React ,{Component} from "react";
import axios from "axios";

class UserService extends Component{

    registerUser(user){
        return axios.post("http://localhost:8080/create/user",user);
    }
    userLogin(login){
        return axios.post("http://localhost:8080/login/user",login);
    }
    changePass(email,pass){
        return axios.put("http://localhost:8080/update/user/"+email+"/password",pass);
    }
}
export default new UserService()