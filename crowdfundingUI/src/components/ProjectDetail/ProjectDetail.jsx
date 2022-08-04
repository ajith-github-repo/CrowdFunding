import React, { Fragment,useContext, useEffect, useState} from 'react';
import {useAppState} from '../../contexts/AppState';
import {useLocation} from 'react-router-dom';
import {setSelectedProject,addProject} from '../../actions/projectActions';
import {fetchProject,makeProjectLive} from '../../api/project';
import {setAlert} from '../../actions/alertActions';
import styles from './ProjectDetail.module.css';
import {values} from 'lodash';
import {getPercentage} from '../../utils/generalUtil';
import {getDifferenceBetweenDays,getDisplayDate} from '../../utils/dateUtil';
import {showSignInModal} from '../../actions/appStateActions';
import {contributeToProject} from '../../api/payments'
import Constants from '../../constants/constants';
import {addNewUserProject,addNewUserContribution} from '../../actions/userActions';

const ProjectDetail = () => {
    const [state,dispatch] = useAppState();
    const constants = new Constants().getConfig();
    const projectId = useLocation().pathname.split(constants.PATHS.PROJECT_DETAILED)[1];
    const [contributionAmt,setContributionAmt] = useState(0);
    const dispatchSetSelectedProject = setSelectedProject(dispatch);
    const dispatchSetAlert = setAlert(dispatch);
    const dispatchShowSignInModal = showSignInModal(dispatch);
    const dispatchAddProject = addProject(dispatch);
    const dispatchAddNewUserProject = addNewUserProject(dispatch);
    const dispatchAddNewUserContribution = addNewUserContribution(dispatch);

    const owner = state.session.currentUser && state.project.selectedProject && state.session.currentUser.userId === state.project.selectedProject.innovator.userId;
    const projectStatus = state.project.selectedProject && state.project.selectedProject.status;

    let disableButton = true;
    let message = '';
    if(projectStatus === constants.PROJECT_STATUS_TYPES.ARCHIVED){ 
      disableButton = true;
      message = 'Target Achieved';
    }else if(projectStatus === constants.PROJECT_STATUS_TYPES.CLOSE){
      disableButton = true;
      message = 'Closed for funding';
    }else{
        message = 'Make it Live';
        if(owner) disableButton = false;
    }

    
    useEffect(() => {
        
        const projects = state.project.projects;

        if(values(projects).length === 0 || (projectId && !projects[projectId])){
            //fetch project
            fetchProject(projectId).then(resp => {
                if(resp.data){
                    dispatchSetSelectedProject(resp.data);
                    dispatchAddProject(resp.data);
                }else{

                    dispatchSetAlert(true,resp.message,constants.ALERT_TYPES.ERROR)
                }
            })
        }else{
            dispatchSetSelectedProject(projects[projectId]);
        }
    },[])

    const handleContribute = (e) => {
        e.preventDefault();
        if(!state.session.isAuthenticated){
            dispatchShowSignInModal();
        }else{
            //VALIDATE
            if(parseInt(contributionAmt) <= 0) {dispatchSetAlert(true,'Please Enter a valid amount',constants.ALERT_TYPES.ERROR); return;};

            contributeToProject({
                amount:parseInt(contributionAmt),
                payeeId:state.project.selectedProject.projectId ,
                payerId:state.session.currentUser.userId, 
            }).then(resp => {
                if(!resp.data) dispatchSetAlert(true,'Some Issue With Contribution,'+resp.message,constants.ALERT_TYPES.ERROR);
                setContributionAmt(0);
                dispatchAddNewUserContribution(resp.data);
                dispatchSetAlert(true,`Thank you ${state.session.currentUser.firstName} for your valuable contribution`,constants.ALERT_TYPES.SUCCESS);
                fetchProject(state.project.selectedProject.projectId).then(response => {
                    if(response.data){
                        dispatchSetSelectedProject(response.data);
                        dispatchAddNewUserProject(response.data);
                        dispatchAddProject(response.data);
                    }else{
                        dispatchSetAlert(true,'Coulnot update project details '+resp.message,constants.ALERT_TYPES.ERROR);
                    }
                })
            })
        }
    }

    const handleMoveProjectToLive = (e) => {
      e.preventDefault();
      if(!state.session.isAuthenticated){
          dispatchShowSignInModal();
      }else{
          //VALIDATE

          if(state.project.selectedProject && !state.project.selectedProject.projectId){
             dispatchSetAlert(true,'Invalid data, Please check',constants.ALERT_TYPES.ERROR);
             return;
          }

          makeProjectLive(state.project.selectedProject.projectId).then(resp => {
              if(!resp.data) {dispatchSetAlert(true,'Issue With Updating Project status '+resp.message,constants.ALERT_TYPES.ERROR); return;};
              dispatchSetSelectedProject(resp.data);
              dispatchAddProject(resp.data);
              dispatchAddNewUserProject(resp.data);
              dispatchSetAlert(true,'Project Made Live Successfully',constants.ALERT_TYPES.SUCCESS);
          })
      }
  }
    const renderDescription = (description) => {
        return (
          <div className={styles.projectOverview}>
            <div className={styles.overviewHeader}>
              <div className={styles.overviewTitle}>Overview</div>
            </div>
            <div className={styles.overviewContent}>{description}</div>
          </div>
        );
      }


    const renderImageBox = (image_url) => {
        return (
          <div className={styles.mediaBox}>
            <img src={`${image_url}`} />
          </div>
        );
      }

      
     const renderProjectSummary = (project) => {
      if(!project) return <h1>Empty</h1>
        return (
          <div className={styles.projectSummaryDiv}>
            <div className={styles.projectSummaryCont}>
              {renderImageBox(project.imageUrl)}
              <div className={styles.projShowHeader}>
                <div className={styles.projectTitle}>{project.title}</div>
                <div className={styles.projectTagline}>{project.tagLine}</div>
                 {renderInnovatorBox(project.innovator)}
                {renderProjectProgress(project)}
                {renderProjectDaysLeft(project)}
                {project.status === constants.PROJECT_STATUS_TYPES.OPEN ? renderContributionReady() : renderContributionNotReady()}
              </div>
            </div>
          </div>
        );
      }

    const renderContributionNotReady = () => {
       
        

        return (
          <div className={styles.contributeSection}>
            <div className={styles.contributionAction}>
              <div className={styles.contrNonActive}>
                <button
                  className={`${styles.openContrBtn} ${styles.submitBtn}`}
                  disabled={disableButton}
                  onClick={handleMoveProjectToLive}>
                  {message}
                </button>
              </div>
            </div>
          </div>
        );
      }
    
    const renderContributionReady = () => {
        return (
          <div className={styles.contributeSection}>
            <div className={styles.contributionAction}>
              <div className={styles.contrActive}>
                <form className={styles.contributeForm} onSubmit={handleContribute}>
                  <input
                    className={`${styles.textField} ${styles.contrInput}`}
                    type="text"
                    min="1.00"
                    pattern="^\d+([,.][0-9]{1,2})?$"
                    onChange={(e)=>{setContributionAmt(e.target.value)}}
                    placeholder="Donation Amount"
                    value={contributionAmt}
                  />
                  <input
                    className={`${styles.submitBtn} ${styles.cntrBtn}`}
                    type="submit"
                    value="CONTRIBUTE"
                  />
                </form>
              </div>
            </div>
          </div>
        );
      }

      const renderProjectProgress = (project) => {
        if(!project) return <h1>Empty</h1>
        const percentage = getPercentage(project.amountCollected , project.amountRequested);
        const contributorNum = project.noOfFunders + "";
        const contributorStr = contributorNum === "1" ? "contributor" : "contributors";
    
        return (
          <div className={styles.projectProgress}>
            <div className={styles.progressRaised}>
              <span className={styles.totalAmount}>
                {project.amountCollected}{" "}
              </span>
              INR
              <span>
                {" "}
                raised by {project.noOfFunders} {contributorStr}
              </span>
            </div>
            <div className={styles.projectGoalBar}>
              <div
                className={styles.projectProgBar}
                style={{ width: `${percentage}px`, maxWidth: "100%" }}
              />
            </div>  
            <div className={styles.projectProgressDetails}>
              <em className={styles.percentage}>{percentage}%</em> of {project.amountRequested} INR
            </div>
          </div>
        );
      }

      const renderProjectDaysLeft = (project) => {
        if(!project) return <h1>Empty</h1>
        const daysLeft = getDifferenceBetweenDays(project.creationDate , project.expireDate);;
        const startedOn = getDisplayDate(project.creationDate)
    
        return (
          <div className={styles.projectDays}>
            <div className={styles.projectDaysProgress}>
              <span >
                Started On 
              </span>
              <span> </span>
              <span className={styles.startedOn}>
                {startedOn}
              </span>
            </div>
            <div className={styles.projectDaysLeft}>
              {daysLeft} Days Left
            </div>
          </div>
        );
      }
    
      const renderInnovatorBox = (innovator) => {
        const imageUrl = `https://avatars.dicebear.com/api/human/${innovator.firstName}.svg`
        return (
          <div className={styles.projectInnovator}>
            <img src={`${imageUrl}`} className={styles.innovatorPic}/>
            <div className={styles.innovatorDetails}>
              <div className={styles.detailsName}>
                {innovator.firstName} {innovator.lastName}
              </div>
              India
            </div>
          </div>
        );
      }

      const renderTags = (tagStr) => {
        const tags = tagStr.split(",");
        const tagsArr = tags.map(tag => <div key={tag} className={styles.tag}>{tag}</div>)
        return (
              <div className={styles.tags}>
                {tagsArr}
              </div>

          );
      }

    if(state.project.selectedProject === null) return (<h1>Project Not Found</h1>)
    return (
        <div className={styles.showProjectDiv}>
          {renderProjectSummary(state.project.selectedProject)}
          <div className={styles.showProject}>
            <div className={styles.bodyLeadSection}>
                {renderDescription(state.project.selectedProject.description)}
            </div>
            <div className={styles.bodyFinalSection}>
              <div className={styles.tagsList}>
                <div className={styles.tagsTitle}>TAGS</div>
                {renderTags(state.project.selectedProject.tags)}
              </div>
            </div>
          </div>
        </div>
        )
};

export default ProjectDetail;
