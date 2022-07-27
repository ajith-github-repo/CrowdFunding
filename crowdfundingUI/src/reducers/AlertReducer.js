import {SET_ALERT,UNSET_ALERT} from '../actions/alertActions'
import Constants from '../constants/constants';

const constants = new Constants().getConfig();

const alertReducer = (state, action = {}) => {
    const { payload, type } = action
    switch (type) {
        case SET_ALERT: {
            return {
                ...state,
                alert: { ...state.alert, ...payload }
            }
        }
        case UNSET_ALERT: {
            return {
                ...state,
                alert: {
                    ...state.alert,
                    alertSeverity: constants.ALERT_TYPES.WARN,
                    showAlert: false,
                    alertMessage: '',
                }
            }
        }
        default:
            return state
    }
}

export default alertReducer