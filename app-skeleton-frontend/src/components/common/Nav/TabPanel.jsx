import React from 'react'

import './TabPanel.css'

const TabPanel = ({ children }) =>
  <div className="panel panel-default tab-panel">
    <div className="panel-body">
      {children}
    </div>
  </div>

TabPanel.propTypes = {
  children: React.PropTypes.any
}

export default TabPanel
