const initialState = {
  loading: false,
}

const sharedReducer = (state = initialState, { type }) => {
  switch (type) {
    case 'SHARED/TURN_ON_LOADER':  return { ...state, loading: true }
    case 'SHARED/TURN_OFF_LOADER': return { ...state, loading: false }
    default: return state;
  }
}

export default sharedReducer;
