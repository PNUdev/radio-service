import React from 'react';
import ReactDOM from 'react-dom';

// BOOTSTRAP
// eslint-disable-next-line no-unused-vars
import $ from 'jquery';
// eslint-disable-next-line no-unused-vars
import Popper from 'popper.js';
import 'bootstrap/dist/css/bootstrap.css';
import { isSSL } from './utils/checker';

import './index.css';
import App from './App';
import * as serviceWorker from './serviceWorker';
import { toast, ToastContainer } from "react-toastify";
import 'react-toastify/dist/ReactToastify.css';

require('dotenv').config({ path: '../.env' });
console.log(process.env);

ReactDOM.render(
  <React.StrictMode>
    <App />
    <ToastContainer />
  </React.StrictMode>,
  document.getElementById('root')
);

// If you want your app to work offline and load faster, you can change
// unregister() to register() below. Note this comes with some pitfalls.
// Learn more about service workers: https://bit.ly/CRA-PWA
serviceWorker.register(toast)

