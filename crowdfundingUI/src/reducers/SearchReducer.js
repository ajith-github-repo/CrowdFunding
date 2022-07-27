import {CHANGE_SEARCH_BY} from '../actions/searchActions'

const searchReducer = (state, action = {}) => {
    const { payload, type } = action
    switch (type) {
        case CHANGE_SEARCH_BY: {
            return {
                ...state,
                search:{
                    ...state.search,
                    searchBy:payload
                }
            }
        }
        default:
            return state
    }
}

export default searchReducer