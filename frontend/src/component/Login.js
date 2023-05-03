import React, { Component } from 'react'
import './Login.css';

export default class Login extends Component{

    render(){
        return(
            <div className='container'>
                <h1>Login</h1>
                <div className='center'>
                    <form>
                        <label className='from-label pad'>Username</label>
                        <input type='text' className='form-control' />
                        <label className='from-label pad'>Password</label>
                        <input type='password' className='form-control'/>
                        <button type='submit' className='btn btn-primary'>Login</button>
                    </form>
                </div>
            </div>
        );
    }
    
}