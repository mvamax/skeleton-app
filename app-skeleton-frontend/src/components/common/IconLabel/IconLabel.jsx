import React from 'react'
import './IconLabel.css'

const IconLabel = ({ icon, glyphicon, fa, label, badge, ...rest }) => {
  let useGlyph = (glyphicon && !fa) || (fa&&glyphicon)


  return (<div {...rest}>
    { icon && !useGlyph && <span className={`fa fa-${icon}`} aria-label={label} /> }
    { icon && useGlyph && <span className={`glyphicon glyphicon-${icon}`} aria-label={label} /> }
    { label && ` ${label}` }
    {badge && <span className="badge icon-label-badge">{badge}</span>}
  </div>)
}

IconLabel.propTypes = {
  icon: React.PropTypes.string,
  label: React.PropTypes.string,
  badge: React.PropTypes.string,
  glyphicon: React.PropTypes.bool,
  fa: React.PropTypes.bool
}

export default IconLabel
