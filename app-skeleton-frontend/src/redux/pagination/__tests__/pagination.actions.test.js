import * as actions from '../'

describe('actions', () => {
  fit('should update a pageable for a context', () => {
    const pageable = { page: 0, size: 5, sort: { column: 'columnName', order: 'asc' } }
    const context='monContext'
    const expectedAction = {
      type: actions.UPDATE_PAGEABLE_FOR_CONTEXT,
      payload: { context , pageable }
    }
    const action=actions.updatePageable(context,pageable)
    expect(action.type).toEqual(expectedAction.type)
    expect(action.payload.context).toEqual(expectedAction.payload.context)
    expect(action.payload.pageable).toEqual(expectedAction.payload.pageable)
  })
})
