export const SET_ALL_PROJECTS="SET_ALL_PROJECTS"
export const SET_SELECTED_PROJECT = 'SET_SELECTED_PROJECT';
export const ADD_PROJECT = 'ADD_PROJECT';
export const APPEND_MORE_TO_PROJECTS = 'APPEND_MORE_TO_PROJECTS';

const setAllProjectsAction = (payload) => {
    return {
      type: SET_ALL_PROJECTS,
      payload
    };
  };

const appendMoreToProjectsAction = (payload) => {
    return {
      type: APPEND_MORE_TO_PROJECTS,
      payload
    };
  };
  
const setSelectedProjectAction = (payload) => {
    return {
      type: SET_SELECTED_PROJECT,
      payload
    };
};

const addProjectAction = (payload) => {
    return {
      type: ADD_PROJECT,
      payload
    };
};
  

export const setAllProjects = (dispatch)  => (projects) => {
  dispatch(setAllProjectsAction(
    projects
))
}

export const appendMoreToProjects = (dispatch)  => (projects) => {
  dispatch(appendMoreToProjectsAction(
    projects
))
}

export const setSelectedProject = (dispatch)  => (project) => {

  dispatch(setSelectedProjectAction(
    project
))
}

export const addProject = (dispatch)  => (project) => {

  dispatch(addProjectAction(
    project
))
}