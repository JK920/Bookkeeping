import React ,{Component} from "react";
import axios from "axios";

class UserService extends Component{

    registerUser(user){
        return axios.post("http://localhost:8080/create/user",user);
    }
    userLogin(login){
        return axios.post("http://localhost:8080/login/user",login);
    }
}
export default new UserService()