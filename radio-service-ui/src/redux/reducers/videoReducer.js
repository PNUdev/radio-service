const initialState = {
  recommended: [],
  recent: [],
  currentPage: 0,
  totalPages: 0,
}

const videoReducer = (state = initialState, { type, recent, recommended, currentPage, totalPages } ) => {
  switch(type) {
    case 'VIDEOS/SET_RECOMMENDED': return { ...state, recommended, currentPage, totalPages };
    case 'VIDEOS/SET_RECENT': return { ...state, recent };

    default: return state;
  }
}

export default videoReducer
