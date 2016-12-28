import React from 'react'

import { DatePicker } from 'components/common'
import { DISPLAY_FORMAT } from 'utils/DateFormatter'

class DatePickerTable extends React.Component {

  constructor(props) {
    super(props)
    this.state = {
      date: props.defaultValue
    }
    this._handleChange = this._handleChange.bind(this)
  }

  focus() {
  }

  _handleChange(e) {
    this.setState({ date: e.target.value })
    this.props.onUpdate(e.target.value)
  }

  render() {
    return (
      <DatePicker
        onChange={this._handleChange}
        defaultValue={this.state.date}
        format={DISPLAY_FORMAT}
        _update={() => ({})}
      />
    )
  }

}

DatePickerTable.propTypes = {
  onUpdate: React.PropTypes.func.isRequired,
  defaultValue: React.PropTypes.string,
}

export default DatePickerTable
