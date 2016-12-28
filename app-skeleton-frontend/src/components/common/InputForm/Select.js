import React, { Component, PropTypes } from 'react'
import getViewData from './helpers/get-view-data.js'
import Base from './Base'

class ElireSelect extends Base {

  render() {
    const data = getViewData(this.props)
    return (<div className={this.props.containerClassName || null}>
      <select
        name={this.props.name}
        value={data.value}
        className={this.props.className || null}
        onChange={this._onChange}
      >
        {this.props.children}
      </select>


    </div>)
  }
}

ElireSelect.propTypes = {
  validations: PropTypes.array,
  onChange: PropTypes.func,
  _register: PropTypes.func,
  _unregister: PropTypes.func,
  _update: PropTypes.func,
  _validate: PropTypes.func,
  _errors: PropTypes.object
}


export default ElireSelect
