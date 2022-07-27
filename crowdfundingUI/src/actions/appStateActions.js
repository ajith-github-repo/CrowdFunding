export const HIDE_SEARCH_BOX="HIDE_SEARCH_BOX"
export const UNHIDE_SEARCH_BOX="UNHIDE_SEARCH_BOX"

export const SHOW_SIGN_IN_MODAL="SHOW_SIGN_IN_MODAL"
export const HIDE_SIGN_IN_MODAL="HIDE_SIGN_IN_MODAL"
export const SHOW_SIGN_UP_MODAL="SHOW_SIGN_UP_MODAL"
export const HIDE_SIGN_UP_MODAL="HIDE_SIGN_UP_MODAL"

const hideSignInModalAction = (alert) => {
    return {
      type: HIDE_SIGN_IN_MODAL
    };
};

const showSignInModalAction = () => {

    return {
      type: SHOW_SIGN_IN_MODAL,
    };
};

const hideSignUpModalAction = () => {
    return {
      type: HIDE_SIGN_UP_MODAL,
    };
};

const showSignUpModalAction = () => {
    return {
      type: SHOW_SIGN_UP_MODAL
    };
};

const hideSearchBoxAction = () => {
    return {
      type: HIDE_SEARCH_BOX,
    };
};
  
const unHideSearchBoxAction = () => {
    return {
      type: UNHIDE_SEARCH_BOX,
    };
};
  
export const hideSearchBox = (dispatch) => () => {
    dispatch(hideSearchBoxAction())
  }
  
export const unHideSearchBox = (dispatch) => () => {
    dispatch(unHideSearchBoxAction())
}




export const hideSignInModal = (dispatch) => () => {
    dispatch(hideSignInModalAction())
  }
  
export const showSignInModal = (dispatch) => () => {
    dispatch(showSignInModalAction())
}

export const hideSignUpModal = (dispatch) => () => {
    dispatch(hideSignUpModalAction())
  }
  
export const showSignUpModal = (dispatch) => () => {
    dispatch(showSignUpModalAction())
}
  