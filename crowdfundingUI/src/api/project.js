import axios from 'axios';
import Constants from '../constants/constants';

import {getContentTypeHeader,constructResponse,getAuthHeader} from '../utils/apiUtil'
import {formatQueryString} from '../utils/generalUtil'

const config = new Constants().getConfig();

export async function fetchAllProjects(paginationInfo,queryInfo) {
    let response;

    let queryStringPagination = formatQueryString(paginationInfo);
    let queryStringSearch = formatQueryString(queryInfo);

    try{
       const fetchAllOpenProjectsUrl = `${config.API.BASE_URL}${config.API.PROJECT.PROJECT_SERVICE_BASE_PATH}${config.API.PROJECT.PAGINATED_SEARCH_PATH}?pagingInfo=${queryStringPagination}&query=${queryStringSearch}`
        

       response = await axios.get(fetchAllOpenProjectsUrl,{
            headers:{
                ...getContentTypeHeader()
            },
        });
    }catch(err){
        response = err.response;
        console.log(err);
    }finally{
        return constructResponse(response);
    }
}

export async function fetchProject(projectId) {
    let response;


    try{
       const fetchProjectUrl = `${config.API.BASE_URL}${config.API.PROJECT.PROJECT_SERVICE_BASE_PATH}${projectId}`
        

       response = await axios.get(fetchProjectUrl,{
            headers:{
                ...getContentTypeHeader()
            },
        });
    }catch(err){
        response = err.response;
        console.log(err);
    }finally{
        return constructResponse(response);
    }
}


export async function createProject(project) {
    let response;
    try{
       const createProjectUrl = `${config.API.BASE_URL}${config.API.PROJECT.PROJECT_SERVICE_BASE_PATH}`
        

       response = await axios.post(createProjectUrl,project,{
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


export async function makeProjectLive(projectId) {
    let response;
    try{
       const updateProjectToLiveUrl = `${config.API.BASE_URL}${config.API.PROJECT.PROJECT_SERVICE_BASE_PATH}${projectId}`
        

       response = await axios.put(updateProjectToLiveUrl,{},{
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