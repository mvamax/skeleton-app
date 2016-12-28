import { connect } from 'react-redux'
import { remove } from 'redux/notifier'
import NotifierGenerator from './NotifierGenerator'

const mapStateToProps = (state) => {
  return { alerts: state.notifier.alerts, date: new Date() }
}

const mapDispatchToProps = (dispatch) => {
  return {
    remove: (alert) => {
      dispatch(remove(alert))
    }
  }
}

export default connect(mapStateToProps, mapDispatchToProps)(NotifierGenerator)
