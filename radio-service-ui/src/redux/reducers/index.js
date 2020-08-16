import { combineReducers } from 'redux';

import shared from './sharedReducer';
import radio from './radioReducer'
import videos from './videosReducer';
import schedule from './scheduleReducer';
import programs from './programsReducer';

export default combineReducers({ shared, radio, videos, schedule, programs });
