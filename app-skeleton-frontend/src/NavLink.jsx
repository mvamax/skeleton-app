import React from 'react'
import { Link } from 'react-router'
import classNames from 'classnames'

const NavLink = ({ children, className, to, router }) =>
  <li className={classNames(className, { active: router.isActive(to) })}>
    <Link to={to}>
      {children}
    </Link>
  </li>


NavLink.propTypes = {
  children: React.PropTypes.object,
  className: React.PropTypes.string,
  to: React.PropTypes.string.isRequired,
  router: React.PropTypes.object.isRequired
}

export default NavLink
