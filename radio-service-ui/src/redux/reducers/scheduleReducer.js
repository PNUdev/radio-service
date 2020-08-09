const initialState = {
  schedule: {},
}

const scheduleReducer = (state = initialState, { type, schedule }) => {
  switch (type) {
    case 'SCHEDULE/SET_SCHEDULE':  return { ...state, schedule }

    default: return state;
  }
}

export default scheduleReducer;
