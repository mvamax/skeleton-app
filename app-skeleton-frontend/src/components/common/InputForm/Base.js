import { PropTypes, Component } from 'react'


class Base extends Component {

  constructor(props, context) {
    super(props)
    if (props._register) { props._register(this) }
    this._onChange = this._onChange.bind(this)
    this._onBlur = this._onBlur.bind(this)
  }

  componentDidMount() {
    if (this.props._validate) {
      this.props._validate(this)
    }
  }

  componentWillUnmount() {
    if (this.props._unregister) {
      this.props._unregister(this)
    }
  }

  _onChange(event) {
    if (this.props._update) {
      this.props._update(this, event)
    }
    event.persist()
    if (this.props.onChange) this.props.onChange(event)
  }

  _onBlur(event) {
    if (this.props._update) {
      this.props._update(this, event)
    }
    event.persist()
    if (this.props.onBlur) this.props.onBlur(event)
  }

}

Base.contextTypes = {
  _register: PropTypes.func,
  _unregister: PropTypes.func,
  _update: PropTypes.func,
  _validate: PropTypes.func,
  _states: PropTypes.object,
  _errors: PropTypes.object
}

Base.propTypes = {
  onChange: PropTypes.func,
  onBlur: PropTypes.func
}


export default Base
