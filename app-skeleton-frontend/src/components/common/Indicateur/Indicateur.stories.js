import React from 'react'
import { storiesOf } from '@kadira/storybook'
import Indicateur, { STATUT_EQUAL, STATUT_DOWN, STATUT_UP } from './Indicateur'

storiesOf('Indicateur', module)
    .add('with statut EQUAL', () => (
      <Indicateur titre="Nombre d'électeurs" icon="user" nombre="4 500" sousNombre="21" description="depuis 7j." statut="STATUT_EQUAL" />
    ))
    .add('with statut DOWN', () => (
      <Indicateur titre="Nombre d'électeurs" icon="user" nombre="4 500" sousNombre="21" description="depuis 7j." statut="STATUT_DOWN" />
    ))
    .add('with statut UP', () => (
      <Indicateur titre="Nombre d'électeurs" icon="user" nombre="4 500" sousNombre="21" description="depuis 7j." statut="STATUT_UP" />
    ))
    .add('with 4 indicateurs', () => (
      <div className="container row">
        <Indicateur
          className="col-md-3"
          titre="Nombre d'électeurs"
          icon="user"
          nombre="4 500"
          sousNombre="21"
          description="depuis 7j."
          statut={STATUT_UP}
        />
        <Indicateur
          className="col-md-3"
          titre="Nombre d'électeurs"
          icon="user"
          nombre="4 500"
          sousNombre="21"
          description="depuis 7j."
          statut={STATUT_UP}
        />
        <Indicateur
          className="col-md-3"
          titre="Nombre d'électeurs"
          icon="user"
          nombre="4 500"
          sousNombre="21"
          description="depuis 7j."
          statut={STATUT_DOWN}
        />
        <Indicateur
          className="col-md-3"
          titre="Nombre d'électeurs"
          icon="user"
          nombre="4 500"
          sousNombre="21"
          description="depuis 7j."
          statut={STATUT_EQUAL}
        />
      </div>
    ))
