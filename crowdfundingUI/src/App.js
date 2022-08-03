import './App.css';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import Header from './components/Header';
import { AppStateProvider } from './contexts/AppState';
import React from 'react';
import { initialState, appReducers } from './reducers';
import Container from './components/Container';
import Alert from './components/Alert';

import {AppRoutes} from './routes/routes';

function App() {
  return (
    <AppStateProvider reducer={appReducers} initialState={initialState}>
      <Container>
        <Router>
          <Alert></Alert>
          <Header />
         <AppRoutes></AppRoutes>
        </Router>
      </Container>
    </AppStateProvider>
  );
}
export default App;
