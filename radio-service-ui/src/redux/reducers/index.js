import { combineReducers } from 'redux';

import shared from './sharedReducer';
import videos from './videoReducer';
import schedule from './scheduleReducer';
import programs from './programsReducer';

export default combineReducers({ shared, videos, schedule, programs });
