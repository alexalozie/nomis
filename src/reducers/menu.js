import * as ACTION_TYPES from '../actions/types'

const initialState = {
  list: [],
  minimize: false
}

const menuReducer = (state = initialState, action) => {
  switch (action.type) {
    case ACTION_TYPES.MENU_FETCH_ALL:
      return { ...state, list: [...action.payload] }
    case 'MENU_MINIMIZE':
      return { ...state, minimize: action.payload }
    default:
      return state
  }
}

export default menuReducer


