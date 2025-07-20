import * as ACTION_TYPES from '../actions/types'

const initialState = {
    domains: [],
    services: [],

}

const domainsServicesReducer = (state = initialState, action) => {
  switch (action.type) {
    case ACTION_TYPES.FETCH_ALL_DOMAINS:
      return { ...state, domains: action.payload }
    
      case ACTION_TYPES.FETCH_ALL_DOMAIN_SERVICES:
        return { ...state, services: action.payload }

    default:
      return state
  }
}

export default domainsServicesReducer


