import React ,{Component} from "react";
import axios from "axios";

class VendorService extends Component{

    viewVendors(userId){
        return axios.get("http://localhost:8080/get/user/"+userId+"/vendors");
    }

    modifyVendor(venId,ven){
        return axios.put("http://localhost:8080/update/user/vendors/"+venId,ven);
    }

    addVendor(ven){
        return axios.post("http://localhost:8080/create/vendor",ven);
    }
}
export default new VendorService()