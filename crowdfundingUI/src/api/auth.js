import axios from 'axios';
import Constants from '../constants/constants';

import {getAuthHeader,storeInLocalStorage,constructResponse,getContentTypeHeader} from '../utils/apiUtil'
const config = new Constants().getConfig();

export async function signIn(auth) {
        let response;
        try{
            const signInUrl = config.API.BASE_URL+config.API.AUTH.AUTH_SERVICE_BASE_PATH+config.API.AUTH.SIGN_IN_PATH
        
            response = await axios({
                url: signInUrl,
                method: config.POST,
                headers: {
                    ...getContentTypeHeader(),
                },
                data:auth
            })

            const token = response.headers['authorization']
            if(!token) return false;
            
            storeInLocalStorage(config.TOKEN_KEY, token.split(" ")[1].trim());

        }catch(err){
            response = err.response;
            console.log(err);
        }finally{
            
            return constructResponse(response);
        }
        

}

export async function fetchCurrentUser() {
    let response;
    try{
       const fetchCurrentUserInfoUrl = `${config.API.BASE_URL}${config.API.USER.USER_SERVICE_BASE_PATH}${config.API.USER.CURRENT_LOGGEDIN_USER_PATH}`
        
       response = await axios.get(fetchCurrentUserInfoUrl,{
            headers:{
                ...getAuthHeader()
            }
        });
    }catch(err){
        response = err.response;
        console.log(err);
    }finally{
        return constructResponse(response);
    }
    

}

export async function signUp(auth) {
    let response;
    try{
        const signUpUrl = `${config.API.BASE_URL}${config.API.AUTH.AUTH_SERVICE_BASE_PATH}${config.API.AUTH.SIGN_UP_PATH}`

        response = await axios({
            url: signUpUrl,
            method: config.POST,
            timeout: 8000,
            headers: {
                ...getContentTypeHeader(),
            },
            data:auth
        })
    }catch(err){
        response = err.response;
        console.log(err);
    }finally{
        return constructResponse(response);
    }
    

}