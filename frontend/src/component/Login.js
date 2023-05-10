import React, { Component } from 'react';
import UserService from '../service/UserService';
import './Login.css';
import { Navigate } from 'react-router-dom';

export default class Login extends Component{

    constructor(props){
        super(props);
        this.state={
            username:"",
            password:"",
            success:false
        }
        this.handleUsernameChange = this.handleUsernameChange.bind(this);
        this.handlePasswordChange = this.handlePasswordChange.bind(this);
        this.submitHandler = this.submitHandler.bind(this); 
    }

    handleUsernameChange(event){
        this.setState({username:event.target.value});
    }
    handlePasswordChange(event){
        this.setState({password:event.target.value});
    }
    submitHandler(event){
        event.preventDefault();
        const login={
            username:this.state.username,
            password:this.state.password
        }
        UserService.userLogin(login)
        .then(response=>{
            if(response.status===200){
                
                localStorage.setItem("userId",response.data.userId);
                localStorage.setItem("username",response.data.username);
                localStorage.setItem("company",response.data.companyName);
                localStorage.setItem("name",response.data.name);
                localStorage.setItem("email",response.data.email);
                this.setState({success:true});
                
            }
        }).catch(err=>{
            alert(err.response.data.message);
        })

    }
    
    render(){
        if(this.state.success){
            return <Navigate to="/main"/>;
        }
        return(
            <div className='container-fluid top'>
                <div className='align'>
                <h1>Login</h1>
                <div className='center'>
                    <form onSubmit={this.submitHandler}>
                        <label className='form-label pad'>Username</label>
                        <input type='text' value={this.state.username} onChange={this.handleUsernameChange} className='form-control' />
                        <label className='form-label pad'>Password</label>
                        <input type='password' value={this.state.password} onChange={this.handlePasswordChange} className='form-control'/>
                        <button type='submit' className='btn btn-primary'>Login</button>
                    </form>
                </div>
                </div>
            </div>
        );
    }
    
}