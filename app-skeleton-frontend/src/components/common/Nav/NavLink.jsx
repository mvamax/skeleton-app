import React from 'react'
import { Link } from 'react-router'
import classNames from 'classnames'

const NavLink = ({ children, className, to, router, role }) =>
  <li className={classNames(className, { active: router ? router.isActive(to) : false })}>
    <Link to={to} role={role}>
      {children}
    </Link>
  </li>


NavLink.propTypes = {
  children: React.PropTypes.object,
  className: React.PropTypes.string,
  to: React.PropTypes.string.isRequired,
  router: React.PropTypes.object,
  role: React.PropTypes.string
}

export default NavLink
