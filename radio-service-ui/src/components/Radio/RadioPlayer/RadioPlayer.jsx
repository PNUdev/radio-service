import React, { useState } from 'react'

import AudioPlayer from 'react-h5-audio-player';

import warningIcon from '../../../images/warning.svg';

import './RadioPlayer.scss'

const STREAM_URL = process.env.REACT_APP_STREAM_URL;

const Radio = () => {
  const [messageShown, setMessageShow] = useState();
  const messageContainer = document.getElementById('message');

  const showLoader = () => {
    if (!messageContainer || messageShown) return;
    messageContainer.classList.add('d-flex','flex-column', 'align-items-center')
  }

  const turnOffLoader = () => {
    if (!messageContainer || messageShown) return;
    messageContainer.classList.remove('d-flex')
    setMessageShow(true)
  }

  return (
    <div className='player d-flex flex-column align-items-center'>
      <div className="mb-5 w-100">
        <AudioPlayer
          src={STREAM_URL}
          preload="auto"
          showDownloadProgress={false}
          showFilledProgress={false}
          showJumpControls={false}
          customProgressBarSection={[]}
          customAdditionalControls={[]}
          customVolumeControls={[]}
          onPlay={() => showLoader()}
          onCanPlay={() => turnOffLoader()}
        />

        <div id="message" className="long-loading-message d-none flex-column justify-content-center text-center">
          <div className='d-flex align-items-center'>
            <img src={warningIcon} alt="" className="d-none d-lg-inline mr-2" />
            Зачекайте, триває з'єднання
          </div>
          <div class="lds-ellipsis"><div></div><div></div><div></div><div></div></div>
        </div>
      </div>
    </div>
  )
}

export default Radio;
