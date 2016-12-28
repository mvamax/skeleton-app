import React from 'react'
import { storiesOf } from '@kadira/storybook'
import NavTab from './NavTab'
import NavLink from './NavLink'
import IconLabel from '../IconLabel/IconLabel'
import TabPanel from './TabPanel'

storiesOf('Tab Panel', module)
      .add('tab panel', () => (
        <div>
          <NavTab>
            <NavLink to="/accueil/taches">
              <IconLabel label="Tâches" icon="tasks" />
            </NavLink>
            <NavLink to="/accueil/details-taches">
              <IconLabel label="Détails des Tâches" icon="search" />
            </NavLink>
            <NavLink to="/accueil/notification">
              <IconLabel label="Notification" icon="comment" />
            </NavLink>
          </NavTab>
          <TabPanel>
            <div>Test contenu</div>
          </TabPanel>
        </div>
      ))
