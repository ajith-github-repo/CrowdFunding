import { BrowserRouter as Router,MemoryRouter} from 'react-router-dom';
import React from "react";
import { initialState, appReducers } from '../../reducers';
import {AppStateProvider} from '../../contexts/AppState'
import { render} from "@testing-library/react";

export function renderWithNormalRouterAndProvider(
    ui,
    appState
  ) {
   const screen =  render(
        <AppStateProvider reducer={appReducers} initialState={appState ? appState : initialState}>
        <Router>
           {ui}
        </Router>
        </AppStateProvider>
    );
    return screen;
  }

export function renderWithProvider(
    ui,
    appState
  ) {
   const screen =  render(
        <AppStateProvider reducer={appReducers} initialState={appState ? appState : initialState}>
           {ui}
        </AppStateProvider>
    );
    return screen;
  }

  export function renderWithMemoryRouterAndProvider(
    ui,
    appState,
    initialRouterEntries
  ) {
   const screen =  render(
        <AppStateProvider reducer={appReducers} initialState={appState ? appState : initialState}>
        <MemoryRouter initialEntries={initialRouterEntries ? initialRouterEntries : ["/"]}>
           {ui}
        </MemoryRouter>
        </AppStateProvider>
    );
    return screen;
  }