import React from 'react'

import AudioPlayer from 'react-h5-audio-player';

import './RadioPlayer.scss'

const STREAM_URL = process.env.REACT_APP_STREAM_URL;

class Radio extends React.Component {
  constructor(props) {
    super(props);
  }

  render() {
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
            onPlay={e => document.getElementById('visualizer').classList.add('visualizer')}
            onPause={e => document.getElementById('visualizer').classList.remove('visualizer')}
          />
        </div>

        <div id="visualizer" className="mt-5"></div>
      </div>
    )
  }
}

export default Radio;
