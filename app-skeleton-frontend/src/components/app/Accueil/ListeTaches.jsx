import React, { Component } from 'react'


class ListeTaches extends Component {

  render() {
    const tasks = [
      {
        key:1,
        title:'AOP @Exists on GET,PUT,DELETE endpoint',
        content: 'Add an AOP @Exists that check if entity with id parameter in url on GET,PUT,DELETE exits else throw EntityNotFoundException',
        badge : 'Spring'
      },
      {
        key:2,
        title:'Swagger fields documentation on join fields options',
        content: 'show in swagger documentations the fields possible for example in /cyclists?fields=contact,addresses contact and adresses are the two options possibles',
        badge : 'Swagger'
      },
      {
        key:3,
        title:'Make a react vue for actuator metrics and env',
        content: 'Table to browse the endpoint maybe charts for metrics with WebSocket would be great exercise',
        badge : 'React, Spring'
      }
    ]

    return (


      <div className="panel panel-default">
        <div className="panel-heading ">
            <h4 role="presentation" className="panel-title">
              Liste des éléments techniques à implémenter
            </h4>
        </div>
        <div className="panel-body">
          <ul>
            {tasks.map( t =>
            <li key={t.key}>
              <h4>{t.title} <span className="badge">{t.badge}</span></h4>
              <p>{t.content}</p>
            </li>)}

          </ul>
        </div>
      </div>

    )
  }
}


export default ListeTaches
