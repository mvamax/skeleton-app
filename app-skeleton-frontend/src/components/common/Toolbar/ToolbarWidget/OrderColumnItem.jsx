import React from 'react'
import { Button, Glyphicon, Checkbox } from 'react-bootstrap'

class OrderColumnItem extends React.Component {


  constructor(props) {
    super(props)
    this._handleClickVisible = this._handleClickVisible.bind(this)
  }

  updateColumnOrdering(direction, e) {
    this.props.updateColumnOrdering(direction, e)
  }

  _handleClickVisible(e) {
    const isVisible = e.target.value === 'true'
    this.props.updateColumnVisible(e.target.name, !isVisible)
  }

  render() {
    return (
      <li >
        <a style={{ display: 'flex' }}>
          <Button className="btn-xs" onClick={e => this.updateColumnOrdering('up', this.props.column)}><Glyphicon glyph="arrow-up" /></Button>
          <Button className="btn-xs" onClick={e => this.updateColumnOrdering('down', this.props.column)}><Glyphicon glyph="arrow-down" /></Button>
          <Checkbox disabled={this.props.isKey} checked={this.props.isVisible} value={this.props.isVisible} name={this.props.column} style={{ paddingLeft: '10px' }} onChange={this._handleClickVisible}>{this.props.header}</Checkbox>
          {/* <button className="btn">test</button>
          <Checkbox checked={this.props.visible}>{this.props.header}</Checkbox> */}
        </a>
      </li>
    )
  }
}

OrderColumnItem.PropTypes = {
  isVisible: React.PropTypes.boolean,
  header: React.PropTypes.string,
  updateColumnVisible: React.PropTypes.func
}

export default OrderColumnItem
