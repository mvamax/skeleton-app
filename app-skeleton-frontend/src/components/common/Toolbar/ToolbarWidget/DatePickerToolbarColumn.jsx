import React from 'react'
import { DateField, DatePicker } from 'react-date-picker'
import { FormGroup, FormControl } from 'react-bootstrap'

class DatePickerToolbarColumn extends React.Component {

  constructor(props) {
    super(props)
    this.handleChange = this.handleChange.bind(this)
    const { paramDebut, labelDebut, paramFin, labelFin } = props
    this.state = { paramDebut, labelDebut, paramFin, labelFin, message: '' }
  }

  getValidationState() {
    if (this.state.paramDebut > this.state.paramFin) { return 'error' }
    return null
  }

  _isDebutBeforeFin(newState) {
    const dateDebutIsEmpty = (newState[this.state.paramDebut] || '') === ''
    const dateFinIsEmpty = (newState[this.state.paramFin] || '') === ''
    return dateDebutIsEmpty || dateFinIsEmpty || newState[this.state.paramDebut] <= newState[this.state.paramFin]
  }

  _isSameValue(param, value) {
    const lastValueOfParam = this.state[param] || ''
    return lastValueOfParam === value
  }

  handleChange(param, value) {
    const newParam = {}
    newParam[param] = value
    const newState = { ...this.state, ...newParam }
    if (this._isSameValue(param, value)) {
      return
    }
    if (this._isDebutBeforeFin(newState)) {
      this.setState({ ...newState, validationState: 'success', message: '' })
      this.props.updateFilter({ param, value })
    } else {
      this.setState({ ...newState, validationState: 'error', message: 'date de début après date de fin' })
    }
  }

  render() {
    return (
      <FormGroup style={{ display: 'flex' }} validationState={this.state.validationState}>
        <div>
          <div><b>{this.props.labelDebut}</b></div>
          <DateField
            dateFormat="DD-MM-YYYY"
            expandOnFocus={false}
            updateOnDateClick
            collapseOnDateClick
            className="cadreDate"
            onChange={e => this.handleChange(this.props.paramDebut, e)}
          >
            <DatePicker
              dateFormat="DD-MM-YYYY"
              navigation
              locale="fr"
              forceValidDate
              highlightWeekends
              highlightToday
              weekNumbers
              weekStartDay={1}
              footer={false}
            />
          </DateField>
        </div>
        <div style={{ paddingLeft: '10px' }}>
          <div><b>{this.props.labelFin}</b></div>
          <DateField
            dateFormat="DD-MM-YYYY"
            expandOnFocus={false}
            updateOnDateClick
            collapseOnDateClick
            className="cadreDate"
            onChange={e => this.handleChange(this.props.paramFin, e)}
          >
            <DatePicker
              dateFormat="DD-MM-YYYY"
              navigation
              locale="fr"
              forceValidDate
              highlightWeekends
              highlightToday
              weekNumbers
              weekStartDay={1}
              footer={false}
            />
          </DateField>
        </div>
        <FormControl.Feedback />
      </FormGroup>
    )
  }
}

DatePickerToolbarColumn.propTypes = {
  paramDebut: React.PropTypes.string.isRequired,
  labelDebut: React.PropTypes.string.isRequired,
  paramFin: React.PropTypes.string.isRequired,
  labelFin: React.PropTypes.string.isRequired,
  updateFilter: React.PropTypes.func
}

export default DatePickerToolbarColumn
