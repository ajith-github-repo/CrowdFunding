import './App.css';
import PageNotFound from './components/PageNotFound';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import Dashboard from './components/Dashboard';
import Header from './components/Header';
import { AppStateProvider } from './contexts/AppState';
import React from 'react';
import { initialState, appReducers } from './reducers';
import Container from './components/Container';
import Profile from './components/Profile';
import Protected from './components/Protected';
import ProjectDetail from './components/ProjectDetail';
import CreateProject from './components/CreateProject';
import Alert from './components/Alert';
import Constants from './constants/constants';

function App() {

  const constants = new Constants().getConfig();

  return (
    <AppStateProvider reducer={appReducers} initialState={initialState}>
      <Container>
        <Router>
          <Alert></Alert>
          <Header />
          <Routes>
            <Route exact path={constants.PATHS.PROFILE} element={
             <Protected>
              <Profile />
             </Protected>
          } 
            />
            <Route path={`${constants.PATHS.PROJECT_DETAILED_WITH_ID}`} element={<ProjectDetail />} />
            <Route path={constants.PATHS.CREATE_PROJECT} element={<Protected><CreateProject /></Protected>} />
            <Route exact path={constants.PATHS.DASHBOARD} element={<Dashboard />} />
            <Route path={constants.PATHS.ALL} element={<PageNotFound />} />
          </Routes>
        </Router>
      </Container>
    </AppStateProvider>
  );
}
export default App;
