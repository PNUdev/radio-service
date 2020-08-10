const initialState = {
  programs: [],
}

const radioReducer = (state = initialState, { type, programs }) => {
  switch (type) {
    case 'RADIO/SET_TODAY_PROGRAMS':  return { ...state, programs }

    default: return state;
  }
}

export default radioReducer;
