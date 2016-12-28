import React, { Component, PropTypes } from 'react'

class ParentSizer extends Component {


  constructor(props) {
    super(props)
    this.state = { height: 0, width: 0 }
    this._computeDimensions = this._computeDimensions.bind(this)
    this._setRef = this._setRef.bind(this)
  }


  componentDidMount() {
    this._computeDimensions()
  }


  _setRef(element) {
    this.parent = element && element.parentNode
  }


  _computeDimensions() {
    const boundingRect = this.parent.getBoundingClientRect()
    const height = boundingRect.height || 0
    const width = boundingRect.width || 0

    const style = getComputedStyle(this.parent)
    const paddingLeft = parseInt(style.paddingLeft, 10) || 0
    const paddingRight = parseInt(style.paddingRight, 10) || 0
    const paddingTop = parseInt(style.paddingTop, 10) || 0
    const paddingBottom = parseInt(style.paddingBottom, 10) || 0

    this.setState({
      height: height - paddingTop - paddingBottom,
      width: width - paddingLeft - paddingRight
    })
  }

  render() {
    const { children } = this.props
    const { height, width } = this.state
    return (
      <div ref={this._setRef} >
        {children({ height, width })}
      </div>
    )
  }


}


ParentSizer.propTypes = {
  children: PropTypes.func
}


export default ParentSizer
