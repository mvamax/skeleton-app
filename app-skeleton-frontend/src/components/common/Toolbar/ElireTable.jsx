import React, { Component } from 'react'
import { BootstrapTable, TableHeaderColumn } from 'react-bootstrap-table'
import _ from 'lodash'

class ElireTable extends Component {

  constructor(props) {
    super(props)
    this._renderColumn = this._renderColumn.bind(this)
    this._columnToTableHeader = this._columnToTableHeader.bind(this)
  }

  _columnToTableHeader(column) {
    return (
      column.isVisible || column.isKey ?
        <TableHeaderColumn
          key={column.field}
          dataField={column.field}
          dataAlign="center"
          dataFormat={column.isNullable ? this.nullFormatter : undefined}
          dataSort={column.isSortable}
          isKey={column.isKey}
          hidden={!column.isVisible}
        >
          {column.header}
        </TableHeaderColumn> :
      null
    )
  }

 //FIXME
  nullFormatter(cell, row) {
    return `${cell || '&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;'}`
  }

  _renderColumn(columnDefinitions) {
    const orderColumnDefinitions = _.orderBy(columnDefinitions, ['order', 'desc'])
    const result = _.map(orderColumnDefinitions, this._columnToTableHeader)
    return _.filter(result, tableHeaderColumn => tableHeaderColumn !== null)
  }

  render() {
    const { items, options, ...rest } = this.props
    // ATTEntion ici ...rest et options sont nécessaires pour récupérer les données de pagination
    const customOptions = {
      ...options,
    }

    return (
      <BootstrapTable
        {...rest}
        data={items}
        selectRow={this.props.onRowSelect?{ onSelect: this.props.onRowSelect, mode: 'radio', clickToSelect: true }:{}}
        options={customOptions}
        remote
        striped
        hover
      >
        {this._renderColumn(this.props.columnsDefinitions)}
      </BootstrapTable>
    )
  }
}

ElireTable.propTypes = {
  onRowSelect: React.PropTypes.func,
  totalSize: React.PropTypes.number,
  columnsDefinitions: React.PropTypes.arrayOf(React.PropTypes.object),
  items: React.PropTypes.array,
  loaded: React.PropTypes.object,
  options: React.PropTypes.object
}

export default ElireTable
