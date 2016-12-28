import {fetchCyclists} from './cyclists.actions'
import {selectReducer} from './cyclists.selectors'
import PaginationManager from 'components/common/WithPagination/PaginationManager'

const cyclistsDefaultPageable = { page: 0, size: 5, sort: { column: 'firstname', order: 'asc' } }
export const cyclistsPaginationManager = new PaginationManager('cyclistsCtx',
                                                                cyclistsDefaultPageable,
                                                                fetchCyclists,
                                                                selectReducer)
