import React, { Component } from 'react'
import Timesheet from '../Timesheet/Timesheet'

class ElireTimeLine extends Component {
  render() {
    return (
      <Timesheet min={2002} max={2013} theme={'white'} data={this.props.data} />
    )
  }
}

ElireTimeLine.propTypes = {
  data: React.PropTypes.array,
}

export default ElireTimeLine
