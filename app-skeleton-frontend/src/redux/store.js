import { combineReducers, createStore, applyMiddleware, compose } from 'redux'
import thunkMiddleware from 'redux-thunk'
import hashHistory from 'react-router/lib/hashHistory'
import { routerMiddleware } from 'react-router-redux'

// Import reducers
import routing from './router'
import notifier from './notifier'
import cyclists from './cyclists'

// Register reducers
const reducers = combineReducers({
  ...{ routing },
  ...{ notifier },
  ...{ cyclists },
})

const store = createStore(
  reducers,
  compose(
    applyMiddleware(thunkMiddleware, routerMiddleware(hashHistory)),
    window.devToolsExtension ? window.devToolsExtension() : f => f,
  ),
)

export default store
