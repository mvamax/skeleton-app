import React, {  PropTypes } from 'react'
import { DateField, DatePicker } from 'react-date-picker'
import 'react-date-picker/index.css'
import './DatePicker.css'
import Base from './Base'

class DatePickerElire extends Base {
  constructor(props) {
    super(props)
    this._handleChange = this._handleChange.bind(this)
  }

  _handleChange(value) {
    const relayEvent = { target: { value }, type: 'change' }
    this.props._update(this, relayEvent, true, true)
    this.props.onChange && this.props.onChange(relayEvent)
  }

  render() {
    return (<div className={this.props.containerClassName || null}>

      <DateField
        dateFormat={this.props.format}
        defaultValue={this.props.defaultValue}
        expandOnFocus={false}
        updateOnDateClick
        collapseOnDateClick
        className="cadreDate"
        onChange={this._handleChange}
      >
        <DatePicker
          dateFormat={this.props.format}
          navigation
          locale="fr"
          forceValidDate={false}
          highlightWeekends
          highlightToday
          weekNumbers
          weekStartDay={1}
          footer={false}
        />
      </DateField>


    </div>)
  }
}

DatePickerElire.defaultProps = {
  format: 'DD-MM-YYYY',
}

DatePickerElire.propTypes = {
  onChange: PropTypes.func,
  defaultValue: PropTypes.string,
  format: PropTypes.string,
}

DatePickerElire.contextTypes = {
  _register: PropTypes.func,
  _unregister: PropTypes.func,
  _update: PropTypes.func,
  _validate: PropTypes.func,
  states: PropTypes.object,
  errors: PropTypes.object
}

export default DatePickerElire
