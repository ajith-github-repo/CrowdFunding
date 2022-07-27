export const SET_ALERT="SET_ALERT"
export const UNSET_ALERT="UNSET_ALERT"


const setAlertAction = (alert) => {
    return {
      type: SET_ALERT,
      payload:alert
    };
  };
  
const unSetAlertAction = () => {
    return {
      type: UNSET_ALERT,
    };
};
  
export const setAlert = (dispatch) => (showAlert, alertMessage, alertSeverity) => {
    dispatch(setAlertAction({
          showAlert,
          alertMessage,
          alertSeverity
    }))
  }
  
export const unSetAlert = (dispatch) => () => {
    dispatch(unSetAlertAction())
  }
  