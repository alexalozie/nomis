import * as ACTION_TYPES from '../actions/types'

const initialState = {
    cboList: [],

}

const cboReducer = (state = initialState, action) => {
  switch (action.type) {
    case ACTION_TYPES.FETCH_ALL_CBO:
      return { ...state, cboList: action.payload }
    
      
    default:
      return state
  }
}

export default cboReducer


