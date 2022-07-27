import React, { useRef, useContext, useState } from 'react';
import PropTypes from 'prop-types';
import styles from './SignIn.module.css';
import { useAppState } from "../../contexts/AppState";
import Hotkeys from 'react-hot-keys';
import { useNavigate } from 'react-router-dom';
import { emailValidator, passwordValidator } from '../../utils/validationUtil';
import authDTO from '../../model/auth';
import { signIn, fetchCurrentUser } from '../../api/auth';
import { setAuthenticatedUser } from '../../actions/sessionActions';
import { setAlert } from '../../actions/alertActions';
import Constants from '../../constants/constants';

const SignIn = ({ handleSignUpPageClick, handleModalClose }) => {

    const [userEmail, setUserEmail] = useState('');
    const [password, setPassword] = useState('');
    const [displayMessage, setDisplayMessage] = useState('');
    const [state, dispatch] = useAppState();

    const dispatchSetAlert = setAlert(dispatch);
    const dispatchSetAuthenticatedUser = setAuthenticatedUser(dispatch);

    const navigate = useNavigate();
    const toggleButtonRef = useRef();

    const config = new Constants().getConfig();

    const onSubmit = async e => {
        e.preventDefault();

        if (!validateInputs(userEmail, password)) return;

        authDTO.userEmail = userEmail;
        authDTO.password = password;
  

        let res = await signIn(authDTO);

        if (res.data === null) {dispatchSetAlert(true, res.message, config.ALERT_TYPES.ERROR);return}

        let currentUserResp = await fetchCurrentUser();

        if (currentUserResp.data === null) {dispatchSetAlert( true, res.message, config.ALERT_TYPES.ERROR); return;}

        //navigate(config.PATHS.DASHBOARD);
        handleModalClose();
        dispatchSetAuthenticatedUser(currentUserResp.data);
        dispatchSetAlert( true, 'Welcome '+currentUserResp.data.firstName, config.ALERT_TYPES.SUCCESS)

    }


    const validateInputs = (userEmail, password) => {
        const isEmailValid = emailValidator(userEmail);
        if (isEmailValid) { setDisplayMessage(isEmailValid); return false }

        const isPasswordValid = passwordValidator(password);
        if (isPasswordValid) { setDisplayMessage(isPasswordValid); return false }

        return true;

    }

    const handleSignUpClick = () => {
        handleSignUpPageClick();
    }
    const onKeyUp = () => {
        toggleButtonRef.current.click();
    }

    

    return (
        //<Hotkeys keyName={constants.TOGGLE_HOT_KEY} onKeyDown={onKeyUp}> 
        <form className={styles.form} onSubmit={onSubmit}>
            <label htmlFor="email">Email*</label>
            <input
                aria-label="userEmail"
                type="text"
                name="email"
                className={`${styles.input} form-control`}
                placeholder="name@mail.com"
                required
                value={userEmail}
                onChange={e => { setUserEmail(e.target.value) }}
            />
            <label htmlFor="password">Password*</label>
            <input
                aria-label="password"
                type="password"
                name="password"
                className={`${styles.input} form-control`}
                placeholder="Min. 8 character"
                required
                value={password}
                onChange={e => { setPassword(e.target.value) }}
            />

            <button type="submit" className={styles.button}>
                SIGN IN
            </button>
            <p className={styles.newUser}>New User? <span onClick={handleSignUpClick}>Click here to SignUp</span></p>

            {displayMessage !== '' && <p className={styles.displayMessage}>{displayMessage}</p>}
        </form>
        //</Hotkeys> 
    );
};

SignIn.propTypes = {
    handleSignUpPageClick: PropTypes.func,
    handleModalClose: PropTypes.func
};

export default SignIn;
