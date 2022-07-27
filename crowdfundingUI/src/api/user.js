import axios from 'axios';
import Constants from '../constants/constants';

import {getContentTypeHeader,constructResponse,getAuthHeader} from '../utils/apiUtil'

const config = new Constants().getConfig();

export async function fetchAllUserProjects(userId) {
    let response;
    try{
        const path = config.API.USER.ALL_USER_PROJECTS_PATH.replace("{userId}",userId);
       const fetchAllUserProjectsUrl = `${config.API.BASE_URL}${config.API.USER.USER_SERVICE_BASE_PATH}${path}`
        

       response = await axios.get(fetchAllUserProjectsUrl,{
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

export async function fetchAllUserContributions(userId) {
    let response;


    try{
       const path = config.API.USER.ALL_USER_CONTRIBUTIONS_PATH.replace("{userId}",userId);
       const fetchProjectUrl = `${config.API.BASE_URL}${config.API.USER.USER_SERVICE_BASE_PATH}${path}`
        

       response = await axios.get(fetchProjectUrl,{
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
