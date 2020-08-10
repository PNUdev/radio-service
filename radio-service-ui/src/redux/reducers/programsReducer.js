const initialState = {
  programs: [],
  currentPage: 0,
  totalPages: 0,
}

const programsReducer = (state = initialState, { type, programs, currentPage, totalPages  }) => {
  switch (type) {
    case 'PROGRAMS/SET_PROGRAMS':  return { ...state, programs, currentPage, totalPages }
    default: return state;
  }
}

export default programsReducer;
