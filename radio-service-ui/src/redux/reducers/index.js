import { combineReducers } from 'redux';

import shared from './sharedReducer';
import radio from './radioReducer'
import videos from './videosReducer';
import schedule from './scheduleReducer';
import programs from './programsReducer';
import banners from './bannersReducer';

export default combineReducers({ shared, radio, videos, schedule, programs, banners });
