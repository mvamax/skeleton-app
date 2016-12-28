import { connect } from 'react-redux'
import * as cyclists from 'redux/cyclists'
import CyclistsTableToolbar from './CyclistsTableToolbar'

const mapStateToProps = (state) => {
  // const filter = state.routing.locationBeforeTransitions.query.filter
  return {
    items: cyclists.getCyclists(state),
    filters: cyclists.getFilters(state),
  }
}

const mapDispatchToProps = (dispatch) => {
  return {
    onUpdateFilters: (filters) => {
      dispatch(cyclists.updateFilters(filters))
      dispatch(cyclists.fetchCyclists())
    },
    // onSelectBureau: bureau => dispatch(bureaux.loadCurrentBureau(bureau)),
  }
}

export default connect(mapStateToProps, mapDispatchToProps)(CyclistsTableToolbar)
