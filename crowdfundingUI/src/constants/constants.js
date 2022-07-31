function Constants() {
    var constants = {
       ALERT_TYPES:{
          SUCCESS:'SUCCESS',
          WARN:'WARN',
          ERROR:'ERROR'
       },
       SEARCH_BY_TYPES:{
        DESCRIPTION:'description',
        TAGS:'tags',
        TITLE:'title',
        TAGLINE:'tagline'
       },
       PROJECT_STATUS_TYPES:{
        PENDING:'PENDING',
        OPEN:'OPEN',
        ARCHIVED:'ARCHIVED',
        CLOSE:'CLOSE'
     },
     PAGINATION:{
         MAX_VALUE_DEFAULT:10,
         FIRST_RESULT_DEFAULT:0
     },
     PATHS:{
        PROFILE:'/profile',
        DASHBOARD :'/',
        CREATE_PROJECT:'/project/create',
        PROJECT_DETAILED:'/projects/',
        PROJECT_DETAILED_WITH_ID:"/projects/:projectId",
        ALL:"*"
     },
     POST:"post",
     GET:"get",
     PUT:"put",
     TOKEN_KEY:"token",
     HIDE_SEARCHBAR_PAGES : ["/projects","/profile","/project/create"],
       API:{
         BASE_URL:process.env.REACT_APP_BASE_URL,
         AUTH:{
            AUTH_SERVICE_BASE_PATH:process.env.REACT_APP_AUTH_SERVICE_BASE_PATH,
            SIGN_IN_PATH:process.env.REACT_APP_SIGN_IN_PATH,
            SIGN_UP_PATH:process.env.REACT_APP_SIGN_UP_PATH,
        },
        USER:{
            USER_SERVICE_BASE_PATH:process.env.REACT_APP_USER_SERVICE_BASE_PATH,
            ALL_USER_CONTRIBUTIONS_PATH:process.env.REACT_APP_ALL_USER_CONTRIBUTIONS_PATH,
            ALL_USER_PROJECTS_PATH:process.env.REACT_APP_ALL_USER_PROJECTS_PATH
        },
        PROJECT:{
            PROJECT_SERVICE_BASE_PATH:process.env.REACT_APP_PROJECT_SERVICE_BASE_PATH,
            PAGINATED_SEARCH_PATH:process.env.REACT_APP_PAGINATED_SEARCH,
        },
        PAYMENT:{
            PAYMENT_SERVICE_BASE_PATH:process.env.REACT_APP_PAYMENT_SERVICE_BASE_PATH,
        }
       },
    }
    this.getConfig = function() { return constants; }
}


export default Constants;