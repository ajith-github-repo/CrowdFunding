import {SET_AUTHENICATED_USER,UNSET_AUTHENICATED_USER} from '../actions/sessionActions'

const sessionReducer = (state, action = {}) => {
    const { payload, type } = action
    switch (type) {
        case SET_AUTHENICATED_USER: {
            return {
                ...state,
                session: { ...state.session, isAuthenticated: true, currentUser : payload }
            }
        }
        case UNSET_AUTHENICATED_USER: {
            return {
                ...state,
                session: {
                    ...state.session,
                    isAuthenticated: false,
                    currentUser: null
                }
            }
        }
        default:
            return state
    }
}

export default sessionReducer