import React from 'react'
import { FormGroup, Radio } from 'react-bootstrap'

class RadioToolbarColumn extends React.Component {
  constructor(props) {
    super(props)
    this.state = {
      value: ''
    }
  }

  getValidationState() {
    return 'success'
  }

  handleChange(optionId) {
    this.setState({ value: optionId })
    this.props.updateFilter({ param: this.props.param, value: optionId })
  }

  render() {
    const { values } = this.props
    return (
      <FormGroup
        controlId="formBasicRadio"
        validationState={this.getValidationState()}
      >
      <div>
        <b>{this.props.label}</b>
      </div>
      <div>
        {values.map(v =>
          <Radio checked={v.id === this.state.value} key={v.id} inline onClick={this.handleChange.bind(this, v.id)}>{v.value}</Radio>)
        }
      </div>
      </FormGroup>
    )
  }
}

RadioToolbarColumn.propTypes = {
  param: React.PropTypes.any,
  updateFilter: React.PropTypes.func
}

export default RadioToolbarColumn
