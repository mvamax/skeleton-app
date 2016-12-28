import React from 'react'
import { AlertList } from 'react-bs-notifier'

class NotifierGenerator extends React.Component {
  constructor(props) {
    super(props)

    this.state = {
      position: 'top-right',
      alerts: [],
      timeout: 10000,
      newMessage: 'This is a test of the Emergency Broadcast System. This is only a test.'
    }
  }


  onAlertDismissed(alert) {
    this.props.remove(alert)
  }

  onTimeoutChange({ target: { value } }) {
    this.setState({
      timeout: (+value) * 1000
    })
  }

  onNewMessageChange({ target: { value } }) {
    this.setState({ newMessage: value })
  }

  onPositionChange({ target: { value } }) {
    this.setState({ position: value })
  }

  clearAlerts() {
    this.setState({ alerts: [] })
  }

  generate(type) {
    const newAlert = {
      id: (new Date()).getTime(),
      type,
      headline: `Whoa, ${type}!`,
      message: this.state.newMessage
    }

    this.setState({
      alerts: [
        ...this.state.alerts,
        newAlert
      ]
    })
  }

  render() {
    return (<AlertList position={this.state.position} alerts={this.props.alerts} timeout={this.state.timeout} dismissTitle="Begone!" onDismiss={alert => this.onAlertDismissed(alert)} />)
  }
}

NotifierGenerator.propTypes = {
  alerts: React.PropTypes.array,
  remove: React.PropTypes.func
}

export default NotifierGenerator
