import * as ACTION_TYPES from '../actions/types'

const initialState = {
    ipList: [],

}

const ipReducer = (state = initialState, action) => {
  switch (action.type) {
    case ACTION_TYPES.FETCH_ALL_IP:
      return { ...state, ipList: action.payload }
    
      
    default:
      return state
  }
}

export default ipReducer


