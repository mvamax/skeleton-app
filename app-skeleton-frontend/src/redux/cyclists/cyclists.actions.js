import Api, { getPaginationRequest } from 'redux/api'
// import { notify } from 'redux/notifier'
import { cyclistsPaginationManager } from 'redux/cyclists'
import { getFilters, paginationContext } from './cyclists.selectors'
import {  updateTotalSize } from 'redux/pagination'


export const LOAD_CYCLISTS = 'LOAD_CYCLISTS'
export const loadCyclists = () => {
  return {
    type: LOAD_CYCLISTS
  }
}

export const LOADED_CYCLISTS = 'LOADED_CYCLISTS'
export const loadedCyclists = (items, totalSize) => {
  return {
    type: LOADED_CYCLISTS,
    payload: { items, totalSize }
  }
}

export const UPDATE_FILTERS = 'UPDATE_FILTERS'
export const updateFilters = (filters) => {
  return {
    type: UPDATE_FILTERS,
    payload: { filters }
  }
}



export const fetchCyclists = () => (dispatch, getState) => {
  getPaginationRequest(Api.cyclists, cyclistsPaginationManager.getPagination(getState()), getFilters(getState()))
    .then(({ data, totalSize }) => {
      dispatch(loadedCyclists(data, totalSize))
      dispatch(updateTotalSize(paginationContext, totalSize))
    }
  )
}
