import React from "react";

import './InstallButton.scss';
import download from  '../../images/download.png';

const InstallButton = () => {
  return (
    <div className="install-btn-container d-flex justify-content-center align-items-center">
      <button
        className="pwa-install"
        id="setup_button"
        aria-label="Install app"
        title="Встановити"
        onClick={() => window.location.href = "https://fw-radio.herokuapp.com/"}
      >
        <img src={download} className="small-img" />
      </button>
    </div>
  )
}

export default InstallButton;
