import * as actions from './cyclists.actions'
import { cyclistsPaginationManager } from './cyclists.paginationManager'

const initialState = {
  items: [],
  filters: []
}

const reducer = (state = initialState, action) => {
  switch (action.type) {
    case actions.LOAD_CYCLISTS:
      return { ...state, loaded: false }
    case actions.LOADED_CYCLISTS:
      return { ...state, loaded: true, items: action.payload.items, totalSize: action.payload.totalSize }
    case actions.UPDATE_FILTERS:
      return { ...state, filters: action.payload.filters }
    // case '@@router/LOCATION_CHANGE':
    //   return { ...state, filters: FiltersUrlConverter.toProps(action.payload.query.filters, 'bureau') }
    default:
      return state
  }
}

export default cyclistsPaginationManager.reducerEnhancer(reducer)
