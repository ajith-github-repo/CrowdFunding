import {ADD_NEW_CONTRIBUTION,ADD_NEW_PROJECT,SET_ALL_USER_CONTRIBUTIONS,SET_ALL_USER_PROJECTS} from '../actions/userActions'

const userReducer = (state, action = {}) => {
    const { payload, type } = action
    switch (type) {
        case ADD_NEW_CONTRIBUTION: {
            let contributionsOld = state.user.contributions
            contributionsOld[payload.contributionId] = payload;
            return {
                ...state,
                user: { ...state.user, contributions: contributionsOld }
            }
        }
        case ADD_NEW_PROJECT: {
            let projectsOld = state.user.projects
            projectsOld[payload.projectId] = payload;
            return {
                ...state,
                user: { ...state.user, projects: projectsOld }
            }
        }
        case SET_ALL_USER_CONTRIBUTIONS: {
            let contributionsNew = payload.reduce((acc,contribution)=> {
                acc[contribution.contributionId] = {...contribution}

                return acc;
           },{});
            return {
                ...state,
                user: {
                    ...state.user,
                    contributions:contributionsNew
                }
            }
        }
        case SET_ALL_USER_PROJECTS: {

            let projectsNew = payload.reduce((acc,project)=> {
                acc[project.projectId] = {...project}

                return acc;
           },{});
            return {
                ...state,
                user: {
                    ...state.user,
                    projects:projectsNew
                }
            }
        }
        default:
            return state
    }
}

export default userReducer