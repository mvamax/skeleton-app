import React from 'react'
import { FormGroup, FormControl, ControlLabel } from 'react-bootstrap'

class FilterToolbarColumn extends React.Component {

  constructor(props) {
    super(props)
    this.state = {
      value: this.props.filter?this.props.filter.value:''
    }
  }

  getValidationState() {
    // const length = this.state.value.length
    // if (length > 5) return 'success'
    // else if (length > 1) return 'warning'
    // else if (length > 0) return 'error'
    // return null
  }

  handleChange(e) {
    this.setState({ value: e.target.value })
    this.props.updateFilter({ param: this.props.param, value: e.target.value })
  }

  render() {
    const { label } = this.props
    return (
      <FormGroup
        controlId="formBasicText"
        validationState={this.getValidationState()}
      >
        <ControlLabel>{label}</ControlLabel>
        <FormControl
          type="text"
          value={this.state.value}
          placeholder="Enter text"
          onChange={e => this.handleChange(e)}
        />
        <FormControl.Feedback />
      </FormGroup>
    )
  }
}

FilterToolbarColumn.propTypes = {
  label:React.PropTypes.string,
  param:React.PropTypes.string,
  updateFilter: React.PropTypes.func
}

export default FilterToolbarColumn
