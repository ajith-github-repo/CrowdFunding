const getAuthHeader = () =>{

    const token = getFromLocalStorage('token');

    return {
        'Authorization':`Bearer ${token}`
    }
}

const getContentTypeHeader = () =>{
    return {
        'Content-Type': 'application/json'
    }
}


const storeInLocalStorage = (key,value) => {
    localStorage.setItem(key, value);
}

const removeFromLocalStorage = (key) => {
    localStorage.removeItem(key);
}

const getFromLocalStorage = (key) => {
    return localStorage.getItem(key);
}

const constructResponse = (constructResponse) => {
  let resp = {};
  if(constructResponse != null){ 

    resp.data = null;
    if(constructResponse.status === 500){
        resp.status = 500;
        resp.message ='Server Error, Please Try after sometime';
    }else if(constructResponse.status === 401){
        resp.status = 401;
        resp.message ='Authentication Error, Details provided doesnt exist in database';
    }else if(constructResponse.status === 409){
        resp.status = 409;
        resp.message ='There is an conflict, Object Already Exists';
    }else if(constructResponse.status === 206){
        resp.status = 206;
        resp.message = constructResponse.data;
    }
    else{
        resp = {
            status:constructResponse.status,
            data : constructResponse.data,
            message:constructResponse.statusText
        }; 
    }

    
  }else{
    resp = {
        status:null,
        data : null,
        message:null
    }; 
  }

  return resp;
}

export {getAuthHeader,storeInLocalStorage,getFromLocalStorage,constructResponse,removeFromLocalStorage,getContentTypeHeader};