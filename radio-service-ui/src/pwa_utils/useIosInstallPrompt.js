import useShouldShowPrompt from './useShouldShowPrompt';

const iosInstallPromptedAt = 'iosInstallPromptedAt';

const isIOS = () => {
  if (navigator.standalone) return false;

  const userAgent = window.navigator.userAgent;
  const isIPad = !!userAgent.match(/iPad/i);
  const isIPhone = !!userAgent.match(/iPhone/i);

  return isIPad || isIPhone;
};

const useIosInstallPrompt = () => {
  const [userShouldBePromptedToInstall, handleUserSeeingInstallPrompt] = useShouldShowPrompt(iosInstallPromptedAt);

  return [isIOS() && userShouldBePromptedToInstall, handleUserSeeingInstallPrompt];
};

export default useIosInstallPrompt;
