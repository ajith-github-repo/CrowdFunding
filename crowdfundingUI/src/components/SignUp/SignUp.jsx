import React, { useRef, useState } from 'react';
import PropTypes from 'prop-types';
import styles from './SignUp.module.css';
import {useAppState} from "../../contexts/AppState";
import Hotkeys from 'react-hot-keys';
import {emailValidator,passwordValidator,nameValidator} from '../../utils/validationUtil';
import signUpDTO from '../../model/signUp';
import {signUp} from '../../api/auth';
import Constants from '../../constants/constants';
import { setAlert } from '../../actions/alertActions';


const SignUp = ({handleSignInPageClick} ) => {

    const config = new Constants().getConfig();
    const [userEmail, setUserEmail] = useState('');
    const [password, setPassword] = useState('');
    const [firstname, setFirstname] = useState('');
    const [lastname, setLastname] = useState('');
    const [displayMessage, setDisplayMessage] = useState('');

    const [ state,dispatch ] = useAppState();

    const toggleButtonRef = useRef();
    const dispatchSetAlert = setAlert(dispatch);


    const handleSignInClick = () => {
        handleSignInPageClick();
    }
    const onSubmit = async e => {
        e.preventDefault();
        if(!validateInputs(userEmail,password,firstname,lastname)) return;

        signUpDTO.userEmail = userEmail;
        signUpDTO.password = password;
        signUpDTO.firstName = firstname;
        signUpDTO.lastName = lastname;

        let resp = await signUp(signUpDTO);


        if(resp.status !== 201) {dispatchSetAlert(true,resp.message,config.ALERT_TYPES.ERROR) ;return;}

        dispatchSetAlert(true,'Successfully Signed Up, Please Sign In',config.ALERT_TYPES.SUCCESS)
        handleSignInClick();
            
    };

    const validateInputs = (userEmail,password,firstname,lastname) => {
        const isFirstNameValid = nameValidator(firstname);
        if (isFirstNameValid) { setDisplayMessage(isFirstNameValid); return false}

        const isLastNameValid = nameValidator(lastname);
        if (isLastNameValid) { setDisplayMessage(isLastNameValid); return false}

        const isEmailValid = emailValidator(userEmail);
        if (isEmailValid) { setDisplayMessage(isEmailValid); return false}

        const isPasswordValid = passwordValidator(password);
        if (isPasswordValid) { setDisplayMessage(isPasswordValid); return false}

        return true;

    }

    const onKeyUp = () => {
        toggleButtonRef.current.click();
    }

    return (
        //<Hotkeys keyName={constants.TOGGLE_HOT_KEY} onKeyDown={onKeyUp}> 
        //</Hotkeys> 

        <form className={styles.form} onSubmit={onSubmit}>
            <label htmlFor="firstname">Firstname*</label>
            <input
                aria-label="firstname"
                type="text"
                name="firstname"
                className={`${styles.input} form-control`}
                placeholder="Rahul"
                required
                value={firstname}
                onChange={e => { setFirstname(e.target.value) }}
            />
            <label htmlFor="lastname">Lastname*</label>
            <input
                aria-label="lastname"
                type="text"
                name="lastname"
                className={`${styles.input} form-control`}
                placeholder="Dravid"
                required
                value={lastname}
                onChange={e => { setLastname(e.target.value) }}
            />
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
                SIGN UP
            </button>
            <p className={styles.alreadyUser}>Already a User? <span onClick={handleSignInClick}>Click here to SignIn</span></p>
            {displayMessage !== '' && <p className={styles.displayMessage}>{displayMessage}</p>}
        </form>
    );
};


SignUp.propTypes = {
    handleSignInPageClick: PropTypes.func,
};
export default SignUp;
