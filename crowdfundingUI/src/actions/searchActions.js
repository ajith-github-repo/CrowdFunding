export const CHANGE_SEARCH_BY="CHANGE_SEARCH_BY"


const changeSearchByAction = (searchBy) => {
    return {
      type: CHANGE_SEARCH_BY,
      payload:searchBy
    };
  };
  
  
export const changeSearchBy = (dispatch) => (searchBy) => {
    dispatch(changeSearchByAction(searchBy))
  }
  