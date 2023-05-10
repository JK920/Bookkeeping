import React ,{Component} from "react";
import axios from "axios";

class JournalService {

    addJournal(journal){
        return axios.post("http://localhost:8080/create/journal",journal);
    }
    viewJournal(userId){
        return axios.get("http://localhost:8080/get/user/"+userId+"/journals");
    }
    addJournalEntries(journalList){
        return axios.post("http://localhost:8080/add/journalentries",journalList);
    }
}
export default new JournalService()