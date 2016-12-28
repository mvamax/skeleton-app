
// const pageInit = { page: 0, size: 1, sort: { column: 'id', order: 'desc' } }
export const getPagination = (context, state) => {
  // let newPageable = { ...pageInit, sort: defaultSort }
  return state.pageable || {}
}

export const getTotalSize = (context, state) => state.pagination[context] ? state.pagination[context].totalSize : 0
