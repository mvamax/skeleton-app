import React, { Component } from 'react'
import { Dropdown } from 'react-bootstrap'
import classNames from 'classnames'
import './NavDropDown.css'

class NavDropDown extends Component {

  constructor(props) {
    super(props)
    this.close = this.close.bind(this)
  }

  close() {
    this.dropDownMenu.props.onClose()
  }

  open() {
    if (!this.dropDownToggle.props.open) {
      this.dropDownToggle.props.onClick()
    }
  }

  render() {
    return (<Dropdown
      componentClass="li" id={this.props.id} className={classNames('dropdown dropdown-large', { active: this.props.router ? this.props.router.isActive(this.props.to) : false })}
      onMouseOver={event => this.open()}
      onMouseOut={event => this.close()}
    >
      <Dropdown.Toggle useAnchor ref={(node) => { this.dropDownToggle = node }}>
        { this.props.title }
      </Dropdown.Toggle>
      <Dropdown.Menu
        ref={(node) => { this.dropDownMenu = node }} className="dropdown-menu dropdown-menu-large row" onClick={(event) => {
          this.close()
        }}
      >
        { this.props.children }
      </Dropdown.Menu>
    </Dropdown>)
  }
}

NavDropDown.propTypes = {
  children: React.PropTypes.any,
  title: React.PropTypes.string,
  to: React.PropTypes.string,
  id: React.PropTypes.string.isRequired,
  router: React.PropTypes.object
}

export default NavDropDown
