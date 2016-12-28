import { NOTIFY_NEW, NOTIFY_DELETE } from './notifier.actions'

const INITIAL_STATE = { alerts: [] }

export default (state = INITIAL_STATE, action) => {
  switch (action.type) {
    case NOTIFY_NEW : {
      const newAlert = {
        id: (new Date()).getTime(),
        type: action.notifyType,
        headline: action.title,
        message: action.message,
        timeout: 10000
      }
      const alerts = state.alerts
      alerts.push(newAlert)

      return Object.assign({}, state, { alerts })
    }

    case NOTIFY_DELETE : {
      let alerts = state.alerts
      // find the index of the alert that was dismissed
      const idx = alerts.indexOf(action.alert)

      if (idx >= 0) {          // remove the alert from the array
        alerts = [...alerts.slice(0, idx), ...alerts.slice(idx + 1)]
      }
      return { ...state, alerts }
    }
    default:
      return state
  }
}
