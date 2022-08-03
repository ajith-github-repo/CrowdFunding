import React, { useState,useEffect } from 'react';
import {debounce,values} from 'lodash'
import styles from './Header.module.css';
import Constants from '../../constants/constants';
import { useAppState } from '../../contexts/AppState'
import { Link, useNavigate } from 'react-router-dom';
import Modal from 'react-modal';
import SignIn from '../SignIn';
import SignUp from '../SignUp';
import { unsetAuthenticatedUser } from '../../actions/sessionActions';
import { removeFromLocalStorage } from '../../utils/apiUtil';
import { FaSearch } from 'react-icons/fa';
import { FaArrowDown } from 'react-icons/fa';
import { FaUserCircle } from 'react-icons/fa';
import {changeSearchBy} from '../../actions/searchActions';
import {setAllProjects} from '../../actions/projectActions';
import {setAlert} from '../../actions/alertActions';
import {fetchAllProjects} from '../../api/project';
import {useLocation} from 'react-router-dom';
import {hideSearchBox,unHideSearchBox,hideSignInModal,showSignUpModal,hideSignUpModal,showSignInModal} from '../../actions/appStateActions'

const customStyles = {
    content: {
        minWidth: '500px',
        position: 'fixed',
        width: '500px',
        top: '50%',
        left: '50%',
        right: 'auto',
        bottom: 'auto',
        margin: 'auto',
        transform: 'translate(-50%, -50%)',
        backgroundColor: '#F5F5F5',
        borderRadius: '0px',
        border: '1px',
        fontFamily: "'Libre Franklin', 'sans-serif'",
        fontSize: '14px',
        fontWeight: '300',
        padding: '50px 10px',
        zIndex: '1050',
        textAlign: 'center',
        color: '#6A6A6A',
        justifyContent: 'center',
    },

    overlay: {
        backgroundColor: 'rgba(39, 36, 36, 0.75)',
    }
};

