import * as ACTION_TYPES from '../actions/types'

const initialState = {
    codesetList: [],

}

const codesetsReducer = (state = initialState, action) => {
  switch (action.type) {
    case ACTION_TYPES.FETCH_ALL_CODESET:
      return { ...state, codesetList: action.payload }
    
      
    default:
      return state
  }
}

export default codesetsReducer


