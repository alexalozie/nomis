import * as ACTION_TYPES from '../actions/types'

const initialState = {
    donorList: [],

}

const donorReducer = (state = initialState, action) => {
  switch (action.type) {
    case ACTION_TYPES.FETCH_ALL_DONOR:
      return { ...state, donorList: action.payload }
    
      
    default:
      return state
  }
}

export default donorReducer


