import React, {Component,PropTypes} from 'react'
import getViewData from './helpers/get-view-data.js'
import classNames from 'classnames'

class ElireErrorInput extends Component {

  constructor(props, context) {
      super(props, context)
      context._register(this)
  }

  componentDidMount() {
      this.context._validate(this)
  }

  componentWillUnmount() {
      this.context._unregister(this)
  }

  handleChange(event) {
      this.context._update(this, event)
      event.persist()
      if(this.props.onChange) this.props.onChange(event)
  }

  handleBlur(event) {
      this.context._update(this, event)
      event.persist()
      if (this.props.onBlur) this.props.onBlur(event)
  }

  render() {
    let data = getViewData(this.props, this.context);
    let props = {
                  ref:'node',
                  className:data.className || null,
                  type:'text',
                  checked:data.props.checked,
                  value:data.value,
                  onChange:this.handleChange.bind(this),
                  onBlur:this.handleBlur.bind(this),
                  ...data.props

    }
    // return React.createElement('input', props);


    return <div className={classNames('form-group has-feedback',{'has-error':data.error})}>
              <label className="col-sm-2 control-label" htmlFor="inputError2">Nom</label>
                <div className="col-sm-10">
                  <input
                      ref='node'
                      type='text'

                      className={data.className || null}
                      checked={data.props.checked}
                      value={data.value}
                      onChange={this.handleChange.bind(this)}
                      onBlur={this.handleBlur.bind(this)} />
                      {data.hint? <span className="glyphicon glyphicon-remove form-control-feedback" aria-hidden="true"></span> : ''}
                      {data.hint}
              </div>
            </div>
  }

}

ElireErrorInput.propTypes = {
    onChange: PropTypes.func,
    onBlur: PropTypes.func,
    validations : PropTypes.array
};

ElireErrorInput.contextTypes = {
    _register: PropTypes.func,
    _unregister: PropTypes.func,
    _update: PropTypes.func,
    _validate: PropTypes.func,
    states: PropTypes.object,
    errors: PropTypes.object
};


export default ElireErrorInput
