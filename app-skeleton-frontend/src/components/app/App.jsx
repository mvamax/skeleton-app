import React from 'react'

import { NotifierGenerator } from 'components/common'
import ElireNavBar from './Nav/ElireNavBar'
import Footer from './Footer/Footer'
import './App.css'

const App = ({ children }) => <div className="App">
  <NotifierGenerator />
  <ElireNavBar />
  { children }
  <Footer />

</div>

App.propTypes = {
  children: React.PropTypes.object
}

export default App
