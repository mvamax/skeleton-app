import moment from 'moment'

export const DISPLAY_FORMAT = 'DD/MM/YYYY'

export const toDisplay = (serverDate, format = DISPLAY_FORMAT) => {
  const momentDate = moment.parseZone(serverDate)
  return momentDate.locale('fr').format(format)
}

export const toServer = (displayDate, format = DISPLAY_FORMAT) => {
  const momentDate = moment(displayDate, format)
  return momentDate.format()
}
