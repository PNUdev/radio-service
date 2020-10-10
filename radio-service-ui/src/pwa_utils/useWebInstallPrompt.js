import { useState, useEffect } from 'react';
import useShouldShowPrompt from './useShouldShowPrompt';

const webInstallPromptedAt = 'webInstallPromptedAt';

const useWebInstallPrompt = () => {
  const [installPromptEvent, setInstallPromptEvent] = useState();
  const [userShouldBePromptedToInstall, handleUserSeeingInstallPrompt] = useShouldShowPrompt(webInstallPromptedAt);

  useEffect(() => {
    const beforeInstallPromptHandler = event => {
      event.preventDefault();

      if (userShouldBePromptedToInstall) setInstallPromptEvent(event);
    };

    window.addEventListener('beforeinstallprompt', beforeInstallPromptHandler);

    return () => window.removeEventListener('beforeinstallprompt', beforeInstallPromptHandler);
  }, [userShouldBePromptedToInstall]);

  const handleInstallDeclined = () => {
    handleUserSeeingInstallPrompt();
    setInstallPromptEvent(null);
  };

  const handleInstallAccepted = () => {
    installPromptEvent.prompt();

    installPromptEvent.userChoice.then(choice => {
      if (choice.outcome !== 'accepted') handleUserSeeingInstallPrompt();

      setInstallPromptEvent(null);
    });
  };

  return [installPromptEvent, handleInstallDeclined, handleInstallAccepted];
};

export default useWebInstallPrompt;
