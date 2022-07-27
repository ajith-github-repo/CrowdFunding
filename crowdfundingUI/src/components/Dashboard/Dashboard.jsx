import React, { Fragment, useContext, useEffect } from 'react';
import styles from './Dashboard.module.css';
import ProjectTile from '../ProjectTile';
import ScrollButton from '../ScrollButton/ScrollButton';
import {fetchAllProjects} from '../../api/project';
import {useAppState} from '../../contexts/AppState';
import {appendMoreToProjects} from '../../actions/projectActions'
import {setAlert} from '../../actions/alertActions'
import { constant, values } from 'lodash';
import Constants from '../../constants/constants';

const Dashboard = () => {
    const [state,dispatch] = useAppState();
    const constants = new Constants().getConfig();
    const dispatchAppendMoreToProjects = appendMoreToProjects(dispatch);
    const dispatchSetAlert = setAlert(dispatch);

    useEffect(()=> {

        if(values(state.project.projects).length === 0 && state.project.pagingInfo.nextAvailable){
            fetchAllProjects({
                maxResults:10,
                firstResult:state.project.pagingInfo.nextResultStartsAt
            },{
                status:'OPEN'
            }).then(resp => {
                if(resp.data){
                    dispatchAppendMoreToProjects(resp.data)

                }else{
                    
                }
            })
        }
        
    },[])

    let tiles = [];
    let showFetchMore = false;
    if(state.project) {
        tiles = getAllTiles(values(state.project.projects))
        showFetchMore = state.project.pagingInfo.nextAvailable;
    };
    

    const handleFetchMore = () => {
  
        fetchAllProjects({
            maxResults:constants.PAGINATION.MAX_VALUE_DEFAULT,
            firstResult:state.project ? state.project.pagingInfo.nextResultStartsAt : constants.PAGINATION.FIRST_RESULT_DEFAULT
        },{
            status:constants.PROJECT_STATUS_TYPES.OPEN
        }).then(resp => {
            if(resp.data){
                dispatchAppendMoreToProjects(resp.data)
       
            }else{
                dispatchSetAlert({
                    showAlert:true,
                    alertMessage:resp.message,
                    alertSeverity:''
                })
            }
        })

    }


    return (
        
            <div className={styles.homepageDiscovery}>
                <div className={styles.campsDiscoverBar}>
                    <ul className={styles.projects}>{tiles}</ul>
                </div>
                <div className={styles.fetchMore}>
                    {showFetchMore && <button className={styles.fetchMoreButton} onClick={handleFetchMore}>Fetch More</button>}
                    {!showFetchMore && <h3 className={styles.thatsall}>Thats All for now.</h3>}
                    <ScrollButton></ScrollButton>
                </div>
            </div>
      
    );
};

function getAllTiles (projects){
    let tilesArr = [];
    return projects.map(project =>
        (<ProjectTile key={project.projectId} project={project} />)
      );
}

export default Dashboard;
