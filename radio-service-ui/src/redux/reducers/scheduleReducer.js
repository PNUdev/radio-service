const initialState = {
  schedule: {},
  tooltip:  {},
  programs: {},
}

const scheduleReducer = (state = initialState, {
  type,
  schedule,
  tooltip,
  programs,
}) => {
  switch (type) {
    case 'SCHEDULE/SET_SCHEDULE': return { ...state, schedule }
    case 'SCHEDULE/SET_TOOLTIP':  return { ...state, tooltip }
    case 'SCHEDULE/SET_PROGRAMS': return { ...state, programs }

    default: return state;
  }
}

export default scheduleReducer;
