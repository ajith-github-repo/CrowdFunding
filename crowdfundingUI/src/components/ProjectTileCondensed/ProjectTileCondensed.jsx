import React from 'react';
import { Link } from 'react-router-dom';
import styles from './ProjectTileCondensed.module.css';
import {getDifferenceBetweenDays} from '../../utils/dateUtil';
import {getPercentage} from '../../utils/generalUtil';
import Card from 'react-bootstrap/Card';
import Constants from '../../constants/constants';

const ProjectTileCondensed = ({ project }) => {

    const progress = getPercentage(project.amountCollected , project.amountRequested);
    const diff = getDifferenceBetweenDays(project.creationDate,project.expireDate);
    const constants = new Constants().getConfig();
    let headerStyle = project.status.toLowerCase();

    return (
        
        <Card
          bg={'light'}
          key={'Light'}
          text={'dark'}
          style={{ width: '18rem' , marginRight:'10px'}}
          className="mb-2"
        >
          <Link className={styles.tileLink} to={`${constants.PATHS.PROJECT_DETAILED}${project.projectId}`}>
          <Card.Header className={`${styles[headerStyle]}`}>{`Status : ${project.status}`}</Card.Header>
          <Card.Body>
          
            <Card.Title style={{fontWeight:600}}>{project.title}</Card.Title>
            <Card.Text style={{fontSize:12}}>
              {project.tagLine}
            </Card.Text>
            <Card.Text>
               Current {project.amountCollected} INR
            </Card.Text>
            <Card.Text>
               Target {project.amountRequested} INR
            </Card.Text>
            
          </Card.Body>
          </Link>
        </Card>
    
    )
};

export default ProjectTileCondensed;
