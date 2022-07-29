import React, { useState, useEffect } from 'react';
import { useAppState } from '../../contexts/AppState'
import Container from 'react-bootstrap/Container';
import Row from 'react-bootstrap/Row';
import Col from 'react-bootstrap/Col';
import Card from 'react-bootstrap/Card';
import Tab from 'react-bootstrap/Tab';
import Tabs from 'react-bootstrap/Tabs';
import Table from 'react-bootstrap/Table';
import Constants from '../../constants/constants';
import {fetchAllUserContributions,fetchAllUserProjects} from '../../api/user';
import {setAllUserContributions,setAllUserProjects} from '../../actions/userActions'
import {setAlert} from '../../actions/alertActions';
import ProjectTileCondensed from '../ProjectTileCondensed';
import {values} from 'lodash';

const Profile = () => {
    const config = new Constants().getConfig();
    const [state, dispatch] = useAppState();
    const [tabSelection,setTabSelection] = useState('Contributions');
    const currentUser = state.session.currentUser;

    const dispatchSetAllUserContributions = setAllUserContributions(dispatch);
    const dispatchSetAllUserProjects = setAllUserProjects(dispatch);
    const dispatchSetAlert = setAlert(dispatch);

    useEffect(()=> {
        if(state.session && state.session.currentUser){
            fetchAllUserContributions(state.session.currentUser.userId).then(resp => {
                if(resp.data){
                    dispatchSetAllUserContributions(resp.data)
                }else{
                    dispatchSetAlert(true,resp.message,config.ALERT_TYPES.ERROR)
                }
            })
        }else{
            console.log('No login found, Cant fetch profile User Contribution Details');
        }
    },[])

    const handleTabChange = (e) => {
        setTabSelection(e);
    }
    useEffect(()=> {
        if(state.session && state.session.currentUser){
            fetchAllUserProjects(state.session.currentUser.userId).then(resp => {
                if(resp.data){
                    dispatchSetAllUserProjects(resp.data)
                }else{
                    dispatchSetAlert(true,resp.message,config.ALERT_TYPES.ERROR)
                }
            })
        }else{
            console.log('No login found, Cant fetch User Project Details');
        }
    },[])

    if (currentUser === null) return (<h1>Please Login</h1>)

    const imageUrl = `https://avatars.dicebear.com/api/human/${currentUser.firstName}.svg`

    const getContributions = () => {
        let contributionTiles = [];

        if(!state.user.contributions || values(state.user.contributions).length === 0) return <h4>No Contributions Found</h4>;

        contributionTiles = values(state.user.contributions).map(contribution => {
            //const projectTitle = getProjectNameFromStore(contributionId);
           return (
            <tr>
                <td>{contribution.contributionId}</td>
                <td>{contribution.contributionAmount}</td>
                <td>{contribution.contributionTime}</td>
                <td>{contribution.project.title}</td>
              </tr>
           )
        })

        return (
            <Table style ={{textAlign:'center'}} striped bordered hover>
            <thead>
              <tr>
                <th>Contribution Id</th>
                <th>Amount</th>
                <th>Date</th>
                <th>Project</th>
              </tr>
            </thead>
            <tbody>
                {contributionTiles}
            </tbody>
          </Table>
        );
    }

    const getPendingProjects = () => {
       
        let projectTiles = [];

        if(!state.user.projects) return <h4>No Projects Found</h4>;

        projectTiles = values(state.user.projects).filter(x => x.status === config.PROJECT_STATUS_TYPES.PENDING).map(project => {
            return ( 
                <ProjectTileCondensed key={project.projectId} project={project}></ProjectTileCondensed>
            )
         })

         return projectTiles.length > 0 ? projectTiles : (  <h4>No Projects Found</h4>);
    }

    const getAllButPendingProjects = () => {
       
        let projectTiles = [];

        if(!state.user.projects) return <h4>No Projects Found</h4>;

        projectTiles = values(state.user.projects).filter(x => x.status !== config.PROJECT_STATUS_TYPES.PENDING).map(project => {
            return ( 
                <ProjectTileCondensed key={project.projectId} project={project}></ProjectTileCondensed>
            )
         })
         return projectTiles;
    }


    let contributionsStr = ''
    let projectsStr = ''
    if(state.user.contributions) contributionsStr = values(state.user.contributions).length + (values(state.user.contributions).length > 1 ? ` Contributions` : ' Contribution')
    if(state.user.projects) projectsStr = values(state.user.projects).length + (values(state.user.projects).length > 1 ? ` Owned Projects` : ' Owned Project')
    return (
        <Container>
            <Row>
                <Col>
                    <Card style={{ width: '18rem', marginTop: '40px', paddingTop: '10px' }}>
                        <Card.Img style={{ width: '100px', height: '100px', borderRadius: '50%', alignSelf: 'center' }} variant="top" src={imageUrl} />
                        <Card.Body>
                            <Card.Title style={{ textAlign: 'center' }}>{currentUser.firstName} {currentUser.lastName}</Card.Title>
                            <Card.Text style={{ textAlign: 'center' }}>
                                {currentUser.userEmail}
                            </Card.Text>
                            <Card.Text style={{ textAlign: 'center' }}>
                                {contributionsStr}
                            </Card.Text>
                            <Card.Text style={{ textAlign: 'center' }}>
                                {projectsStr}
                            </Card.Text>
                        </Card.Body>
                    </Card>
                </Col>
                <Col xs={9}><Tabs
                    defaultActiveKey={tabSelection}
                    id="fill-tab-example"
                    className="mb-3"
                    fill
                    variant="pills"
                    onSelect={handleTabChange}
                    style={{ marginTop:'40px'}}
                >
                    <Tab  eventKey="Contributions" title="Contributions" >
                        <div style={{width:'100%',display:'flex',flexWrap:'wrap'}}> {getContributions()} </div>
                    </Tab>
                    <Tab  eventKey="Pending_Projects" title="Owned Projects - Not Live" >
                    <div style={{width:'100%',display:'flex',flexWrap:'wrap'}}>{getPendingProjects()}</div>
                    </Tab>
                    <Tab  eventKey="AllOtherProjects" title="Owned Projects - Others">
                    <div style={{width:'100%',display:'flex',flexWrap:'wrap'}}> {getAllButPendingProjects()}</div>
                    </Tab>
                </Tabs></Col>
            </Row>
        </Container>
    );


    
};

export default Profile;
