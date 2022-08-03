

import React from "react";
import {  Routes, Route } from 'react-router-dom';
import Profile from '../components/Profile';
import Protected from '../components/Protected';
import ProjectDetail from '../components/ProjectDetail';
import CreateProject from '../components/CreateProject';
import PageNotFound from '../components/PageNotFound';
import Dashboard from '../components/Dashboard';
import Constants from '../constants/constants';

export const AppRoutes = () => {
    const constants = new Constants().getConfig();
    return (
    <Routes>
            <Route exact path={constants.PATHS.PROFILE} element={
             <Protected>
              <Profile />
             </Protected>
          } 
            />
            <Route path={constants.PATHS.PROJECT_DETAILED_WITH_ID} element={<ProjectDetail />} />
            <Route path={constants.PATHS.CREATE_PROJECT} element={<Protected><CreateProject /></Protected>} />
            <Route exact path={constants.PATHS.DASHBOARD} element={<Dashboard />} />
            <Route path={constants.PATHS.ALL} element={<PageNotFound />} />
    </Routes>
    );
}