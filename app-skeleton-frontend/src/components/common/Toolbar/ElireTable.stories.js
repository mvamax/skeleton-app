import React from 'react'
import { storiesOf, action } from '@kadira/storybook'
import ElireTable from './ElireTable'
import ElireTableToolbar from './ElireTableToolbar'
import FilterToolbarColumn from './ToolbarWidget/FilterToolbarColumn'

storiesOf('ElireTable', module)
    .add('ElireTable simple sans pagination', () => {
      const columnsDefinitions = [
        { column: 'column1', header: 'Ma colonne clé', field: 'champ1', order: 1, isVisible: true, isKey: true },
        { column: 'column2', header: 'Ma colonne 2', field: 'champ2', order: 2, isVisible: true, isSortable: true },
        { column: 'column3', header: 'Ma colonne 3', order: 3, field: 'champ3', isVisible: true }
      ]
      const items = [
        { champ1: 1, champ2: 'contenu champ2' },
        { champ1: 2, champ2: 'contenu champ2', champ3: 'contenu champ3' },
        { champ1: 6, champ2: 'contenu champ2' },
        { champ1: 7, champ2: 'contenu champ2', champ3: 'contenu champ3' },
        { champ1: 8, champ2: 'contenu champ2' },
        { champ1: 9, champ2: 'contenu champ2' },
        { champ1: 10, champ2: 'contenu champ2' }
      ]
      return <ElireTable columnsDefinitions={columnsDefinitions} totalSize={items.size} items={items}  />
    })

storiesOf('ElireTable', module)
    .add('ElireTable avec toolbar sans pagination', () => {
          const columnsDefinitions = [
            { column: 'column1', header: 'Ma colonne clé', field: 'champ1', order: 1, isVisible: true, isKey: true },
            { column: 'column2', header: 'Ma colonne 2', field: 'champ2', order: 2, isVisible: true, isSortable: true },
            { column: 'column3', header: 'Ma colonne 3', order: 3, field: 'champ3', isVisible: true }
          ]
          const items = [
            { champ1: 1, champ2: 'contenu champ2' },
            { champ1: 2, champ2: 'contenu champ2', champ3: 'contenu champ3' },
            { champ1: 6, champ2: 'contenu champ2' },
            { champ1: 7, champ2: 'contenu champ2', champ3: 'contenu champ3' },
            { champ1: 8, champ2: 'contenu champ2' },
            { champ1: 9, champ2: 'contenu champ2' },
            { champ1: 10, champ2: 'contenu champ2' }
          ]
          return (<ElireTableToolbar
                    columnsDefinitions={columnsDefinitions}
                    items={items}
                    updateFilters={action('update Filters')}
                    filters={[]}
                    onRowSelect={action('click')}>
                      <FilterToolbarColumn param="champ1" label="filtrer sur champ1" />
                  </ElireTableToolbar>)
        })
