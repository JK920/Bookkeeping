import React, { Component } from 'react'
import './Register.css';
import Navbar from './Navbar';
import UserService from '../service/UserService';
import { Navigate } from 'react-router-dom';

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
            success: false
        }
        
        const password = this.state.password;
        const confirmPassword = this.state.confirmPassword;
        if(password === confirmPassword){
            UserService.registerUser(user)
            .then(response =>{
                const id = response.data.userId;
                if(response.status===200){
                    alert('User Registered with ID:'+id);
                    document.getElementById('res').innerHTML='You are Registered';
                    this.setState({success:true})
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
        if(this.state.success){
            return <Navigate to="/login"/>;
        }
        return(
        <div className="container-fluid top">
        <div className="align">
        <h1>Register</h1>
        <div className="center">
            <form id='signup' onSubmit={this.submitHandler} >
                <div className="row">
                    <div className="col">
                        <label htmlFor="name" className="from-label pad" >Name</label>
                        <input type="text" value={this.state.name} onChange={this.handleNameChange} className="form-control" id="name" required placeholder="John Doe"/>
                    </div>
                    <div className="col">
                        <label htmlFor="email" className="from-label pad" >Email</label>
                        <input type="email" value={this.state.email} onChange={this.handleEmailChange} className="form-control" id="email" required placeholder="johndoe@email.com"/>
                    </div>
                </div>
               
                <div className="row">
                    <div className="col">
                        <label htmlFor="username" className="form-label pad">Username</label>
                        <input type="text" value={this.state.username} onChange={this.handleUsernameChange}  className="form-control" id="username" required placeholder="johndoe"/>
                    </div>
                </div>
                <div className="col">
                    <label htmlFor="companyName" className="form-label pad">Company Name</label>
                    <input type="text" value={this.state.companyName} onChange={this.handleCompanyNameChange} className="form-control" id="companyName" required placeholder="Company Name"/>
                </div>
                <div className="row">
                    <div className="col">
                        <label htmlFor="password" className="form-label pad">Password</label>
                        <input type="password" value={this.state.password} onChange={this.handlePasswordChange} className="form-control" id="password" required placeholder="Password"/>
                    </div>
                    <div className="col">
                        <label htmlFor="confirmPassword" className="form-label pad">Confirm Password</label>
                        <input type="password" value={this.state.confirmPassword} onChange={this.handleConfirmPasswordChange} className="form-control" required id="confirmPassword" placeholder="Confirm Password"/>
                    </div>
                </div>
                <button type='submit' className="btn btn-primary">Submit</button>
            </form>
        </div>
            <div id="res"></div>
        </div>
    </div>
        );
    }
}