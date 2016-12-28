import React, { Component } from 'react'
import { FilterToolbarColumn } from 'components/common/Toolbar'
import { ElireTableToolbar } from 'components/common/Toolbar'
import { cyclistsPaginationManager } from 'redux/cyclists'

class CyclistsTableToolbar extends Component {

  constructor(props) {
    super(props)
    this.columnsDefinitions = [
      { column: 'id', header: 'Id', field: 'id', order: 1, isVisible: false, isKey: true , ignoreColumnHeader : true},
      { column: 'firstname', header: 'Firstname', field: 'firstname', order: 2, isVisible: true, isSortable: true },
      { column: 'lastname', header: 'Lastname', field: 'lastname', order: 3, isVisible: true, isSortable: true },
    ]
  }

  render() {
    return (
      <ElireTableToolbar
        columnsDefinitions={this.columnsDefinitions}
        withPaginationManager={cyclistsPaginationManager}
        filters={this.props.filters}
        updateFilters={this.props.onUpdateFilters}
        items={this.props.items}
      >
        <FilterToolbarColumn param="search" label="filter on lastname or firstname" />
      </ElireTableToolbar>
    )
  }
}

CyclistsTableToolbar.propTypes = {
  filters: React.PropTypes.array,
  onUpdateFilters: React.PropTypes.func,
  // onRowSelect: React.PropTypes.func,
  items: React.PropTypes.array
}

export default CyclistsTableToolbar
