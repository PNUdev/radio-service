import { combineReducers } from 'redux';

import shared from './sharedReducer'
import videos from './videoReducer'

export default combineReducers({ shared, videos});
