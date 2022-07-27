export const SET_AUTHENICATED_USER="SET_AUTHENICATED_USER"
export const UNSET_AUTHENICATED_USER="UNSET_AUTHENICATED_USER"

 const setAuthenticatedUserAction = (payload) => {
    return {
      type: SET_AUTHENICATED_USER,
      payload
    };
  };
  
const unSetAuthenticatedUserAction = () => {
    return {
      type: UNSET_AUTHENICATED_USER
    };
};
  

export const setAuthenticatedUser = (dispatch)  => (currentUser) => {
    dispatch(setAuthenticatedUserAction(
      currentUser
  ))
}

export const unsetAuthenticatedUser = (dispatch)  => () => {
  dispatch(unSetAuthenticatedUserAction())
}