import React, { Component } from 'react'
import { IconLabel } from 'components/common'

class Notification extends Component {


  render() {
    return (
      <div className="panel panel-default elire-panel-tab">
        <div className="panel-body">
          <div className="list-group">
            <a className="list-group-item ">
              <IconLabel className="pull-left big" icon="envelope-o" fa />
              <h4 className="list-group-item-heading">12/12/2016 - Radiation</h4>
              <p className="list-group-item-text">L'électeur Paul Dubois a été radié de votre liste électorale.
                <IconLabel className="pull-right" icon="link" />
              </p>
            </a>
            <a className="list-group-item">
              <IconLabel className="pull-left big" icon="envelope-o" fa />
              <h4 className="list-group-item-heading">11/12/2016 - Inscription</h4>
              <p className="list-group-item-text">L'électeur Pierre Blanc a été inscrit sur votre liste électorale. <IconLabel className="pull-right" icon="link" /></p>
            </a>
            <a className="list-group-item">
              <IconLabel className="pull-left big" icon="envelope-open" fa />
              <h4 className="list-group-item-heading">11/12/2016 - Inscription</h4>
              <p className="list-group-item-text">L'électeur Pierre Blanc a été inscrit sur votre liste électorale. <IconLabel className="pull-right" icon="link" /></p>
            </a>
            <a className="list-group-item">
              <IconLabel className="pull-left big" icon="envelope-open" fa />
              <h4 className="list-group-item-heading">10/12/2016 - Modification d'état civil</h4>
              <p className="list-group-item-text">L'état civil de l'électeur Marie Jacques a été modifié. <IconLabel className="pull-right" icon="link" /> </p>
            </a>
            <a className="list-group-item">
              <IconLabel className="pull-left big" icon="envelope-open" fa />
              <h4 className="list-group-item-heading">10/12/2016 - Modification d'état civil</h4>
              <p className="list-group-item-text">L'état civil de l'électeur Marie Jacques a été modifié. <IconLabel className="pull-right" icon="link" /> </p>
            </a>
            <a className="list-group-item">
              <IconLabel className="pull-left big" icon="envelope-open" fa />
              <h4 className="list-group-item-heading">10/12/2016 - Modification d'état civil</h4>
              <p className="list-group-item-text">L'état civil de l'électeur Marie Jacques a été modifié. <IconLabel className="pull-right" icon="link" /> </p>
            </a>
          </div>
        </div>
      </div>
    )
  }
}


export default Notification
