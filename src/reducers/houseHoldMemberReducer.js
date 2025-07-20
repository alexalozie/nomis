import * as ACTION_TYPES from '../actions/types'

const initialState = {
  list: [],
  members: [],
  errorMessage: [],
  member: {},
  serviceHistory: []

}

const houseHoldMemberReducer = (state = initialState, action) => {
  switch (action.type) {
    case ACTION_TYPES.HOUSE_HOLD_MEMBER_CREATE_SERVICE:
      return { ...state, list: action.payload }
    
      case ACTION_TYPES.ERROR_HOUSE_HOLD_MEMBER_CREATE_SERVICE:
      return { ...state, errorMessage: action.payload}

    case ACTION_TYPES.FETCH_HOUSE_HOLD_MEMBER:
      return { ...state, members: [...action.payload] }

    case ACTION_TYPES.FETCH_HOUSE_HOLD_MEMBER_BY_ID:
      return { ...state, member: action.payload}

    case ACTION_TYPES.FETCH_HOUSEHOLD_MEMBER_SERVICE_HISTORY:
      return {...state, serviceHistory: action.payload}

    default:
      return state
  }
}

export default houseHoldMemberReducer


