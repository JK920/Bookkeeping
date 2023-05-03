import React, { Component } from 'react'
import './Register.css';

export default class Register extends Component{

    render(){
        return(
        <div class="container">  
        <h1>Register</h1>
        <div class="center">
            <form d>
                <div class="row">
                    <div class="col">
                        <lable for="name" class="from-label pad" >Name</lable>
                        <input type="text" class="form-control" id="name" placeholder="John Doe"/>
                    </div>
                    <div class="col">
                        <lable for="email" class="from-label pad" >Email</lable>
                        <input type="email" class="form-control" id="email" placeholder="johndoe@email.com"/>
                    </div>
                </div>
               
                <div class="row">
                    <div class="col">
                        <lable for="phone" class="from-label pad" >Phone</lable>
                        <input type="tel" class="form-control" id="phone" placeholder="Phone Number"/>
                    </div>
                    <div class="col">
                        <lable for="username" class="form-label pad">Username</lable>
                        <input type="text"  class="form-control" id="username" placeholder="johndoe"/>
                    </div>
                </div>
                <div class="col">
                    <lable for="companyName" class="form-label pad">Company Name</lable>
                    <input type="text"  class="form-control" id="companyName" placeholder="Company Name"/>
                </div>
                <div class="row">
                    <div class="col">
                        <lable for="password" class="form-label pad">Password</lable>
                        <input type="password"  class="form-control" id="password" placeholder="Password"/>
                    </div>
                    <div class="col">
                        <lable for="confirmPassword" class="form-label pad">Confirm Password</lable>
                        <input type="password"  class="form-control" id="confirmPassword" placeholder="Confirm Password"/>
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