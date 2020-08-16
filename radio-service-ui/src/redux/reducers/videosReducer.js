const initialState = {
  recommended: [],
  currentPage: 0,
  hasMore: true,
  recent: [],
}

const videosReducer = (state = initialState, {
  type,
  recent,
  recommended,
  currentPage,
  hasMore,
}) => {
  switch(type) {
    case 'VIDEOS/SET_RECOMMENDED': return { ...state, recommended, currentPage };
    case 'VIDEOS/SET_HAS_MORE':    return { ...state, hasMore };
    case 'VIDEOS/SET_RECENT':      return { ...state, recent };

    default: return state;
  }
}

export default videosReducer
