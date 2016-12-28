import React from 'react'
import { storiesOf } from '@kadira/storybook'
import IconLabel from './IconLabel'

storiesOf('Icon Label', module)
    .add('with icon and label', () => (
      <IconLabel icon="home" label="Accueil" />
    ))
    .add('with icon', () => (
      <IconLabel icon="home" />
    ))
    .add('with icon with style', () => (
      <IconLabel icon="home" style={{ color: 'red' }} />
    ))
    .add('with label', () => (
      <IconLabel label="Accueil" />
    ))
