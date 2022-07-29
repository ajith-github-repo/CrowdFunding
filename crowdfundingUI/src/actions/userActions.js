export const SET_ALL_USER_CONTRIBUTIONS="SET_ALL_USER_CONTRIBUTIONS"
export const SET_ALL_USER_PROJECTS = 'SET_ALL_USER_PROJECTS';
export const ADD_NEW_USER_CONTRIBUTION = 'ADD_NEW_USER_CONTRIBUTION';
export const ADD_NEW_USER_PROJECT = 'ADD_NEW_USER_PROJECT';

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
  
const addNewUserContributionAction = (payload) => {
    return {
      type: ADD_NEW_USER_CONTRIBUTION,
      payload
    };
};

const addNewUserProjectAction = (payload) => {
    return {
      type: ADD_NEW_USER_PROJECT,
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

export const addNewUserContribution = (dispatch)  => (contribution) => {

  dispatch(addNewUserContributionAction(
    contribution
))
}

export const addNewUserProject = (dispatch)  => (project) => {

  dispatch(addNewUserProjectAction(
    project
))
}