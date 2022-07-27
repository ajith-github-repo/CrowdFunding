import axios from 'axios';
import Constants from '../constants/constants';

import {getContentTypeHeader,constructResponse,getAuthHeader} from '../utils/apiUtil'

const config = new Constants().getConfig();

export async function contributeToProject(data) {
    let response;

    try{
       const contributeToProjectUrl = `${config.API.BASE_URL}${config.API.PAYMENT.PAYMENT_SERVICE_BASE_PATH}`
        

       response = await axios.post(contributeToProjectUrl,data,{
            headers:{
                ...getContentTypeHeader(),
                ...getAuthHeader()
            },
        });
    }catch(err){
        response = err.response;
        console.log(err);
    }finally{
        return constructResponse(response);
    }
}