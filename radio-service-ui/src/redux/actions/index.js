export const setBackGrounds = backgrounds => ({ type: 'SHARED/SET_BACKGROUNDS', backgrounds })

export const turnLoadingOn  = () => ({ type: 'SHARED/TURN_ON_LOADER' })
export const turnLoadingOff = () => ({ type: 'SHARED/TURN_OFF_LOADER' })

export const setRecommended = (recommended, currentPage, totalPages) => ({ type: 'VIDEOS/SET_RECOMMENDED', recommended, currentPage, totalPages });
export const setRecent = recent => ({ type: 'VIDEOS/SET_RECENT', recent });
