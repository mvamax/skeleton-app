import React from 'react'
import classnames from 'classnames'
import './Indicateur.css'
import IconLabel from '../IconLabel/IconLabel'

export const STATUT_UP = 'STATUT_UP'
export const STATUT_DOWN = 'STATUT_DOWN'
export const STATUT_EQUAL = 'STATUT_EQUAL'

const CONFIG = { STATUT_UP: {
  icon: 'sort-asc',
  sousNombreClass: 'success'
},
  STATUT_DOWN: {
    icon: 'sort-desc',
    sousNombreClass: 'danger'
  },
  STATUT_EQUAL: {
    icon: '',
    sousNombreClass: 'warning'
  } }

const Indicateur = ({ titre, icon, nombre, sousNombre, description, statut, className, ...rest }) => {
  return (<div {...rest} className={classnames('indicateur panel panel-default default-header-bg', className)}>
    <div className="panel-body">
      <span className="indicateur-titre text-center">
        <IconLabel icon={icon} label={titre} fa glyphicon={false} />
      </span>
      <div className={`indicateur-nombre text-${CONFIG[statut].sousNombreClass}`}>{nombre}</div>
      {(sousNombre || description) && <span><i className={`text-${CONFIG[statut].sousNombreClass}`}><i className={`fa fa-${CONFIG[statut].icon}`} />{sousNombre} </i>{description}</span>}
      {!sousNombre && !description && <span>&nbsp;</span>}
    </div>
  </div>)
}

Indicateur.propTypes = {
  titre: React.PropTypes.string,
  icon: React.PropTypes.string,
  nombre: React.PropTypes.string,
  sousNombre: React.PropTypes.string,
  description: React.PropTypes.string,
  statut: React.PropTypes.string,
  className: React.PropTypes.string
}

Indicateur.defaultProps = {
  statut: STATUT_EQUAL
}

export default Indicateur
