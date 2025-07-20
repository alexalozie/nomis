import * as ACTION_TYPES from '../actions/types'

const initialState = {
  houseHoldList: [],
  householdServiceHistory:[],
  household: {},
  householdMembers: [],
  dueServices: [],
  dueGraduation: []
}

const houseHoldReducer = (state = initialState, action) => {
  switch (action.type) {
   
    case ACTION_TYPES.FETCH_HOUSE_HOLD:
      return { ...state, houseHoldList: [...action.payload] }

    case ACTION_TYPES.FETCH_HOUSEHOLD_SERVICE_HISTORY:
        return { ...state, householdServiceHistory: [...action.payload] }

    case ACTION_TYPES.FETCH_HOUSE_HOLD_BY_ID:
      return { ...state, household: action.payload }

    case ACTION_TYPES.FETCH_HOUSEHOLD_MEMBERS_BY_HOUSEHOLD_ID:
      return { ...state, householdMembers: action.payload }

    case ACTION_TYPES.GET_HOUSEHOLD_SERVICE_DUE_DATE:
    return {...state, dueServices: [...action.payload] }

    case ACTION_TYPES.GET_HOUSEHOLD_DUE_FOR_GRADUATION:
      return {...state, dueGraduation: [...action.payload] }
    default:
      return state
  }
}

export default houseHoldReducer


