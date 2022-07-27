import { Navigate } from "react-router-dom";
import {useAppState} from '../contexts/AppState';
import Constants from '../constants/constants';

const Protected = ({ children }) => {
    const constants = new Constants().getConfig();
    const[state,dispatch] = useAppState();

    if(!state.session.isAuthenticated) {return <Navigate to={constants.PATH.DASHBOARD} replace />}
    else {console.log(state.session.isAuthenticated)};
 return children;
};
export default Protected;
