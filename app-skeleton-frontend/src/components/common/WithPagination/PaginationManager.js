import withPaginationtable from './WithPaginationTable'
import * as actions from 'redux/pagination'

class PaginationManager {

  constructor(context, defaultPageable, fetchItems, selectReducer) {
    this.context = context
    this.defaultPageable = defaultPageable
    this.fetchItems = fetchItems
    this.selectReducer = selectReducer
    this.getPagination = this.getPagination.bind(this)
    this.getTotalSize = this.getTotalSize.bind(this)
  }

  withPaginationTable(ComponentToWrap) {
    return withPaginationtable(this.context, this.defaultPageable, this.fetchItems, this.selectReducer, ComponentToWrap)
  }

  reducerEnhancer(reducer) {
    return (state, action) => {
      switch (action.type) {
        case actions.UPDATE_PAGEABLE_FOR_CONTEXT:
          if (action.payload.context===this.context){
              return { ...state, pageable:action.payload.pageable }
          }
          return state
        case actions.UPDATE_TOTAL_SIZE_FOR_CONTEXT:
          // newContext[action.payload.context] = { ...state[action.payload.context], totalSize: action.payload.totalSize }
          return { ...state, totalSize: action.payload.totalSize }
        // case '@@router/LOCATION_CHANGE':
        //     return { ...state, pageable: JSON.parse(action.payload.query.pageable) }
        default:
          return reducer(state, action)
      }
    }
  }

  getPagination(state) {
    return this.selectReducer(state).pageable || this.defaultPageable
  }

  getTotalSize(state) {
    return this.selectReducer(state).totalSize || 0
  }

}

export default PaginationManager
