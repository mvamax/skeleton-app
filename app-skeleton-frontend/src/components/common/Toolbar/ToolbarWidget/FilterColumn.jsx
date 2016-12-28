import React from 'react'
import { Input } from 'components/common'

class FilterColumn extends React.Component {

  constructor() {
    super()
    this.onChange = this.onChange.bind(this)
  }

  onChange(event) {
    console.log('change input', event.target.value)
    const filter = {}
    filter[this.props.column] = event.target.value
    this.props.onUpdateFilter(filter)
  }

  render() {
    const { label } = this.props
    return (
      <div className="form-group">
        <label className="col-sm-2 control-label" htmlFor={label}>{label}</label>
        <div className="col-sm-3">
          <Input type="text" errorClassName="inputError" className="form-control input-sm" name={label} onChange={this.onChange} />
        </div>
      </div>
    )
  }
}

FilterColumn.propTypes = {
  label: React.PropTypes.any,
  column: React.PropTypes.any,
  onUpdateFilter: React.PropTypes.func.isRequired
}

export default FilterColumn
