import React from 'react'
import ReactDOM from 'react-dom'

import { Provider } from 'react-redux'
import { Redirect, Router, Route, IndexRedirect, hashHistory } from 'react-router'
import { syncHistoryWithStore } from 'react-router-redux'

import 'bootstrap/dist/css/bootstrap.css'
import 'font-awesome/css/font-awesome.css'

// redux store
import store from './redux/store'

// Components for router
import App from './components/app/App'
import Accueil from './components/app/Accueil/Accueil'
// import Notification from './components/app/Notification/Notification'
import Cyclists from './components/app/CyclistsList/Cyclists'

import './global.css'
import './theme-bleu.css'
// import './theme-rorcal.css'

const LOCAL_STORAGE_PREF_ROUTE = 'PrefRoute'

const getPrefRoute = (path) => {
  const prefRoute = JSON.parse(localStorage.getItem(LOCAL_STORAGE_PREF_ROUTE))
  if (prefRoute) {
    return prefRoute[path]
  }
  return undefined
}

const setPrefRoute = (parentPath, childPath) => {
  let prefRoute = JSON.parse(localStorage.getItem(LOCAL_STORAGE_PREF_ROUTE))
  if (!prefRoute) prefRoute = {}
  prefRoute[parentPath] = childPath
  localStorage.setItem(LOCAL_STORAGE_PREF_ROUTE, JSON.stringify(prefRoute))
}

const onEnterRootRoute = (nextState, replace, callback) => {
  const prefRoute = getPrefRoute(nextState.location.pathname)
  if (prefRoute) {
    replace(prefRoute)
  }
  callback()
}

const onEnterSecondRoute = (nextState, replace, callback) => {
  let parentPath = ''

  nextState.routes.forEach((route, index, array) => {
    if (index !== array.length - 1) {
      parentPath += route.path
    }
  })
  setPrefRoute(parentPath, nextState.location.pathname)
  callback()
}

// const NoScreen = () => <div>Pas d'écran</div>
// const UnAuthorized = () => <div>Pas autorisé sur l'écran ou la fonction</div>


ReactDOM.render(
  <Provider store={store}>
    <Router history={syncHistoryWithStore(hashHistory, store)}>
      <Route path="/" component={App}>
        <IndexRedirect to="accueil" />
        <Route path="accueil" component={Accueil} />
        <Route path="cyclists" component={Cyclists} />
        <Redirect path="*" to="/" />
      </Route>
    </Router>
  </Provider>,
  document.getElementById('root'),
)
