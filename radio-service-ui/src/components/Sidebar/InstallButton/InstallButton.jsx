import React, { useEffect, useState } from "react";

import './InstallButton.scss';
import download from  '../../../images/download.png';

const InstallButton = () => {
  const [supportsPWA, setSupportsPWA] = useState(false);
  const [promptInstall, setPromptInstall] = useState(null);

  useEffect(() => {
    const handler = e => {
      e.preventDefault();
      setSupportsPWA(true);
      setPromptInstall(e);
    };

    window.addEventListener("beforeinstallprompt", handler);
    return () => window.removeEventListener("transitionend", handler);
  }, []);

  const onClick = evt => {
    evt.preventDefault();

    if (!promptInstall) {
      return;
    }
    promptInstall.prompt();
  };

  if (!supportsPWA) return null;

  return (
    <div className="install-btn-container d-flex justify-content-center">
      <div className="d-flex align-items-center">
        <img src={download} className="small-img" />

        <button
          className="pwa-install"
          id="setup_button"
          aria-label="Install app"
          title="Встановити"
          onClick={onClick}
        >
          Встановити програму
        </button>
      </div>
    </div>
  );
};

export default InstallButton;
