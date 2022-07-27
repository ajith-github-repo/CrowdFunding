import {HIDE_SEARCH_BOX,UNHIDE_SEARCH_BOX,HIDE_SIGN_IN_MODAL,HIDE_SIGN_UP_MODAL,SHOW_SIGN_IN_MODAL,SHOW_SIGN_UP_MODAL} from '../actions/appStateActions'

const appStateReducer = (state, action = {}) => {
    const { payload, type } = action
    switch (type) {
        case HIDE_SEARCH_BOX: {
            return {
                ...state,
                appState: { ...state.appState, hideSearchBox:true }
            }
        }
        case UNHIDE_SEARCH_BOX: {
            return {
                ...state,
                appState: { ...state.appState, hideSearchBox:false }
            }
        }
        case HIDE_SIGN_IN_MODAL: {
            return {
                ...state,
                appState: { ...state.appState, hideSignIn:true }
            }
        }
        case SHOW_SIGN_IN_MODAL: {

            return {
                ...state,
                appState: { ...state.appState, hideSignIn:false }
            }
        }
        case HIDE_SIGN_UP_MODAL: {
            return {
                ...state,
                appState: { ...state.appState, hideSignUp:true }
            }
        }
        case SHOW_SIGN_UP_MODAL: {
            return {
                ...state,
                appState: { ...state.appState, hideSignUp:false }
            }
        }
        default:
            return state
    }
}

export default appStateReducer