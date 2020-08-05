import React from 'react';

import RadioPlayer from './RadioPlayer';

import './Radio.scss'

const Radio = () => {
  return (
    <div className="radio-container d-flex flex-column justify-content-between">
      <div className="player-container">
        <RadioPlayer />
      </div>
    </div>
  )
}

export default Radio;
