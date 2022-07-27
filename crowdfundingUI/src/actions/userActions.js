export const SET_ALL_USER_CONTRIBUTIONS="SET_ALL_USER_CONTRIBUTIONS"
export const SET_ALL_USER_PROJECTS = 'SET_ALL_USER_PROJECTS';
export const ADD_NEW_CONTRIBUTION = 'ADD_NEW_CONTRIBUTION';
export const ADD_NEW_PROJECT = 'ADD_NEW_PROJECT';

const setAllUserContributionsAction = (payload) => {
    return {
      type: SET_ALL_USER_CONTRIBUTIONS,
      payload
    };
  };

const setAllUserProjectsAction = (payload) => {
    return {
      type: SET_ALL_USER_PROJECTS,
      payload
    };
  };
  
const addNewContributionAction = (payload) => {
    return {
      type: ADD_NEW_CONTRIBUTION,
      payload
    };
};

const addNewProjectAction = (payload) => {
    return {
      type: ADD_NEW_PROJECT,
      payload
    };
};
  

export const setAllUserContributions = (dispatch)  => (contributions) => {
  dispatch(setAllUserContributionsAction(
    contributions
))
}

export const setAllUserProjects = (dispatch)  => (projects) => {
  dispatch(setAllUserProjectsAction(
    projects
))
}

export const addNewContribution = (dispatch)  => (contribution) => {

  dispatch(addNewContributionAction(
    contribution
))
}

export const addNewProject = (dispatch)  => (project) => {

  dispatch(addNewProjectAction(
    project
))
}