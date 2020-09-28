export const turnLoadingOn    = () => ({ type: 'SHARED/TURN_ON_LOADER' })
export const turnLoadingOff   = () => ({ type: 'SHARED/TURN_OFF_LOADER' })
export const toggleHamburger  = () => ({ type: 'SHARED/TOGGLE_HAMBURGER' })
export const turnOffHamburger = () => ({ type: 'SHARED/TURN_OFF_HAMBURGER' })

export const setTodayPrograms    = programs                            => ({ type: 'RADIO/SET_TODAY_PROGRAMS', programs });
export const setRecent           = recent                              => ({ type: 'VIDEOS/SET_RECENT',        recent });
export const setSchedule         = schedule                            => ({ type: 'SCHEDULE/SET_SCHEDULE',    schedule });
export const setEmbeddedPrograms = programs                            => ({ type: 'SHEDULE/SET_PROGRAMS',     programs })
export const setTooltips         = tooltips                            => ({ type: 'SCHEDULE/SET_TOOLTIPS',    tooltips })
export const setRecommended      = (recommended, currentPage)          => ({ type: 'VIDEOS/SET_RECOMMENDED',   recommended, currentPage });
export const setHasMore          = hasMore                             => ({ type: 'VIDEOS/SET_HAS_MORE',      hasMore })
export const setPrograms         = (programs, currentPage, totalPages) => ({ type: 'PROGRAMS/SET_PROGRAMS',    programs, currentPage, totalPages });

export const setMainBanner       = mainBanner => ({ type: 'BANNERS/SET_MAIN_BANNER', mainBanner });
export const setSecondaryBanner  = secondaryBanner => ({ type: 'BANNERS/SET_SECONDARY_BANNER', secondaryBanner });
export const setAdditionalBanner = additionalBanner => ({ type: 'BANNERS/SET_ADDITIONAL_BANNER', additionalBanner });
