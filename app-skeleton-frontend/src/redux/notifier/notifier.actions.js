export const NOTIFY_NEW = 'NOTIFY_NEW'
export function notify(title, message, notifyType) {
  return {
    type: NOTIFY_NEW,
    title,
    message,
    notifyType
  }
}

export const NOTIFY_DELETE = 'NOTIFY_DELETE'
export function remove(alert) {
  return {
    type: NOTIFY_DELETE,
    alert
  }
}
