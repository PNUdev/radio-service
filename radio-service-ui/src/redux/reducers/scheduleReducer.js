const initialState = {
  schedule: {},
  tooltips: {},
  programs: {},
}

const scheduleReducer = (state = initialState, { type, schedule, tooltips, programs }) => {
  switch (type) {
    case 'SCHEDULE/SET_SCHEDULE': return { ...state, schedule }
    case 'SCHEDULE/SET_TOOLTIPS': return { ...state, tooltips }
    case 'SCHEDULE/SET_PROGRAMS': return { ...state, programs }

    default: return state;
  }
}

export default scheduleReducer;
