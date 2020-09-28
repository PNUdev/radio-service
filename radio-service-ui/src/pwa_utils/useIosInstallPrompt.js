import useShouldShowPrompt from './useShouldShowPrompt';

const iosInstallPromptedAt = 'iosInstallPromptedAt';

const isIOS = () => {
  if (navigator.standalone) {
    //user has already installed the app
    return false;
  }
  const ua = window.navigator.userAgent;
  const isIPad = !!ua.match(/iPad/i);
  const isIPhone = !!ua.match(/iPhone/i);
  return isIPad || isIPhone;
};

const useIosInstallPrompt = () => {
  const [userShouldBePromptedToInstall, handleUserSeeingInstallPrompt] = useShouldShowPrompt(iosInstallPromptedAt);

  return [isIOS() && userShouldBePromptedToInstall, handleUserSeeingInstallPrompt];
};
export default useIosInstallPrompt;
