import React, { createContext, useReducer, useContext } from 'react'
import { object, func } from 'prop-types'

export const Context = createContext()

export function AppStateProvider({ reducer, initialState = {}, children }) {
  const value = useReducer(reducer, initialState)

  return (
    <Context.Provider value={value}>
      {children}
    </Context.Provider>
  )
  }

export function useAppState() {
  return useContext(Context)
}


AppStateProvider.propTypes = {
  reducer: func,
  initialState: object,
}