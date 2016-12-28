import React from 'react'
import { storiesOf } from '@kadira/storybook'
import Sider from './Sider'

storiesOf('Sider', module)
  .add('1er Sider', () => {
    const shortcuts = [
      {
        header: 'Listes électorales',
        links: [
          { label: 'Je veux modifier l\'état civil d\'un électeur', icon: 'edit', to: '#' },
          { label: 'Je veux rechercher un électeur', icon: 'search', to: '#' },
          { label: 'Je veux consulter la liste électorale', icon: 'zoom-in', to: '#' },
          { label: 'Je veux imprimer les listes d\'émargement', icon: 'print', to: '#' },
          { label: 'Je veux consulter les mouvements sur la liste électorale', icon: 'zoom-in', to: '#' }
        ]
      },
      {
        header: 'Bureaux de vote',
        links: [
          { label: 'Je veux gérer les bureaux de vote', icon: 'edit', to: '#' },
          { label: 'Je veux affecter les électeurs aux bureaux de vote', icon: 'edit', to: '#' },
          { label: 'Je veux renuméroter la liste des électeurs par bureaux de vote', icon: 'edit', to: '#' }
        ]
      },
      {
        header: 'Instruction',
        links: [
          { label: 'Je veux inscrire un électeur', icon: 'edit', to: '#' },
          { label: 'Je veux radier un électeur', icon: 'edit', to: '#' },
          { label: 'Je veux suivre les demandes d\'inscription', icon: 'edit', to: '#' },
          { label: 'Je veux suivre les demandes de radiation', icon: 'edit', to: '#' }
        ]
      }
    ]
    return <Sider shortcuts={shortcuts} className="col-md-3" />
  })