const Header = () => {
    const constants = new Constants().getConfig();
    const [state, dispatch] = useAppState()
    const navigate = useNavigate();
    const location = useLocation();
    const dispatchChangeSearchBy = changeSearchBy(dispatch);
    const dispatchUnsetAuthenticatedUser = unsetAuthenticatedUser(dispatch);
    const dispatchSetAllProjects = setAllProjects(dispatch);
    const dispatchSetAlert = setAlert(dispatch);
    const dispatchHideSearchBox = hideSearchBox(dispatch);
    const dispatchUnHideSearchBox = unHideSearchBox(dispatch);

    const dispatchShowSignUpModal= showSignUpModal(dispatch);
    const dispatchShowSignInModal = showSignInModal(dispatch);
    const dispatchHideSignInModal = hideSignInModal(dispatch);
    const dispatchHideSignUpModal = hideSignUpModal(dispatch);

    const [dropdownHidden, setDropdownHidden] = useState(true)
    
    const currentlySelected = state.search.searchBy;
    const placeholderText = `Search by ${currentlySelected}`

    useEffect(()=>{
        if(constants.HIDE_SEARCHBAR_PAGES.some(x => x.test(location.pathname))){
             dispatchHideSearchBox();
        }else{
             dispatchUnHideSearchBox();
        }
     },[location.pathname])

    const handleModalClose = () => {
        if (!state.appState.hideSignIn) dispatchHideSignInModal()
        else dispatchHideSignUpModal()
    }

    const handleSignInOpen = () => {
        if(!state.appState.hideSignUp) dispatchHideSignUpModal();

        dispatchShowSignInModal();
    }

    const handleSignUpOpen = () => {
        if(!state.appState.hideSignIn) dispatchHideSignInModal();
        dispatchShowSignUpModal();
    }

    const handleSignInPageClick = () => {
        dispatchHideSignUpModal();
        dispatchShowSignInModal();
    }

    const handleSignUpPageClick = () => {
        dispatchHideSignInModal();
        dispatchShowSignUpModal();
    }
    const handleLinkToProfile = () => {
        navigate(constants.PATHS.PROFILE);
    }

    const handleLogout = () => {
        removeFromLocalStorage(constants.TOKEN_KEY);
        dispatchUnsetAuthenticatedUser(false, null);
        navigate(constants.PATHS.DASHBOARD);
        dispatchSetAlert(true,'Adios Amigo',constants.ALERT_TYPES.SUCCESS);
    } 

    const handleDropdown = () => {
        setDropdownHidden(!dropdownHidden)
    }

    const handleDropdownSelection = (e) => {
        dispatchChangeSearchBy(e.target.innerText)
    }

    const handleOnSearchTextChange = debounce((e) => {
        fetchAllProjects({
            maxResults:constants.PAGINATION.MAX_VALUE_DEFAULT,
            firstResult:constants.PAGINATION.FIRST_RESULT_DEFAULT
        },{
            status:constants.PROJECT_STATUS_TYPES.OPEN,
            [state.search.searchBy]:e.target.value
        }).then(resp => {
    
            if(resp.data){
                dispatchSetAllProjects(resp.data)
                if(resp.data.projects && resp.data.projects.length == 0){
                    dispatchSetAlert(
                        true,
                        'Couldnt find anything,Please refine your search',
                        constants.ALERT_TYPES.WARN
                    )
                }

            }else{
                dispatchSetAlert(
                    true,
                    resp.message,
                    constants.ALERT_TYPES.ERROR
                )
            }
        })
    },500)
    
    const handleStartProjectClick = () => {
        if(!state.session.isAuthenticated){
            dispatchHideSignUpModal();
            dispatchShowSignInModal();
        }else{
            navigate(constants.PATHS.CREATE_PROJECT)
        }
    }

    return (
        <div className={styles.header}>
            <nav className={styles.navBar}>
                <div className={styles.left}>
                    <Link to={constants.PATHS.DASHBOARD} className={styles.headingLink}><h1 className={styles.heading} data-testid="title"><span className={styles.light}>Crowd</span>Funding</h1></Link>
                    {!state.appState.hideSearchBox && <div className={styles.searchForm}>
                        <div className={styles.selectText} onClick={handleDropdown}>
                            <p>{currentlySelected}</p>
                            <FaArrowDown className={styles.dropIcon}/>
                            <ul className={dropdownHidden ? styles.ulClose : styles.ulOpen}>

                                {values(constants.SEARCH_BY_TYPES).map(text => <li key={text} onClick={handleDropdownSelection} className={styles.options}>{text}</li>)}
                            </ul>
                        </div>
                        <input placeholder={placeholderText} onChange= {handleOnSearchTextChange} className={`${styles.navSearchBar} ${styles.navSearchInput}`} ></input><FaSearch className={styles.searchIcon}></FaSearch>
                    </div>}
                </div>
                <div className={styles.right}>
                    <button className={styles.navRightBtnSpcl} onClick={handleStartProjectClick}>Start a project</button>
                    {state && !state.session.currentUser && <button className={styles.navRightBtn} onClick={handleSignInOpen}>SignIn</button>}
                    {state && !state.session.currentUser && <button className={styles.navRightBtn} onClick={handleSignUpOpen}>SignUp</button>}
                    {state && state.session.currentUser && <div onClick={handleLinkToProfile} className={styles.profileBox}><FaUserCircle></FaUserCircle><button className={`${styles.navRightBtn} ${styles.navRightBtnProfile}`} >{state.session.currentUser.firstName}</button></div>}
                    {state && state.session.currentUser && <button className={styles.navRightBtn} onClick={handleLogout}>Logout</button>}
                    {
                        <Modal
                            ariaHideApp={false}
                            isOpen={!state.appState.hideSignIn || !state.appState.hideSignUp}
                            onRequestClose={handleModalClose}
                            style={customStyles}
                            contentLabel="">
                            {!state.appState.hideSignIn && <SignIn handleSignUpPageClick={handleSignUpPageClick} handleModalClose={handleModalClose} />}
                            {!state.appState.hideSignUp && <SignUp handleSignInPageClick={handleSignInPageClick} />}
                        </Modal>}
                </div>
            </nav>
        </div>
    )
}

export default Header;
