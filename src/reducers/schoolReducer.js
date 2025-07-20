import * as ACTION_TYPES from '../actions/types'

const initialState = {
    schoolList: [],
}

const schoolReducers = (state = initialState, action) => {
    switch (action.type) {
        case ACTION_TYPES.FETCH_ALL_SCHOOLS:
            return { ...state, schoolList: [...action.payload] }

        case ACTION_TYPES.CREATE_SCHOOL:
            return { ...state, list: action.payload }

        case ACTION_TYPES.ERROR_CREATE_SCHOOL:
            return { ...state, errorMessage: action.payload}

        case ACTION_TYPES.UPLOAD_SCHOOL:
            return { ...state, status: action.payload }

        default:
            return state
    }
}

export default schoolReducers

