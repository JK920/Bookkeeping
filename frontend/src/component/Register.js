import React, { Component } from 'react'
import axios from "axios";
import './Register.css';

export default class Register extends Component{

    constructor(){
        super();
        this.state = {
                name:'',
                username:'',
                password:'',
                confirmPassword:'',
                email:'',
                companyName:''
        };
        this.handleNameChange = this.handleNameChange.bind(this);
        this.handleUsernameChange = this.handleUsernameChange.bind(this);
        this.handlePasswordChange = this.handlePasswordChange.bind(this);
        this.handleConfirmPasswordChange = this.handleConfirmPasswordChange.bind(this);
        this.handleEmailChange = this.handleEmailChange.bind(this);
        this.handleCompanyNameChange = this.handleCompanyNameChange.bind(this);  
        this.submitHandler = this.submitHandler.bind(this); 
    }

    handleNameChange(event){
        this.setState({name:event.target.value});
    }
    handleUsernameChange(event){
        this.setState({username:event.target.value});
    }
    handlePasswordChange(event){
        this.setState({password:event.target.value});
    }
    handleConfirmPasswordChange(event){
        this.setState({confirmPassword:event.target.value});
    }
    handleEmailChange(event){
        this.setState({email:event.target.value});
    }
    handleCompanyNameChange(event){
        this.setState({companyName:event.target.value});
    }
    submitHandler(event){
        event.preventDefault();
        const user={
            name : this.state.name,
            username : this.state.username,
            password : this.state.password,
            email : this.state.email,
            companyName : this.state.companyName,
        }
        
        const password = this.state.password;
        const confirmPassword = this.state.confirmPassword;
        if(password === confirmPassword){
            axios.post('http://localhost:8080/create/user',user)
            .then(response =>{
                const id = response.data.userId;
                if(response.status===200){
                    alert('User Registered with ID:'+id);
                }
            })
            .catch((err)=>{
                alert(err.response.data.message);

            });
        }
        else{
            alert('Passwords do not match');
        }
    }

    render(){
        return(
        <div class="container">  
        <h1>Register</h1>
        <div class="center">
            <form onSubmit={this.submitHandler}>
                <div class="row">
                    <div class="col">
                        <lable for="name" class="from-label pad" >Name</lable>
                        <input type="text" value={this.state.name} onChange={this.handleNameChange} class="form-control" id="name" placeholder="John Doe"/>
                    </div>
                    <div class="col">
                        <lable for="email" class="from-label pad" >Email</lable>
                        <input type="email" value={this.state.email} onChange={this.handleEmailChange} class="form-control" id="email" placeholder="johndoe@email.com"/>
                    </div>
                </div>
               
                <div class="row">
                    <div class="col">
                        <lable for="username" class="form-label pad">Username</lable>
                        <input type="text" value={this.state.username} onChange={this.handleUsernameChange}  class="form-control" id="username" placeholder="johndoe"/>
                    </div>
                </div>
                <div class="col">
                    <lable for="companyName" class="form-label pad">Company Name</lable>
                    <input type="text" value={this.state.companyName} onChange={this.handleCompanyNameChange} class="form-control" id="companyName" placeholder="Company Name"/>
                </div>
                <div class="row">
                    <div class="col">
                        <lable for="password" class="form-label pad">Password</lable>
                        <input type="password" value={this.state.password} onChange={this.handlePasswordChange} class="form-control" id="password" placeholder="Password"/>
                    </div>
                    <div class="col">
                        <lable for="confirmPassword" class="form-label pad">Confirm Password</lable>
                        <input type="password" value={this.state.confirmPassword} onChange={this.handleConfirmPasswordChange} class="form-control" id="confirmPassword" placeholder="Confirm Password"/>
                    </div>
                </div>
                <button type='submit' class="btn btn-primary">Submit</button>
            </form>
            <div id="res"></div>
        </div>
    </div>
        );
    }
}