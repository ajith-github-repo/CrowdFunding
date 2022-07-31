import React, { Fragment, useContext } from 'react';
import { Link } from 'react-router-dom';
import styles from './ProjectTile.module.css';
import {getDifferenceBetweenDays} from '../../utils/dateUtil';
import {getPercentage} from '../../utils/generalUtil';
import Constants from '../../constants/constants';

const ProjectTile = ({ project }) => {
    const constants = new Constants().getConfig();
    const progress = getPercentage(project.amountCollected , project.amountRequested);
    const diff = getDifferenceBetweenDays(project.creationDate,project.expireDate);
    return (
        <div className={styles.projTile}>
         
                <Link className={styles.tileLink} to={`${constants.PATHS.PROJECT_DETAILED}${project.projectId}`}>
                    <div className={styles.imageWrapper}>
                        <div className={styles.cardImage} style={{ backgroundImage: `url(${project.imageUrl})` }}></div>
                    </div>
                    <div className={styles.tileInfo}>
                        <div className={styles.tileTitleTagline}>
                            <div>{project.title}</div>
                            <div>{project.tagline}</div>
                        </div>
                        <div className={styles.tileBottom}>
                            <span>{project.amountCollected}</span>
                            &nbsp;
                            <span className={styles.inr}>INR Collected</span>
                            <div className={styles.progressBar} style={{ width: `${progress}%`, maxWidth: '100%' }}></div>
                            <div className={styles.progressLabel}>
                                <div className="tilePercent">{progress}%</div>
                                <div>{diff} days left</div>
                            </div>
                        </div>
                    </div>
                </Link>
           
        </div>
    );
};

export default ProjectTile;
