import React,{Component} from "react";
import UserService from "../service/UserService";

export default class Profile extends Component{

    constructor(){
        super();
        this.state={
            old:"",
            new:""
        };
        this.showForm = this.showForm.bind(this);
        this.handleChangePassword = this.handleChangePassword.bind(this);
        this.handleOldPassword = this.handleOldPassword.bind(this);
        this.handleNewPassword = this.handleNewPassword.bind(this);
    }

    showForm(){
        document.getElementById("resetPass").style.display="block";
    }
    handleOldPassword(event){
        this.setState({old:event.target.value});
    }
    handleNewPassword(event){
        this.setState({new:event.target.value});
    }

    handleChangePassword(){
        const pass = {
            oldPass : this.state.old,
            newPass : this.state.new
        }
        UserService.changePass(localStorage.getItem("email"),pass)
        .then((res)=>{
            alert(res.data);
        }).catch(err=>{
            alert(err.response.data.message);
        })
    }s
    render(){
        return(
            <div className="container-fluid">
                <div>
                    <h1 >User Details</h1>
                    <h5>Name : {localStorage.getItem("name")}</h5>
                    <h5>Company Name : {localStorage.getItem("company")}</h5>
                    <h5>Email ID : {localStorage.getItem("email")}</h5>
                    <h5>Username : {localStorage.getItem("username")}</h5>
                </div>
                <div>
                    <button onClick={this.showForm} className="btn btn-info">Reset Password</button>
                </div>
                <div id="resetPass" style={{display:'none'}}>
                    <form className="my-5" onSubmit={this.handleChangePassword}>
                        <label className="form-label">Old Password</label>
                        <input className="form-control" type="text" required/>
                        <label className="form-label">New Password</label>
                        <input className="form-control" type="text" required/>
                        <button className="btn btn-primary" type="submit">Change Password</button>
                    </form>
                </div>
            </div>
        );
    }
}