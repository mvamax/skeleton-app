import React, { Component, PropTypes } from 'react'
import validator from 'validator'
import Rules from './rules'
import Base from './Base'

// TODO une propriete onChange
class ElireForm extends Component {

  constructor(props) {
    super(props)

    this.state = {
      errors: {},
      states: {}
    }

    this.components = {}

    this._register = this._register.bind(this)
    this._unregister = this._unregister.bind(this)
    this._update = this._update.bind(this)
    this._validate = this._validate.bind(this)

      // Alimentation du state à partir de l'objet en paramètre
    if (props['data-edit']) {
      Object.keys(props['data-edit']).forEach(function (key) {
        this.state.states[key] = { value: props['data-edit'][key] }
      }, this)
    }
  }

  componentWillReceiveProps(nextProps) {
    if (nextProps['data-edit']) {
      const states = {}
        // Ajout pour mettre à vide les champs et autoriser une data-edit à {}

      Object.keys(nextProps['data-edit']).forEach((key) => {
        states[key] = { value: nextProps['data-edit'][key] }
      }, this)

      this.setState(Object.assign({}, this.state, { states }))
    }
  }


  _register(component) {
    this.components[component.props.name] = component
    if (!this.state.states[component.props.name]) this.state.states[component.props.name] = { value: '' }
  }

    // TODO: Should be refactored
  _unregister(component) {
    const states = Object.assign({}, this.state.states)
    const errors = Object.assign({}, this.state.errors)

    delete states[component.props.name]
    delete errors[component.props.name]
    delete this.components[component.props.name]

    this.setState({
      states,
      errors
    })
  }

  _update(component, event, isChanged, isUsed) {
    // FIXME: remove mutation
    this.state.states[component.props.name] = this.state.states[component.props.name] || {}

    const componentState = this.state.states[component.props.name]
    const checkbox = (component.props.type === 'checkbox' || component.props.type === 'radio')
    const isChecked = component.refs && component.refs.node && component.refs.node.checked

    Object.assign(componentState, {
      value: event.target.value,
      isChanged: isChanged || componentState.isChanged || event.type === 'change',
      isUsed: isUsed || checkbox || componentState.isUsed || event.type === 'blur',
      isChecked
    })

    this._validate()

    // On déclence l'évenement onChange que s'il n'y a pas d'erreurs sur la validation
    if (this.props.onChange && Object.keys(this.state.errors).length === 0) {
      this.props.onChange(event, componentState)
    }
  }

  _validate() {
    const errors = Object.assign({}, this.state.errors)

    Object.keys(this.components).forEach((key) => {
      const error = this._getError(this.components[key])

      if (error) {
        Object.assign(errors, error)
      } else {
        delete errors[this.components[key].props.name]
      }
    })

    this.setState({
      errors
    })
  }

  _getError(component) {
    const validations = component.props.validations
    if (!validations) return

    let i = validations.length
    let error = null

    while (i--) {
      const validation = validations[validations.length - i - 1]
      let value = this.state.states.hasOwnProperty(component.props.name)
                ? this.state.states[component.props.name].value
                : component.props.value || ''

      if ((component.props.type === 'checkbox' || component.props.type === 'radio') && component.refs.node && !component.refs.node.checked) {
        value = ''
      }

      if (!Rules[validation].rule(value, component, this)) {
        error = {}
        error[component.props.name] = validation

        break
      }
    }

    return error
  }

  showError(name, error) {
    const errors = Object.assign({}, this.state.errors)
    errors[name] = error
    this.setState({
      errors
    })
  }

  hideError(name) {
    const errors = Object.assign({}, this.state.errors)
    delete errors[name]
    this.setState({
      errors
    })
  }

  validate(name) {
        // FIXME: remove mutation
    this.state.states[name] = this.state.states[name] || {}
    const componentState = this.state.states[name]

    Object.assign(componentState, {
      value: this.state.states[name].value || this.components[name].props.value,
      isChanged: true,
      isUsed: true
    })

    this._validate()
  }


  getForm() {
    const form = {}
    Object.keys(this.state.states).forEach((component, index, tabs) => {
        // Cas quand le composant n'est pas dans le formulaire
      if (!this.components[component]) {
        form[component] = this.state.states[component].value
        return
      }

      const checkbox = this.components[component].props.type === 'checkbox' || this.components[component].props.type === 'radio'
      if (checkbox) {
        form[component] = this.state.states[component].isChecked
      } else {
        form[component] = this.state.states[component].value
      }
    })
    return form
  }

  getErrors() {
    return this.state.errors
  }

  render() {
    const addProps = (child) => {
      return React.cloneElement(child, {
        _register: this._register,
        _unregister: this._unregister,
        _validate: this._validate,
        _update: this._update,
        _errors: this.state.errors,
        _states: this.state.states
      })
    }

    // Fonction qui permet de savoir si un objet hérite de base
    const isInstanceOfBase = (child) => {
      if (child.type && child.type.prototype && child.type.prototype instanceof Base) {
        return true
      }
      if (child.type && child.props && child.props.isFormInput) {
        return true
      }
      return false
    }

    // Fonction qui recherche si le composant hérité de Base
    const search = (childrens) => {
      return React.Children.map(childrens, (child) => {
        if (isInstanceOfBase(child)) {
          return addProps(child)
        }

        if (child.props && child.props.children) {
          return React.cloneElement(child, { children: search(child.props.children) })
        } else {
          return child
        }
      }

      )
    }

    const newChildrens = search(this.props.children)

    return (<form {...this.props} >
      {newChildrens}
    </form>)
  }


}

Object.assign(Rules, {
    // Key name maps the rule
  required: {
        // Function to validate value
    rule: (value, component, form) => {
      return value ? String(value).trim() : value // si la valeut est un entier
    },
        // Function to return hint
        // You may use current value to inject it in some way to the hint
    hint: (value) => {
      return <span className="text-danger is-visible">Le champ est requis</span>
    }
  },
  email: {
        // Example usage with external 'validator'
    rule: (value) => {
      return validator.isEmail(value)
    },
    hint: (value) => {
      return <span className="text-danger is-visible">{value} n est pas un email valide.</span>
    }
  },
  length2: {
        // Example usage with external 'validator'
    rule: (value) => {
      return validator.isLength(value, { min: 2, max: undefined })
    },
    hint: (value) => {
      return <span id="inputError2Status" className="text-danger" >Le nom doit avoir une taille d au moins 2 caractères</span>
    }
  },
    // This example shows a way to handle common task - compare two fields for equality
  password: {
        // rule function can accept 2 extra arguments:
        // component - current checked component
        // form - form component which has 'states' inside native 'state' object
    rule: (value, component, form) => {
            // form.state.states[name] - name of corresponding field
      const password = form.state.states.password
      const passwordConfirm = form.state.states.passwordConfirm
            // isUsed, isChanged - public properties
      const isBothUsed = password && passwordConfirm && password.isUsed && passwordConfirm.isUsed
      const isBothChanged = isBothUsed && password.isChanged && passwordConfirm.isChanged

      if (!isBothUsed || !isBothChanged) {
        return true
      }

      return password.value === passwordConfirm.value
    },
    hint: (value) => {
      return <span className="form-error is-visible">Passwords should be equal.</span>
    }
  }
})


ElireForm.childContextTypes = {
  _register: PropTypes.func,
  _unregister: PropTypes.func,
  _update: PropTypes.func,
  _validate: PropTypes.func,
  states: PropTypes.object,
  errors: PropTypes.object
}

export default ElireForm
