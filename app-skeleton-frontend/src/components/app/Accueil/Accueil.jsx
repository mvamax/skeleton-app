import React from 'react'
import ListeTaches from './ListeTaches'
import './Accueil.css'

const Accueil = ({ children }) => {

  return (
    <div className="container">
      <div className="col-md-12">
        <ListeTaches/>
      </div>
    </div>
  )
}

export default Accueil
