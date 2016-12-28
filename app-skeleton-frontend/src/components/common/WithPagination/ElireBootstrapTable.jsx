import React from 'react'
import { BootstrapTable } from 'react-bootstrap-table'

class ElireBootstrapTable extends Component {



  return (
    <BootstrapTable
      {...rest}
    // data={items}
      remote
      striped
      hover
      pagination
    >
      {children}
    </BootstrapTable>
  )
}

ElireBootstrapTable.propTypes = {
  children: React.PropTypes.array,
  items: React.PropTypes.object
}

// export const ElirePaginationTable = (pageable) =>  withPagination(pageable)(ElireBootstrapTable)

export default ElireBootstrapTable
