const SSL_REGEX = new RegExp('^(https)');

export const isSSL = SSL_REGEX.test(window.location.href);
export const isChrome = /chrome/.test(navigator.userAgent.toLowerCase());
export const isPWA = window.matchMedia('(display-mode: standalone)').matches || window.navigator.standalone;
