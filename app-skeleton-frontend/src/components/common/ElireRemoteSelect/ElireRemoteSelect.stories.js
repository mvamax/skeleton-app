import React from 'react'
import { storiesOf } from '@kadira/storybook'

import ElireRemoteSelect from './ElireRemoteSelect'


const FLAVOURS = [
{ label: 'Chocolate', value: '1' },
{ label: 'Vanilla', value: '2' },
{ label: 'Strawberry', value: '3' },
{ label: 'Caramel', value: '4' },
{ label: 'Cookies and Cream', value: '5' },
{ label: 'Peppermint', value: '6' },
]

const fetchFlavour = (input) => {
  return new Promise(
    (resolve) => {
      resolve(FLAVOURS.filter(f => f.label.toLowerCase().startsWith(input.toLowerCase())))
    }
  )
}

storiesOf('Elire Select', module)
  .add('ElireRemoteSelect Simple', () => (
    <ElireRemoteSelect
      values={{ value: 1, label: 'Chocolate' }}
      fetchData={fetchFlavour}
      placeHolder="Sélectionner Goût"
    />
  ))
  .add('ElireRemoteSelect multiple', () => (
    <ElireRemoteSelect
      values={[{ value: 1, label: 'Chocolate' }, { value: 2, label: 'Vanilla' }]}
      multi
      fetchData={fetchFlavour}
      placeHolder="Sélectionner Goût"
    />
  ))
