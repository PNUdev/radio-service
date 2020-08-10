const initialState = {
  schedule: {},
  tooltips: {},
}

const scheduleReducer = (state = initialState, { type, schedule, tooltips }) => {
  switch (type) {
    case 'SCHEDULE/SET_SCHEDULE':  return { ...state, schedule }
    case 'SCHEDULE/SET_TOOLTIPS':  return { ...state, tooltips }

    default: return state;
  }
}

export default scheduleReducer;
