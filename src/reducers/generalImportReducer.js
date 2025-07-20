import * as ACTION_TYPES from '../actions/types'

const initialState = {
    fileList: [],
    exportdata:[],
    restore: [],
    downloadDataBase: []
}

const dataBaseReducers = (state = initialState, action) => {
    switch (action.type) {
        case ACTION_TYPES.FILE_LIST:
            return { ...state, fileList: [...action.payload] }

        case ACTION_TYPES.EXPORT_DATA:
            return { ...state, exportdata: [...action.payload] }

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

