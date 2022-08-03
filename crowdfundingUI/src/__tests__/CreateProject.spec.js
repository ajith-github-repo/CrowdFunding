
import React from "react";
import { fireEvent, render, screen, waitFor } from "@testing-library/react";
import "@testing-library/jest-dom";
import { renderWithNormalRouterAndProvider } from './helper/renderWith'
import CreateProject from '../components/CreateProject';
import Constants from '../constants/constants';

describe("Create Project Component Tests", () => {
 
    const constants = new Constants().getConfig();

  test("renders all elements", () => {
    const initialState = {
        session: {
          isAuthenticated: true,
          currentUser: {
            userId: 152,
            userEmail: "ajith@gmail.com",
            lastName: "kumar",
            firstName: "Ajith",
            projectsFunded: null,
            projectsOwned: null,
            contributions: null
          },
        },
        alert: {
          alertSeverity: constants.ALERT_TYPES.WARN,
          showAlert: false,
          alertMessage: '',
        },
        project: {
          selectedProject: null,
          pagingInfo: {
            nextAvailable: true,
            nextResultStartsAt: 0
          },
          projects: {}
        },
        search: {
          searchBy: constants.SEARCH_BY_TYPES.DESCRIPTION,
        },
        appState: {
          hideSearchBox: false,
          hideSignIn: true,
          hideSignUp: true
        },
        user: {
          contributions: {},
          projects: {}
        }
      }
    const screen = renderWithNormalRouterAndProvider(<CreateProject></CreateProject>)

    expect(screen.getByLabelText(/Title/i)).toBeInTheDocument();
    expect(screen.getByLabelText(/Tagline/i)).toBeInTheDocument();
    expect(screen.getByLabelText(/ExpireDate/i)).toBeInTheDocument();
    expect(screen.getByLabelText(/amountRequested/i)).toBeInTheDocument();
    expect(screen.getByLabelText(/startFundingProject/i)).toBeInTheDocument();
    expect(screen.getByLabelText(/Tags/i)).toBeInTheDocument();
    expect(screen.getByLabelText(/Description/i)).toBeInTheDocument();
    expect(screen.getByRole("button",{name:/create/i})).toBeInTheDocument();
    expect(screen.getByRole("button",{name:/create/i})).toBeEnabled();
  })


})


