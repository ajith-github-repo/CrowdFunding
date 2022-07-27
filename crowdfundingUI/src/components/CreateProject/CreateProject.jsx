import React, { Fragment,useState} from 'react';
import styles from './CreateProject.module.css';
import {useAppState} from '../../contexts/AppState'
import {setAlert} from '../../actions/alertActions';
import {createProject} from '../../api/project';
import {addProject,setSelectedProject} from '../../actions/projectActions';
import projectDTO from '../../model/project'
import {amountValidator,descriptionValidator,expiryDateValidator,taglineValidator,titleValidator,tagsValidator} from '../../utils/validationUtil';
import { useNavigate } from 'react-router-dom';

import Constants from '../../constants/constants';



const CreateProject = () => {
    const [state,dispatch] = useAppState();
    const constants = new Constants().getConfig();
    const [displayMessage, setDisplayMessage] = useState('');
    const dispatchSetAlert = setAlert(dispatch);
    const dispatchAddProject = addProject(dispatch);
    const dispatchSetSelectedProject = setSelectedProject(dispatch);
    const navigate = useNavigate();

    const [title, setTitle] = useState('');
    const [description, setDescription] = useState('');
    const [tagline, setTagline] = useState('');
    const [expireDate, setExpireDate] = useState();
    const [amountRequested, setAmountRequested] = useState(0);
    const [tags, setTags] = useState('');
    const [startFundingProject,setStartFundingProject] = useState(false);
    
    const validateInputs = (title, description,tagline,expireDate,amountRequested,tags) => {
        
        const isTitleValid = titleValidator(title);
        if (isTitleValid) { setDisplayMessage(isTitleValid); return false }

        const isDescriptionValid = descriptionValidator(description);
        if (isDescriptionValid) { setDisplayMessage(isDescriptionValid); return false }
        
        const isTaglineValid = taglineValidator(tagline);
        if (isTaglineValid) { setDisplayMessage(isTaglineValid); return false }

        const isExpireDateValid = expiryDateValidator(expireDate);
        if (isExpireDateValid) { setDisplayMessage(isExpireDateValid); return false }

        const isAmountRequestedValid = amountValidator(amountRequested);
        if (isAmountRequestedValid) { setDisplayMessage(isAmountRequestedValid); return false }

        const isTagsValid = tagsValidator(tags);
        if (isTagsValid) { setDisplayMessage(isTagsValid); return false }

        return true;

    }
    
    const onSubmit = (e) => { 
        e.preventDefault();
        setDisplayMessage('');
        if (!validateInputs(title, description,tagline,expireDate,amountRequested,tags)) return;

        projectDTO.title = title;
        projectDTO.description = description;
        projectDTO.amountRequested = amountRequested;
        projectDTO.expireDate = expireDate;
        projectDTO.tags = tags.split(",");
        projectDTO.tagline = tagline;
        projectDTO.startGettingFunded = startFundingProject;

        createProject(projectDTO).then(resp => {
            
            if(resp.data){
                dispatchSetAlert(true,`Project ${resp.data.title} Created Successfully`,constants.ALERT_TYPES.SUCCESS)
                dispatchAddProject(resp.data)
                dispatchSetSelectedProject(resp.data);
                navigate(`${constants.PATHS.PROJECT_DETAILED}${resp.data.projectId}`)
            }else{
                dispatchSetAlert(true,resp.message,constants.ALERT_TYPES.ERROR)
            }
        })
    }
    
    return (
        <div style={{display:'flex',alignItems:'center',justifyContent:'center'}}>
 <form className={styles.form} onSubmit={onSubmit}>
            <label htmlFor="title">Title*</label>
            <input
                aria-label="title"
                type="text"
                name="title"
                className={`${styles.input} form-control`}
                placeholder="Skippi Pops"
                required
                value={title}
                onChange={e => { setTitle(e.target.value) }}
            />
            <label htmlFor="tagline">Tagline*</label>
            <input
                aria-label="tagline"
                type="text"
                name="tagline"
                className={`${styles.input} form-control`}
                placeholder="Ice Pops for everyone at a reasonable price"
                required
                value={tagline}
                onChange={e => { setTagline(e.target.value) }}
            />
            <div className={styles.inputGroup}>
            <div >
            <label htmlFor="expireDate">ExpireDate*</label>
            <input
                aria-label="expireDate"
                type="date"
                name="expireDate"
                className={`${styles.input} form-control`}
                placeholder="name@mail.com"
                required
                value={expireDate}
                onChange={e => { setExpireDate(e.target.value) }}
            />
            </div>
            <div>
            <label htmlFor="amountRequested">AmountNeeded*</label>
            <input
                aria-label="amountRequested"
                type="number"
                name="amountRequested"
                className={`${styles.input} form-control`}
                placeholder="In INR"
                required
                value={amountRequested}
                onChange={e => { setAmountRequested(e.target.value) }}
            />
            </div>
            <div className={styles.checkBoxDiv}>
            <label htmlFor="startFundingProject">Start Funding the Project</label>
            <input
                aria-label="startFundingProject"
                type="checkbox"
                name="startFundingProject"
                className={`${styles.input} ${styles.inputCheckBox}`}
                placeholder="name@mail.com"
                value={startFundingProject}
                onChange={e => { setStartFundingProject(e.target.checked) }}
            />
            </div>
            </div>
            
            <label htmlFor="tags">Tags*</label>
            <input
                aria-label="tags"
                type="text"
                name="tags"
                className={`${styles.input} form-control`}
                placeholder="add tags seperated by comma like ice,pops,summer"
                required
                value={tags}
                onChange={e => { setTags(e.target.value) }}
            />
            <label htmlFor="description">Description*</label>
            <textarea
                aria-label="description"
                type="text"
                name="description"
                className={`${styles.input} form-control ${styles.descriptionInput}`}
                placeholder="Describe who you are and why you need funding"
                required
                value={description}
                onChange={e => { setDescription(e.target.value) }}
                cols="30"
                 rows="8"
            
            />

            <button type="submit" className={styles.button}>
                CREATE
            </button>
            {displayMessage !== '' && <p className={styles.displayMessage}>{displayMessage}</p>}
        </form>
        </div>
       
    );
};

export default CreateProject;
