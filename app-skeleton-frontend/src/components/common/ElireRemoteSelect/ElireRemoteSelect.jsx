import React from 'react'
import debounce from 'lodash/debounce'

import 'react-select/dist/react-select.css'
import Select from 'react-select'

class ElireRemoteSelect extends React.Component {

  constructor(props) {
    super(props)
    this.state = { values: props.values, isLoading: false }
    this._handleChange = this._handleChange.bind(this)
    this._handleSearch = this._handleSearch.bind(this)
    this._debounceSearch = debounce(this._handleSearch, 250)
  }

  _handleChange(values) {
    this.setState({ ...this.state, values })
    if (this.props.onChange) {
      this.props.onChange(values)
    }
  }

  _handleSearch(input) {
    this.setState({ ...this.state, isLoading: true })
    this.props.fetchData(input).then(
      (options) => {
        this.setState({ ...this.state, options, isLoading: false })
      }
    )
  }

  render() {
    const { placeholder, noResultsText, clearAllText, multi } = this.props
    return (
      <Select
        isLoading={this.state.isLoading}
        value={this.state.values}
        options={this.state.options}
        onChange={this._handleChange}
        onInputChange={this._debounceSearch}
        placeholder={placeholder}
        noResultsText={noResultsText}
        clearAllText={clearAllText}
        multi={multi}
      />
    )
  }
}

ElireRemoteSelect.defaultProps = {
  placeholder: 'Sélectionner',
  noResultsText: 'Aucun résultat trouvé',
  clearAllText: 'Tout supprimer',
  multi: false,
}

ElireRemoteSelect.propTypes = {
  values: React.PropTypes.any,
  fetchData: React.PropTypes.func,
  onChange: React.PropTypes.func,
  multi: React.PropTypes.bool,
  placeholder: React.PropTypes.string,
  noResultsText: React.PropTypes.string,
  clearAllText: React.PropTypes.string,
}

export default ElireRemoteSelect
