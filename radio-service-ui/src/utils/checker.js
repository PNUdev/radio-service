export function checkSSL() {
  const regex = new RegExp('^(https)');
  return regex.test(window.location.href);
}
