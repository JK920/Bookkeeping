import React, { Component } from "react";

export default class Help extends Component{
    render(){
        return(
            <div className="container-fluid">
                <div className="row my-5" style={{textAlign:'left',lineHeight:'2rem'}}>
                    <h3>Getting Started Step By Step</h3>
                    <p>
                        Step 1 : Register and login
                        <br/>
                        Step 2 : Dashboard will contain basic details about your business like payables,receivables and income.
                        <br/>
                        Step 3 : In side menu there are 5 sections : Dashboard,Accounts,Customers,Vendors,Journal.
                        <br/>
                        Step 4 : In Accounts section you can view your 'Chart Of Accounts', add new accounts and set opening balances.
                                Best practice is to set all opening balances after creaating required accounts.
                        <br/>
                        Step 5 : In Customers section you will be able to add, view and modify customer details. Invoices generated for the customer can be added and viewed in this sections. The invoices added will generate corresponding journals which can be viewed in the Journals Section
                        <br/>
                        Step 6 : The Venodrs section is similar to customers section. Here the bills from vendors can be added and maintained. The bills added will generate Journals which can be viewed in the Journal Section.
                        <br/>
                        Step 7 : The Journal Section provides options to view Journal entries, add manual entries and view the General Ledger for your business.
                        <br/>
                        Step 8 : To edit your password you can navigate to Profile tab on the top.
                    </p>
                </div>
            </div>
        );
    }
}