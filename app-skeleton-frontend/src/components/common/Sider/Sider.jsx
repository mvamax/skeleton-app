import React from 'react'
import classNames from 'classnames'
import { Accordion, Panel } from 'react-bootstrap'
import SiderLinkItem from './SiderLinkItem'
import './Sider.css'


class SiderPanel extends Panel {

  renderAnchor(header, id, role, expanded) {
    return (
      <a
        role={role}
        href={id && `#${id}`}
        onClick={this.handleClickTitle}
        aria-controls={id}
        aria-expanded={expanded}
        aria-selected={expanded}
        className={expanded ? null : 'collapsed'}
      >
        {header} {this.props.useBadge && <span className="badge icon-label-badge">{this.props.badge}</span> } <i className={classNames('pull-right glyphicon', { 'glyphicon-chevron-right': expanded }, { 'glyphicon-chevron-down': !expanded })} aria-hidden="true" />
      </a>
    )
  }
}

const Sider = ({ shortcuts, defaultActiveKey, ...rest }) =>
  <div {...rest}>
    <Accordion defaultActiveKey={defaultActiveKey}>
      {shortcuts.map((item, index) =>
        <SiderPanel
          key={index}
          className="sider"
          header={item.header}
          eventKey={index}
          role="button"
          useBadge={rest.useBadge}
          badge={item.links.length}

        >
          <ul className="list-group">
            {
              item.links.map((link, indexLink) =>
                <SiderLinkItem key={indexLink} {...link} />
              )
            }
          </ul>
        </SiderPanel>
      )}
    </Accordion>
  </div>

Sider.propTypes = {
  defaultActiveKey: React.PropTypes.number,
  shortcuts: React.PropTypes.arrayOf(
    React.PropTypes.shape({
      header: React.PropTypes.string,
      links: React.PropTypes.arrayOf(
        React.PropTypes.shape({
          label: React.PropTypes.string,
          icon: React.PropTypes.string,
          to: React.PropTypes.string
        })
      )
    })
  ),
}

export default Sider
