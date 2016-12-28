import isObject from 'lodash/isObject'

import pullValue from './pull-value.js'
import pullError from './pull-error.js'
import pullHint from './pull-hint.js'
import pullClassName from './pull-class-name.js'



module.exports = (props) => {
  const data = {}
  data.value = props && props._states && props._states[props.name] ? props._states[props.name].value : ''
  // Si l'objet a un id, on le met en valeur (utile pour les select)
  if (isObject(data.value) && data.value.id) {
    data.value = data.value.id
  }

    // data.value = pullValue(props, context);
    // data.error = pullError(props, context);
    // data.hint = pullHint(data.error, data.value, Rules);
    // data.className = pullClassName(data.error, props);
    // data.props = rest;

  return data
}
