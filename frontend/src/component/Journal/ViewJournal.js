import React,{Component} from "react";
import JournalService from "../../service/JournalService";

export default class ViewJournal extends Component{

    constructor(){
        super();
        this.state = {
            journals:[]
        };
    }

    componentDidMount(){
        JournalService.viewJournal(localStorage.getItem("userId"))
        .then(res => {
            this.setState({journals:res.data});
        }).catch(err=>{
            console.log(err.response.data.message);
        })
    }
    render(){
        return(
            <div className="container-fluid">
                
                <table className="table">
                    <thead>
                        <tr>
                            <th scope="col">Journal Id</th>
                            <th scope="col">Date</th>
                            <th scope="col">Account</th>
                            <th scope="col">Reference</th>
                            <th scope="col">Description</th>
                            <th scope="col">Debit</th>
                            <th scope="col">Credit</th>
                        </tr>
                    </thead>
                    <tbody>
                        {this.state.journals.map(journal=>{
                            console.log(journal);
                            return(
                                <tr>
                                    <td>{journal.journalId}</td>
                                    <td>{journal.date}</td>
                                    <td>{journal.accountId}</td>
                                    <td>{journal.reference}</td>
                                    <td>{journal.description}</td>
                                    <td>{journal.debit}</td>
                                    <td>{journal.credit}</td>
                                </tr>
                            )
                        })

                        }
                        
                    </tbody>
                </table>
            </div>
        );
    }
}