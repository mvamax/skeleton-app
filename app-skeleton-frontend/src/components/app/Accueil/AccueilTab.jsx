import React from 'react'
import { NavActiveLink, IconLabel, NavTab } from 'components/common'

const AccueilTab = () =>

  <NavTab>
    <NavActiveLink to="/accueil/taches">
      <IconLabel label="Tâches" icon="tasks" />
    </NavActiveLink>
    <NavActiveLink to="/accueil/details-taches">
      <IconLabel label="Détails des Tâches" icon="search" />
    </NavActiveLink>
    <NavActiveLink to="/accueil/notification">
      <IconLabel label="Notification" icon="comment" badge="2" />
    </NavActiveLink>
  </NavTab>

export default AccueilTab
