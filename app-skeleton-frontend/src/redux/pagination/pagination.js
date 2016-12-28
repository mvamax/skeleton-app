import * as actions from './pagination.actions'

const initialState = { }

export default (state = initialState, action) => {
// export default (paginationContext, defaultPageable = initialState) => (state = { ...defaultPageable , paginationContext }, action) => {
  const newContext = {}
  switch (action.type) {
    case actions.UPDATE_PAGEABLE_FOR_CONTEXT:
      newContext[action.payload.context] = { ...state[action.payload.context], ...action.payload.pageable }
      return { ...state, ...newContext }
    case actions.UPDATE_TOTAL_SIZE_FOR_CONTEXT:
      newContext[action.payload.context] = { ...state[action.payload.context], totalSize:action.payload.totalSize }
      return { ...state, ...newContext }
    default:
      return state
  }
}
