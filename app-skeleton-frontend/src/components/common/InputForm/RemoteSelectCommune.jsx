import React from 'react'

import RemoteSelect from 'components/common/InputForm/RemoteSelect'

import { fetchCommunes } from 'redux/common/util.actions'
import Base from './Base'

class RemoteSelectCommune extends Base {

  render() {
    return (
      <RemoteSelect
        fetchData={fetchCommunes}
        placeholder="Sélectionner Commune"
        {...this.props}
      />
    )
  }
}
export default RemoteSelectCommune
