import Constants from '../constants/constants';
import sessionReducer from './SessionReducer';
import alertReducer from './AlertReducer';
import projectReducer from './ProjectReducer';
import searchReducer from './SearchReducer';
import appStateReducer from './AppStateReducer';
import userReducer from './UserReducer';

const constants = new Constants().getConfig();

const initialState = {
    session:{
        isAuthenticated:false,
        currentUser:null,
      },
    alert:{
        alertSeverity:constants.ALERT_TYPES.WARN,
        showAlert:false,
        alertMessage:'',
      },
    project:{
       selectedProject:null,
       pagingInfo:{
           nextAvailable : true,
           nextResultStartsAt:0
       },
       projects:{}
    },
    search:{
      searchBy:constants.SEARCH_BY_TYPES.DESCRIPTION,
    },
    appState:{
      hideSearchBox:false,
      hideSignIn:true,
      hideSignUp:true
    },
    user:{
      contributions:{},
      projects:{}
    }
  }
  
const combineReducers = reducers => {
    return (state, action) => {
      return Object.keys(reducers).reduce(
        (acc, prop) => {
          return ({
            ...acc,
            ...reducers[prop]({ [prop]: acc[prop] }, action),
          })
        },
        state
      )
    }
  }
  
  const appReducers = combineReducers({
    session: sessionReducer,
    alert: alertReducer,
    project: projectReducer,
    search: searchReducer,
    appState: appStateReducer,
    user:userReducer
  })


  export { initialState, appReducers }