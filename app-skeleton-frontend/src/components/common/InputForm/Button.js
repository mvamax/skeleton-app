import React, { PropTypes } from 'react'
import Base from './Base'

class ElireButton extends Base {
  render() {
    let { errorClassName, ...props } = this.props
    const isDisabled = this.props._errors &&  Object.keys(this.props._errors).length
    const className = `${this.props.className ? this.props.className : ''}${isDisabled && errorClassName ? ` ${errorClassName}` : ''}`

    return (<button
      disabled={isDisabled}
      className={className || null}
    >
      {this.props.children}
    </button>)
  }
}

ElireButton.propTypes = {
  form: PropTypes.object
}

export default ElireButton
