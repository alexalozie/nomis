import * as ACTION_TYPES from '../actions/types'

const initialState = {
    cboProjectList: [],

}

const cboProjectReducer = (state = initialState, action) => {
  switch (action.type) {
    case ACTION_TYPES.FETCH_ALL_CBO_PROJECT:
      return { ...state, cboProjectList: action.payload }

      case ACTION_TYPES.CREATE_CBO_PROJECT:
      return { ...state, list: action.payload }

      case ACTION_TYPES.ERROR_CREATE_CBO_PROJECT:
        return { ...state, errorMessage: action.payload}
      
    default:
      return state
  }
}

export default cboProjectReducer


