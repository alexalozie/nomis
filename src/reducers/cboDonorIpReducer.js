import * as ACTION_TYPES from '../actions/types'

const initialState = {
    cboIpList: [],

}

const cboDonorIpReducer = (state = initialState, action) => {
  switch (action.type) {
    case ACTION_TYPES.FETCH_ALL_CBO_IP:
      return { ...state, cboIpList: action.payload }
    
      
    default:
      return state
  }
}

export default cboDonorIpReducer


