
import React, { Fragment } from "react";
import { fireEvent,waitFor,act} from "@testing-library/react";
import "@testing-library/jest-dom";
import { renderWithNormalRouterAndProvider,renderWithMemoryRouterAndProvider } from './helper/renderWith'
import Header from '../components/Header';
import Constants from '../constants/constants';
import { AppRoutes } from '../routes/routes'
import userEvent from '@testing-library/user-event'

jest.mock('axios');

import axios from 'axios'



beforeAll(() => {
  axios.get.mockResolvedValue({
    data:[],
    status:200,
    message:''
  });

});

afterAll(() => {
  jest.clearAllMocks();
});

describe("Header Component", () => {
  const constants = new Constants().getConfig();


  test("renders all elements", () => {
    const screen = renderWithNormalRouterAndProvider(<Header></Header>)


    expect(screen.getByRole("button", { name: /SignIn/i })).toBeInTheDocument();
    expect(screen.getByRole("button", { name: /SignUp/i })).toBeInTheDocument();
    expect(screen.getByRole("button", { name: /START A PROJECT/i })).toBeInTheDocument();

    expect(screen.getByRole("textbox")).toBeInTheDocument();
    expect(screen.getAllByRole("heading").length).toBe(2);
    expect(screen.getAllByRole("heading").map(ele => ele.textContent)).toStrictEqual(["CF","CrowdFunding"]);
    
    const ul = screen.getByTestId('searchDropdown');

    expect(ul).toBeInTheDocument();

    const descriptionli = screen.getAllByRole('listitem').find(listitem => listitem.textContent === 'description')
    const tagsli = screen.getAllByRole('listitem').find(listitem => listitem.textContent === 'tags')
    const taglineli = screen.getAllByRole('listitem').find(listitem => listitem.textContent === 'tagline')
    const titleli = screen.getAllByRole('listitem').find(listitem => listitem.textContent === 'title')

    expect(descriptionli).toBeInTheDocument();
    expect(tagsli).toBeInTheDocument();
    expect(taglineli).toBeInTheDocument();
    expect(titleli).toBeInTheDocument();
  })

  test("signIn button click renders signIn Modal", () => {
    const screen = renderWithNormalRouterAndProvider(<Header></Header>)

    const signInButton = screen.getByRole("button", { name: /SignIn/i });

    fireEvent.click(signInButton);


    expect(screen.getByLabelText(/Email/i)).toBeInTheDocument();
    expect(screen.getByLabelText(/Password/i)).toBeInTheDocument();

  })

  test("signUp button click renders signUp Modal", () => {
    const screen = renderWithNormalRouterAndProvider(<Header></Header>)

    const signUpButton = screen.getByRole("button", { name: /SignUp/i });

    fireEvent.click(signUpButton);

    expect(screen.getByLabelText(/Firstname/i)).toBeInTheDocument();
  })

  test("Start  a project click takes to project/create page", async () => {


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


    const initialRouterEntries = ['/profile'];
    const screen = renderWithMemoryRouterAndProvider(<Fragment><Header></Header> <AppRoutes></AppRoutes></Fragment>, initialState,initialRouterEntries)

    const startProjectButton = screen.getByRole("button", { name: /START A PROJECT/i });
    userEvent.click(startProjectButton);

    await waitFor(() => expect(screen.getByLabelText(/description/i)).toBeInTheDocument());
  })

  test("Click on Crowdfunding link in header , routes to Dashboard", async () => {

    

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

    const initialRouterEntries = ['/project/create'];
    const screen = renderWithMemoryRouterAndProvider(<Fragment><Header></Header> <AppRoutes></AppRoutes></Fragment>, initialState,initialRouterEntries)

    const crowdFundingHeading = screen.getAllByRole("heading")[0];

    
    userEvent.click(crowdFundingHeading);
    await waitFor(() => expect(screen.getByText(/thats all for now/i)).toBeInTheDocument());
  })
})


