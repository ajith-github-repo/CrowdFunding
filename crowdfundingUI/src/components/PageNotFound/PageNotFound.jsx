import React from 'react';
import styles from './PageNotFound.module.css';
import {useNavigate} from 'react-router-dom';
import Constants from '../../constants/constants';

const PageNotFound = () => {
    const constants = new Constants().getConfig();
    const navigate = useNavigate();
    return(
        <div >
            <h1 className={styles.heading}><span className={styles.highlight}>404 Page Not found</span></h1>
            <h4 className={styles.linkText} onClick={()=>{navigate(constants.PATHS.DASHBOARD)}}>Click here to go back </h4>
        </div >
    );
};


export default PageNotFound;
