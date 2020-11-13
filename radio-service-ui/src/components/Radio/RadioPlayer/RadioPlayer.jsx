import React, { useState } from 'react'

import AudioPlayer from 'react-h5-audio-player';

import './RadioPlayer.scss'

const STREAM_URL = process.env.REACT_APP_STREAM_URL;

const Radio = () => {
  const [messageShown, setMessageShow] = useState();

  const showTemproraryMessage = () => {
    const messageContainer = document.getElementById('message');
    if (!messageContainer || messageShown) return;

    console.log(messageContainer)
    messageContainer.classList.add('d-block')

    setTimeout(() => {
      messageContainer.classList.remove('d-block')
      setMessageShow(true)
    }, 3000)
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
          onPlay={() => showTemproraryMessage()}
        />

        <div id="message" className="d-none long-loading-message text-center">
          З'єднання може зайняти деякий час, зачекайте будь ласка (:
        </div>
      </div>
    </div>
  )
}

export default Radio;
