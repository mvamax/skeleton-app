
export const UPDATE_PAGEABLE_FOR_CONTEXT = 'UPDATE_PAGEABLE_FOR_CONTEXT'
export const updatePageable = (context, pageable) => {
  return {
    type: UPDATE_PAGEABLE_FOR_CONTEXT,
    payload: { context, pageable }
  }
}

export const UPDATE_TOTAL_SIZE_FOR_CONTEXT= 'UPDATE_TOTAL_SIZE_FOR_CONTEXT'
export const updateTotalSize = (context, totalSize) => {
  return {
    type: UPDATE_TOTAL_SIZE_FOR_CONTEXT,
    payload: { context, totalSize }
  }
}
