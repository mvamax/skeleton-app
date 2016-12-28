import React from 'react'
import './NavTab.css'

const NavTab = ({ children }) =>
  <ul className="nav nav-tabs elire-nav-tab">
    {children}
  </ul>

NavTab.propTypes = {
  children: React.PropTypes.array
}

export default NavTab
