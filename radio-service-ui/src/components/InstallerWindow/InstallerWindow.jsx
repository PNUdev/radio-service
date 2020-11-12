import React, { useEffect, useState } from "react";

import './InstallerWindow.scss';
import radioBG from '../../images/radio-bg.jpg'

const InstallerWindow = () => {
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

  const onClick = e => {
    e.preventDefault();
    if (!promptInstall) return
    promptInstall.prompt();
  };

  return (
    <div className="install-window d-flex justify-content-center align-items-center" style={{
      backgroundImage: `url(${radioBG})`,
      backgroundPosition: 'center',
      backgroundSize: 'cover',
      backgroundRepeat: 'no-repeat' }}>

      <div className="bg d-flex justify-content-center align-items-center">
        {!supportsPWA &&
          <div className="not-supported d-flex flex-column justify-content-center align-items-center text-center">
            Ваш пристрій не підтримує встановлення такого типу програм
            <a href="http://radio.shpalta.if.ua" className="back-link btn btn-primary mt-3">Повернутись на сайт</a>
          </div>
        }
        {supportsPWA &&
          <div className="install-button d-flex flex-column justify-content-center align-items-center text-center">
            Натисніть щоб встановити
            <button className="btn btn-primary mt-3" onClick={onClick}>Встановити</button>
          </div>
        }
      </div>
    </div>
  )
}

export default InstallerWindow;
