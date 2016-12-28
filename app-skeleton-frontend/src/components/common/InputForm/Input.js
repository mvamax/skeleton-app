import React, { PropTypes } from 'react'
import getViewData from './helpers/get-view-data.js'
import Base from './Base'


class ElireInput extends Base {
  render() {
    const data = getViewData(this.props)

    const props = {
      ref: 'node',
      className: this.props.className || null,
      type: this.props.type,
      checked: this.props.checked,
      value: data.value,
      name: this.props.name,
      onChange: this._onChange,
      onBlur: this._onBlur
    }
    return React.createElement('input', props)
  }
}

ElireInput.propTypes = {
  validations: PropTypes.array,
  type: PropTypes.string.isRequired,
  _register: PropTypes.func,
  _unregister: PropTypes.func,
  _update: PropTypes.func,
  _validate: PropTypes.func
}

export default ElireInput
