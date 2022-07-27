import React, { Fragment } from 'react'
import styles from './Alert.module.css';
import {useAppState} from '../../contexts/AppState';
import { FaWindowClose } from 'react-icons/fa';
import Constants from '../../constants/constants';
import {setAlert} from '../../actions/alertActions';

const Alert = () => {
    const constants = new Constants().getConfig();
    const [state,dispatch] = useAppState();
    const dispatchSetAlert = setAlert(dispatch);

    const getClassOfAlert = (alertType) =>{
       let alertClass = 'warn'
       if(alertType === constants.ALERT_TYPES.ERROR) {alertClass = 'error'}
       else if(alertType === constants.ALERT_TYPES.WARN) alertClass = 'warn'
       else alertClass = 'success'

       return alertClass;
    }


    const handleAlertClose = () =>{
        dispatchSetAlert(false,'',constants.ALERT_TYPES.WARN);
    }

    if(state.alert && !state.alert.showAlert) return (<Fragment></Fragment>);

    setTimeout(()=> {
        dispatchSetAlert(false,'',constants.ALERT_TYPES.WARN);
    },5000)
    const alertClass = getClassOfAlert(state.alert.alertSeverity);

    return (<div className={`${styles.alertContainer} ${styles[alertClass]}`} >
     <p className={styles.message}>{state.alert.alertMessage}</p>
     <FaWindowClose onClick={handleAlertClose} className={styles.closeIcon}></FaWindowClose>
 </div>);


}

export default Alert;
