import React, {Fragment}from 'react';
import { useEffect } from 'react';
import {useAppState} from '../../contexts/AppState';
import {getFromLocalStorage,removeFromLocalStorage} from '../../utils/apiUtil';
import {fetchCurrentUser} from '../../api/auth';
import {setAuthenticatedUser,unsetAuthenticatedUser} from '../../actions/sessionActions';
import Constants from '../../constants/constants';

function Container({children}){
    const[state,dispatch] = useAppState();
    const config = new Constants().getConfig();
    const dispatchSetAuthenticatedUser = setAuthenticatedUser(dispatch);
    const dispatchUnsetAuthenticatedUser = unsetAuthenticatedUser(dispatch);

    useEffect(()=>{
        if(getFromLocalStorage(config.TOKEN_KEY) && state.session.currentUser == null){
            fetchCurrentUser().then(resp => {
              
                if(resp.data) dispatchSetAuthenticatedUser(resp.data);
                else  {removeFromLocalStorage(config.TOKEN_KEY);dispatchUnsetAuthenticatedUser();};
            })
        }
    },[]);

    return <Fragment >
        {children}
    </Fragment>

}

export default Container

