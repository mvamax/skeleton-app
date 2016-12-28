import React from 'react'
import { ButtonToolbar, Panel, DropdownButton, Button, ButtonGroup, Glyphicon } from 'react-bootstrap'
import _ from 'lodash'
import { OrderColumnItem } from './'
import ElireTable from './ElireTable'

class ElireTableToolbar extends React.Component {

  constructor(props) {
    super(props)
    this.state = {
      open: false,
      filters: this.props.filters,
      columnsDefinitions: this.props.columnsDefinitions,
    }
    this._updateFilter = this._updateFilter.bind(this)
    this._updateColumnOrdering = this._updateColumnOrdering.bind(this)
    this._updateColumnVisible = this._updateColumnVisible.bind(this)
    this._debounce = _.debounce(this.props.updateFilters, 300)
  }

  componentWillMount() {
    const { withPaginationManager } = this.props
    if (withPaginationManager) {
      this.table = withPaginationManager.withPaginationTable(ElireTable)
    } else {
      this.table = ElireTable
    }
  }

  _updateColumnOrdering(direction, column) {
    const maxOrder = _.max(_.map(this.state.columnsDefinitions, c => c.order))
    const columnToUpdate = _.filter(this.state.columnsDefinitions, c => c.column === column)[0]
    // pas de down avec nue colonne avec ordre maximum
    if (direction === 'down' && columnToUpdate.order === maxOrder) {
      return
    }
    // pas de up avec une colonne d'ordre 1
    if (direction === 'up' && columnToUpdate.order === 1) {
      return
    }
    const orderToSwap =columnToUpdate.order +(direction === 'up' ? -1 : 1)
    const newColumnsDefinitions = _.map(this.state.columnsDefinitions, (c) => {
      if (c.order === orderToSwap) {
        return { ...c, order: columnToUpdate.order }
      } else if (c.order === columnToUpdate.order) {
        return { ...c, order: orderToSwap }
      } else {
        return c
      }
    })
        // pas de up sur une colonne avec ordre 1
    // pas de down avec nue colonne avec ordre maximum
    this.setState({ ...this.state, columnsDefinitions: newColumnsDefinitions })
  }

  _updateColumnVisible(column, isVisible) {
    const newColumnsDefinitions = _.map(this.state.columnsDefinitions,
       (c) => {
         return c.column === column ? { ...c, isVisible } : c
       }
   )
    this.setState({ ...this.state, columnsDefinitions: newColumnsDefinitions })
  }

  _updateFilter(e) {
    let newFilters = this.state.filters
    // Soit le paramètre n'existe pas et on l'ajoute
    if (!_.some(newFilters, { param: e.param })) {
      newFilters.push(e)
    } else {
    // Sinon il existe et on le remplace
      newFilters = _.map(newFilters, (f) => { return (f.param === e.param) ? e : f })
    }
    // FIXME si la valeur est vide alors on le supprime des filtres
    if (e.value === '') {
      newFilters = _.filter(newFilters, (f) => { return (f.param !== e.param) })
    }
    this.setState({ filters: newFilters })
    this._debounce(newFilters)
  }

  render() {
    const childrenWithProps = React.Children.map(
      this.props.children,
      child => React.cloneElement(child, {
        updateFilter: this._updateFilter,
        filter: _.find(this.props.filters, { param: child.props.param }),
      })
    )

    const Table = this.table
    return (
      <div>
        <ButtonToolbar>
          <ButtonGroup>
            <Button onClick={() => this.setState({ open: !this.state.open })}>{this.state.open ? <Glyphicon glyph="minus" /> : <Glyphicon glyph="plus" />}<Glyphicon glyph="filter" /></Button>
          </ButtonGroup>
          { this.props.actionElements.length>0?
            <ButtonGroup>
              {this.props.actionElements.map(e => e)}
            </ButtonGroup>
            :null
          }
          <ButtonGroup className="pull-right control-group">
            {/* <Button  >csv <Glyphicon glyph="download-alt" /></Button>
             <Button  >pdf <Glyphicon glyph="download-alt" /></Button>*/}
            <DropdownButton title="modifier les colonnes" id="dropdown" pullRight>
              { _.orderBy(this.state.columnsDefinitions, 'order', 'asc').map(c =>
                ! c.ignoreColumnHeader ?
                <OrderColumnItem
                key={c.order}
                {...c}
                updateColumnOrdering={this._updateColumnOrdering}
                updateColumnVisible={this._updateColumnVisible}
              />:
              null
              )}
            </DropdownButton>
          </ButtonGroup>
        </ButtonToolbar>
        <Panel collapsible expanded={this.state.open} hidden={!this.state.open}>
          {childrenWithProps}
        </Panel>
        <Table
          columnsDefinitions={this.state.columnsDefinitions}
          onRowSelect={this.props.onRowSelect}
          items={this.props.items}
        />
      </div>
    )
  }
}

ElireTableToolbar.defaultProps = {
  items: [],
  actionElements : []
}

ElireTableToolbar.propTypes = {
  columnsDefinitions: React.PropTypes.array,
  withPaginationManager: React.PropTypes.object,
  updateFilters: React.PropTypes.func.isRequired,
  filters: React.PropTypes.array,
  items: React.PropTypes.array,
  onRowSelect: React.PropTypes.func,
  actionElements: React.PropTypes.arrayOf(React.PropTypes.element),
  // children: React.PropTypes.arrayOf(React.PropTypes.element)
}

export default ElireTableToolbar
