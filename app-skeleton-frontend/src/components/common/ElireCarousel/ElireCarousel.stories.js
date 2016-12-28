import React from 'react'
import { storiesOf } from '@kadira/storybook'
import ElireCarousel from './ElireCarousel'

storiesOf('ElireCarousel', module)
  .add('ElireCarousel html', () => {
    const items = [
      {
        titre: 'Texte en HTML',
        date: '30/11/2016',
        text: 'Développement des composants'
      },
      {
        titre: 'Texte en HTML',
        date: '30/11/2016',
        text: `Intégration de balises HTML
          <br/>
          <a href="http://www.lvmh.fr">LVMH </a>
          <br/> <li> Test <li>`
      },
      {
        titre: 'Texte en HTML',
        date: '30/11/2016',
        text: 'Changement de fond d\'écran',
        img: 'http://www.lordphoto.ca/wp-content/uploads/2014/10/papier-gris.jpg'
      }
    ]
    return <ElireCarousel items={items} style={{ width: '40%' }} />
  })
  .add('ElireCarousel markdown', () => {
    const items = [
      {
        titre: 'Utilisation des Markdown "Wiki"',
        date: '30/11/2016',
        text: `Liste ordonnée
            \n\n 1. Poires
            \n\n 2. Pommes\n\n     * Golden`,
      },
      {
        titre: 'Utilisation des Markdown "Wiki"',
        date: '28/02/2016',
        text: `Liste non ordonnée
            \n\n * Poires\n\n * Pommes\n\n     * Golden`,
      }
    ]
    return <ElireCarousel items={items} markdown style={{ width: '40%' }} />
  })
