import React from 'react';
import { Provider } from 'react-redux';
import { createStore, compose } from 'redux';

import rootReducer from './redux/reducers';

import Wrapper from './Wrapper';

import './App.scss';

const store = createStore(rootReducer, compose(
  window.__REDUX_DEVTOOLS_EXTENSION__ && window.__REDUX_DEVTOOLS_EXTENSION__({trace: true})
));

const App = () => {
  return (
    <Provider store={store}>
      <Wrapper />
    </Provider>
  );
};

export default App;
