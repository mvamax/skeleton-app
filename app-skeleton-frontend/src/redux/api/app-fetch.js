const buildPaginationRequest = (url, pageable, filters) => {
  // const sortParams = pageable.sort.map(s => `sort=${s.column},${s.order}`).join('&')
  const sortParams = pageable.sort
  ? `sort=${pageable.sort.column},${pageable.sort.order}`
  : ''
  const filterParams = filters.map(k => `${k.param}=${k.value}`).reduce((a, b) => [a, b].join('&'), '')
  return `${url}?page=${pageable.page}&size=${pageable.size}&${sortParams}${filterParams}`
}

// customfetch générique

const customFetch = (url, callback, method = 'GET', body) => {
  let headers
  return fetch(url, {
    method,
    headers: {
      'Content-Type': 'application/json'
    },
    body: body && JSON.stringify(body)
  })
  // .then( checkStatus
  //    dispatch notify error
  //   throw new Error(response.headers)
  // )
  //.then( notify)
    .then((response) => {
      headers = response.headers
      return method === 'GET' && response.json()
    })
    .then(json => callback(json, headers))
}

// callbacks spécifiques

const callbackGet = (data) => {
  return {
    data
  }
}

const callbackPagination = (data, headers) => {
  return {
    data,
    totalSize: Number(headers.get('P-total-count'))
  }
}

const callbackPost = (data, headers) => {
  return { id: headers.get('Location') }
}

const callbackDelete = (data, headers) => { // FIXME
  return { alert: headers.get('x-app-alert') }
}

const callbackPut = (data, headers) => { // FIXME
  return { alert: headers.get('x-app-alert') }
}

// fonction réellement utilisable

export const getPaginationRequest = (url, pageable, filter) => {
  return customFetch(buildPaginationRequest(url, pageable, filter), callbackPagination)
}

export const postRequest = (url, body) => {
  return customFetch(url, callbackPost, 'POST', body)
}

export const putRequest = (url, body) => {
  return customFetch(`${url}/${body.id}`, callbackPut, 'PUT', body)
}

export const deleteRequest = (url, id) => {
  return customFetch(`${url}/${id}`, callbackDelete, 'DELETE')
}

// Pagination côté client  => on récupère tous les éléments
export const getRequest = (url, id) => {
  const buildUrl = id ? `${url}/${id}` : `${url}`
  return customFetch(buildUrl, callbackGet)
}
