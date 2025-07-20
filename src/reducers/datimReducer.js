import * as ACTION_TYPES from '../actions/types'

const initialState = {
    datimList: [],
    availableList: []

}

const dataBaseReducers = (state = initialState, action) => {
    switch (action.type) {
        case ACTION_TYPES.DATIM_LIST:
            return { ...state, datimList: [...action.payload] }

        case ACTION_TYPES.DATIM_REPORT:
            return { ...state, availableList: [...action.payload] }

        default:
            return state
    }
}

export default dataBaseReducers

