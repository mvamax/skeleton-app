import React from 'react'
import { IconLabel, NavLink } from '../'

const SiderLinkItem = ({ label, to, icon }) =>
  <NavLink to={to} className="list-group-item">
    <IconLabel icon={icon} label={label} />
  </NavLink>

SiderLinkItem.propTypes = {
  to: React.PropTypes.string,
  label: React.PropTypes.string,
  icon: React.PropTypes.string,
}
export default SiderLinkItem
