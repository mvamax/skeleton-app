import React from 'react'
import { IconLabel, NavActiveDropDown, NavActiveLink } from 'components/common'

import './ElireNavBar.css'


const NavBar = () => <nav className="navbar navbar-default navbar-static-top">

  <div className="navbar-header">
    <button className="navbar-toggle" type="button" data-toggle="collapse" data-target=".js-navbar-collapse">
      <span className="sr-only">Toggle navigation</span>
      <span className="icon-bar" />
      <span className="icon-bar" />
      <span className="icon-bar" />
    </button>
    <span className="navbar-brand">
      <h1><IconLabel icon="star" label="App" /></h1>
      <h2>Skeleton-app</h2>
    </span>
  </div>

  <div className="collapse navbar-collapse js-navbar-collapse">
    <ul className="nav navbar-nav">
      <NavActiveLink to="/accueil">
        <IconLabel icon="home" label="Accueil" />
      </NavActiveLink>

      <NavActiveDropDown title="Liste des cyclistes" id="vote" to="/cyclists">
        <li className="col-sm-3">
          <ul>
            <NavActiveLink to="/cyclists" role="menu">
              <IconLabel icon="inbox" label="Liste des cyclistes" />
            </NavActiveLink>
          </ul>
        </li>
      </NavActiveDropDown>
    </ul>

  </div>


</nav>

export default NavBar
