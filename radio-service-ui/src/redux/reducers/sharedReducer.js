const initialState = {
  loading: false,
  open: false,
}

const sharedReducer = (state = initialState, { type }) => {
  switch (type) {
    case 'SHARED/TURN_ON_LOADER':  return { ...state, loading: true }
    case 'SHARED/TURN_OFF_LOADER': return { ...state, loading: false }

    case 'SHARED/TOGGLE_HAMBURGER': return { ...state, open: !state.open }
    case 'SHARED/TURN_OFF_HAMBURGER': return { ...state, open: false }
    default: return state;
  }
}

export default sharedReducer;
