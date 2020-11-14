import React, { useEffect, useState } from "react";

import {isChrome} from '../../utils/checker';

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
    <div className="install-window d-flex justify-content-center align-items-center p-3" style={{
      backgroundImage: `url(${radioBG})`,
      backgroundPosition: 'center',
      backgroundSize: 'cover',
      backgroundRepeat: 'no-repeat' }}>

      <div className="bg d-flex justify-content-center align-items-center">
        {!supportsPWA && isChrome &&
          <div className="d-flex flex-column justify-content-center align-items-center text-center">
            Зачекайте, будь ласка, відбувається підготовка
            <div class="lds-ellipsis"><div></div><div></div><div></div><div></div></div>
          </div>
        }
        {!isChrome &&
          <div className="not-supported d-flex flex-column justify-content-center align-items-center text-center">
            Ми не можемо встановити програму на ваш пристрій. Будь ласка, переконайтесь, що ви використовуєте браузер Google Chrome.
            <a href="http://radio.shpalta.if.ua" className="back-link btn btn-primary mt-3">Повернутись на сайт</a>
          </div>
        }
        {supportsPWA && isChrome &&
          <div className="d-flex flex-column justify-content-center align-items-center text-center">
            Натисніть щоб встановити
            <button className="install-button btn btn-primary mt-3" onClick={onClick}>Встановити</button>
          </div>
        }
      </div>
    </div>
  )
}

export default InstallerWindow;
