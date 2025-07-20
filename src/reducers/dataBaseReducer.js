import * as ACTION_TYPES from '../actions/types'

const initialState = {
    dataBaseList: [],
    dataBaseBackUp:[],
    restore: [],
   downloadDataBase: []
}

const dataBaseReducers = (state = initialState, action) => {
    switch (action.type) {
        case ACTION_TYPES.BACKUP_LIST:
            return { ...state, dataBaseList: [...action.payload] }

        case ACTION_TYPES.BACKUP_DATABASE:
           return { ...state, dataBaseBackUp: [...action.payload] }

        case ACTION_TYPES.RESTORE_DATABASE:
           return { ...state, restore: action.payload }

        case ACTION_TYPES.DOWNLOAD_DATABASE:
            return { ...state, downloadDataBase: action.payload }

        case ACTION_TYPES.UPLOAD_DATA_BASE:
            return { ...state, status: action.payload }

        default:
            return state
    }
}

export default dataBaseReducers

