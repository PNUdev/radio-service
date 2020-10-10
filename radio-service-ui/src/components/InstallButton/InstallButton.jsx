import React, { useEffect, useState } from "react";

import './InstallButton.scss';
import download from  '../../images/download.png';

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
    <div className="install-btn-container d-flex justify-content-center align-items-center">
      <button
        className="pwa-install"
        id="setup_button"
        aria-label="Install app"
        title="Встановити"
        onClick={onClick}
      >
        <img src={download} className="small-img" />
      </button>
    </div>
  );
};

export default InstallButton;
