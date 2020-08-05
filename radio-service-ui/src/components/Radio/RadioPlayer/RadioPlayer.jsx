import React from 'react';

import AudioPlayer from 'react-h5-audio-player';
import Popup from "reactjs-popup";
import { CopyToClipboard } from 'react-copy-to-clipboard';

import share from '../../../images/share.png'
import facebook_black from '../../../images/socials/facebook_black.svg';
import copyIcon from '../../../images/copy.svg'

import './RadioPlayer.scss'

const RadioPlayer = () => {
  const url = "http://82.145.41.107:8431/stream?1556707127413.mp3";

  const onCopy = () => {
    alert('Скопійовано!');
  }

    return (
      <div className='player d-flex align-items-center'>
        <AudioPlayer
          src={url}
          preload="auto"
          showDownloadProgress={false}
          showFilledProgress={false}
          showJumpControls={false}
          customProgressBarSection={[]}
          customAdditionalControls={[]}
        />

        <Popup
          trigger={<img src={share} alt="" className="share-link mr-5" />}
          position="top right"
        >
          <div className="share-popup d-flex justify-content-center align-items-center">
            <a
              href="https://www.facebook.com/sharer/sharer.php?u=http://pnu-radio.pp.ua/radio"
              target='_blank'
            >
              <img src={facebook_black} alt="" className="mr-2"/>
            </a>

            {/* TODO: add real link */}
            <CopyToClipboard onCopy={onCopy} text="link_to_pnu_radio">
             <span className="copy-link">
               <img src={copyIcon} alt=""/>
             </span>
            </CopyToClipboard>
          </div>
        </Popup>
      </div>
    )
  }

export default RadioPlayer;
