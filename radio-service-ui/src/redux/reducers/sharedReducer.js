const initialState = {
  loading: false,
  backgrounds: {},
}

const sharedReducer = (state = initialState, { type, backgrounds }) => {
  switch (type) {
    case 'SHARED/TURN_ON_LOADER':  return { ...state, loading: true }
    case 'SHARED/TURN_OFF_LOADER': return { ...state, loading: false }
    case 'SHARED/SET_BACKGROUNDS': return { ...state, backgrounds }
    default: return state;
  }
}

export default sharedReducer;
