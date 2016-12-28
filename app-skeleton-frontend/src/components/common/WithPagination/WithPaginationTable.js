import React, { Component, PropTypes } from 'react'
import { connect } from 'react-redux'
import * as pagination from 'redux/pagination'
// https://github.com/AllenFang/react-bootstrap-table/issues/710 https://github.com/AllenFang/react-bootstrap-table/issues/539

const withPaginationtable = (context, defaultPageable, fetchItems, selectReducer, ComponentToWrap) => {
  // Le component to Wrap doit forcément avoir un <BootstrapTable>
  class WithPaginationtableComponent extends Component {

    constructor(props) {
      super(props)
      this.onPageChange = this.onPageChange.bind(this)
      this.onSortChange = this.onSortChange.bind(this)
      this.renderPaginationShowsTotal = this.renderPaginationShowsTotal.bind(this)
    }

    componentWillMount() {
      // const { pageable } = this.props
      this.props.onPaginationChange(defaultPageable)
    }

    onPageChange(page,size) {
      const { pageable } = this.props
      const newPageable = { ...pageable, page: page - 1,size: size  }
      this.props.onPaginationChange(newPageable)
    }

    onSortChange(column, order) {
      const { pageable } = this.props
      const newPageable = { ...pageable, sort: { column, order } }
      this.props.onPaginationChange(newPageable)
    }

    renderPaginationShowsTotal(start, to, total) { // EsLint?
      return (
        <p style={{ color: 'blue' }}>
        de { start } à { to } sur { total } éléments
      </p>
      )
    }


    render() {
      const { pageable, totalSize } = this.props
      const paginationOptions = {
        sizePerPage: pageable.size ||defaultPageable.size,
        onPageChange: this.onPageChange,
        onSortChange: this.onSortChange,
        page: pageable.page + 1,
        sizePerPageList: [1, 5, 10],
        defaultSortName: pageable.sort ? pageable.sort.column : defaultPageable.sort.column, // pourquoi -> componentWillMount?
        defaultSortOrder: pageable.sort ? pageable.sort.order : defaultPageable.sort.order, // pourquoi-> componentWillMount??
        paginationShowsTotal: this.renderPaginationShowsTotal,
      }
      return (
        <ComponentToWrap
          {...this.props}
          options={paginationOptions}
          fetchInfo={{ dataTotalSize: totalSize }}
          pagination
        />
      )
    }
  }

  WithPaginationtableComponent.propTypes = {
    pageable: PropTypes.object,
    onPaginationChange: PropTypes.func,
    totalSize: PropTypes.number,
    // defaultSort: PropTypes.object.isRequired,
    //comment declencher les données + sort
  }

  const mapStateToProps = (state) => {
    // const filter = state.routing.locationBeforeTransitions.query.filter
    return {
      pageable: selectReducer(state).pageable || defaultPageable,
      totalSize:  selectReducer(state).totalSize || 0
    }
  }

  const mapDispatchToProps = (dispatch) => {
    return {
      onPaginationChange: (pageable) => {
        console.log("onPaginationChange")
        dispatch(pagination.updatePageable(context, pageable))
        dispatch(fetchItems())
      }
    }
  }

  return connect(mapStateToProps, mapDispatchToProps)(WithPaginationtableComponent)
  // return WithPaginationtableComponent
}

export default withPaginationtable
