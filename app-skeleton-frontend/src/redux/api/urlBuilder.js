//FIXME

export const buildParametersRequest = (url, pageable, filters) => {
  // const sortParams = pageable.sort.map(s => `sort=${s.column},${s.order}`).join('&')
  const sortParams = pageable.sort
  ? `sort=${pageable.sort.column},${pageable.sort.order}`
  : ''
  const filterParams = filters.map(k => `${k.param}=${k.value}`).reduce((a, b) => [a, b].join('&'), '')
  return `${url}?page=${pageable.page}&size=${pageable.size}&${sortParams}${filterParams}`
}


export class FiltersUrlConverter {

  static toProps(filters,prefix='') {
    console.log("FiltersUrlConverter",filters)
    return filters?JSON.parse(filters):[]
  }

  static toUrl(filters,prefix='') {
    const filterParams = filters.map(k => `${k.param}=${encodeURI(k.value)}`).reduce((a, b) => [a, b].join('&'), '')
    return filterParams
  }

}

//
export class PageableUrlConverter {

  static toProps(url, pageable) {
    console.log('PageableUrlConverter', url, pageable)
  }

  static toUrl(pageable) {
    const sortParams = pageable.sort
    ? `sort=${pageable.sort.column},${pageable.sort.order}`
    : ''
    return `page=${pageable.page}&size=${pageable.size}&${sortParams}`
  }

}
