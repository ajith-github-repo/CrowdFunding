import {SET_ALL_PROJECTS,SET_SELECTED_PROJECT,APPEND_MORE_TO_PROJECTS, ADD_PROJECT} from '../actions/projectActions'

const projectReducer = (state, action = {}) => {
    const { payload, type } = action

    switch (type) {
        case SET_ALL_PROJECTS: {

            let projectOld = {...state.project};
            projectOld.pagingInfo = {
                nextAvailable : payload.nextAvailable,
                nextResultStartsAt: payload.nextResultStartsAt
            }
            
            projectOld.projects = payload.projects.reduce((acc,project)=> {
                 acc[project.projectId] = {...project}

                 return acc;
            },{});
            
            return {
                ...state,
                project:projectOld
            }
        }
        case APPEND_MORE_TO_PROJECTS: {

            
            let projectOld = {...state.project};
            projectOld.pagingInfo = {
                nextAvailable : payload.nextAvailable,
                nextResultStartsAt: payload.nextResultStartsAt
            }
            
            let projects = {
                ...projectOld.projects,
                ...payload.projects.reduce((acc,project)=> {
                    acc[project.projectId] = {...project}
   
                    return acc;
               },{})
            };

            projectOld.projects = projects;
        
            return {
                ...state,
                project:projectOld
            }
        }
        case SET_SELECTED_PROJECT: {
            return {
                ...state,
                project:{
                    ...state.project,
                    selectedProject:payload
                }
            }
        }
        case ADD_PROJECT: {
            let oldProject = {...state.project}
            let projects = oldProject.projects;
            projects[payload.projectId] = payload;

            oldProject.projects = projects;

            return {
                ...state,
                project:oldProject
            }
        }
        default:
            return state
    }
}

export default projectReducer