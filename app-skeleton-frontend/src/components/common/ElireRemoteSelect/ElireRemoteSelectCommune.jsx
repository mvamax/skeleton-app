import React from 'react'

import ElireRemoteSelect from 'components/common/ElireRemoteSelect/ElireRemoteSelect'

import { fetchCommunes } from 'redux/common/util.actions'

const ElireRemoteSelectCommune = ({ ...rest }) => {
  return (
    <ElireRemoteSelect
      fetchData={fetchCommunes}
      placeholder="Sélectionner Commune"
      {...rest}
    />
  )
}

export default ElireRemoteSelectCommune
