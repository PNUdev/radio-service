const initialState = {
  mainBanner: '',
  secondaryBanner: '',
  additionalBanner: '',
}

const bannersReducer = (state = initialState, {
  type,
  mainBanner,
  secondaryBanner,
  additionalBanner,
}) => {
  switch (type) {
    case 'BANNERS/SET_MAIN_BANNER':        return { ...state, mainBanner }
    case 'BANNERS/SET_SECONDARY_BANNER':   return { ...state, secondaryBanner }
    case 'BANNERS/SET_ADDITIONAL_BANNER':  return { ...state, additionalBanner }

    default: return state;
  }
}

export default bannersReducer;
