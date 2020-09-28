import React from 'react';
import axios from 'axios';

import PropTypes from 'prop-types';
import { connect } from 'react-redux';

import * as actions from '../../../redux/actions';
import { extractBannerLink } from '../../../utils';


import Video from '../Video';

const RECENT_URL = process.env.REACT_APP_SITE_URL + '/api/v1/videos/recent'
const BG_URL = process.env.REACT_APP_SITE_URL + '/api/v1/backgrounds'
const BANNER_LINK = process.env.REACT_APP_SITE_URL + '/api/v1/banners'


class Recent extends React.Component {
  constructor(props) {
    super(props);

    this.fetchBackground();
    this.fetchVideos(RECENT_URL);
    this.fetchBanner();

    this.fetchBackground = this.fetchBackground.bind(this);
    this.fetchVideos = this.fetchVideos.bind(this);
    this.fetchBanner = this.fetchBanner.bind(this)
  }

  componentDidMount() {
    if(this.props.open){
      document.getElementById('menu').style.width = '0%';
      document.body.style.overflow = 'visible';
      document.querySelector('.toggle').classList.remove('active');
      this.props.turnOffHamburger();
    }
  }

  fetchBackground() {
    this.props.turnLoadingOn();

    axios.get(BG_URL)
    .then((response) => {
      this.props.turnLoadingOff();
      document.getElementById('content').style.backgroundImage = "url('" + response.data.recentVideosPage + "')";
    })
    .catch((errors) => {
      this.props.turnLoadingOff();
      console.error(errors)
    });
  }

  fetchVideos() {
    this.props.turnLoadingOn();

    axios.get(RECENT_URL)
    .then((response) => {
      this.props.turnLoadingOff();
      this.props.setRecent(response.data.recent);
    })
    .catch((errors) => {
      this.props.turnLoadingOff();
      console.error(errors)
    });
  }

  fetchBanner() {
    this.props.turnLoadingOn();

    axios.get(BANNER_LINK)
    .then((response) => {
      this.props.turnLoadingOff();
      this.props.setAdditionalBanner(extractBannerLink(response.data["main-banner"]))
    })
    .catch((errors) => {
      this.props.turnLoadingOff();
      console.error(errors)
    })
  }

  render() {
    const { recent, additionalBanner } = this.props;

    return (
      <div className="recent-container">
        {recent && recent.length > 0 && recent.map(video => {
          return (
            <>
              <Video video={video} key={video.id} />
              {
                recent.indexOf(video) == 0 &&
                <img src={additionalBanner} alt="" className="additional-banner my-3" />
              }
            </>
          )
        })}
      </div>
    )
  }
}

const mapStateToProps = state => {
  return {
    recent: state.videos.recent,
    open:   state.shared.open,
    additionalBanner: state.banners.additionalBanner,
  }
};

const mapDispatchToProps = dispatch => ({
  setRecent:      recent => dispatch(actions.setRecent(recent)),
  setAdditionalBanner: banner => dispatch(actions.setAdditionalBanner(banner)),


  turnOffHamburger: () => dispatch(actions.turnOffHamburger()),
  turnLoadingOn:    () => dispatch(actions.turnLoadingOn()),
  turnLoadingOff:   () => dispatch(actions.turnLoadingOff()),
});

Recent.propTypes = {
  recent: PropTypes.array,
  open:   PropTypes.bool,
  additionalBanner: PropTypes.string,


  turnOffHamburger: PropTypes.func,
  turnLoadingOff:   PropTypes.func,
  turnLoadingOn:    PropTypes.func,
}

export default connect(mapStateToProps, mapDispatchToProps)(Recent);
