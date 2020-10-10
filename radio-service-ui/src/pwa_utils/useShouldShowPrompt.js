import { useState } from 'react';
import moment from 'moment';

const getInstallPromptLastSeenAt = (promptName) => localStorage.getItem(promptName);

const setInstallPromptSeenToday = (promptName) => {
  const today = moment().toISOString();
  localStorage.setItem(promptName, today);
};

function getUserShouldBePromptedToInstall(promptName, hoursToWaitBeforePromptingAgain) {
  const lastPrompt = moment(getInstallPromptLastSeenAt(promptName));
  const daysSinceLastPrompt = moment().diff(lastPrompt, 'hours');
  return isNaN(daysSinceLastPrompt) || daysSinceLastPrompt > hoursToWaitBeforePromptingAgain;
}

const useShouldShowPrompt = (promptName, hoursToWaitBeforePromptingAgain = 6) => {
  const [userShouldBePromptedToInstall, setUserShouldBePromptedToInstall] = useState(
    getUserShouldBePromptedToInstall(promptName, hoursToWaitBeforePromptingAgain)
  );

  const handleUserSeeingInstallPrompt = () => {
    setUserShouldBePromptedToInstall(false);
    setInstallPromptSeenToday(promptName);
  };

  return [userShouldBePromptedToInstall, handleUserSeeingInstallPrompt];
};
export default useShouldShowPrompt;
