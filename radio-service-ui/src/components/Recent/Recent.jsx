import React from 'react';
import axios from 'axios';

import PropTypes from 'prop-types';
import { connect } from 'react-redux';

import * as actions from '../../redux/actions';

import './Recent.scss'

const RECENT_URL = 'https://radio-service-api-stage.herokuapp.com/api/v1/videos/recent'
const DESCRIPTION_LENGTH = 1000;
const BG_URL = 'https://radio-service-api-stage.herokuapp.com/api/v1/backgrounds'

class Recent extends React.Component {
  constructor(props) {
    super(props);

    this.fetchBackground();
    this.fetchVideos(RECENT_URL);

    this.fetchBackground = this.fetchBackground.bind(this);
    this.fetchVideos = this.fetchVideos.bind(this);
  }

  componentDidMount(){
    if(this.props.open){
      document.getElementById('menu').style.width = '0%';
      document.body.style.overflow = 'visible';
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
      console.log(response.data)
      this.props.setRecent(response.data.recent);
    })
    .catch((errors) => {
      this.props.turnLoadingOff();
      console.error(errors)
    });
  }

  render() {
    const { recent, } = this.props;

    const renderVideo = video => {
      return (
        <div className="video-card mb-4 p-3" key={video.id}>
          <h3 className="title mb-3 pb-2">{video.title}</h3>
          <div className="d-flex flex-column flex-lg-row">
            <iframe width="560"
                    height="315"
                    src={"https://www.youtube.com/embed/" + video.id}
                    frameBorder="0"
                    allow="accelerometer; autoplay; encrypted-media; gyroscope; picture-in-picture"
                    allowFullScreen
                    className="youtube-video"></iframe>
            <div className="description ml-2">{video.description.substring(0, DESCRIPTION_LENGTH)}</div>
          </div>
        </div>
      )
    };

    return (
      <div className="recent-container">
        {recent.length > 0 && recent.map(video => renderVideo(video))}
      </div>
    )
  }
}

const mapStateToProps = state => {
  return {
    recent: state.videos.recent,
    open: state.shared.open,

  }
};

const mapDispatchToProps = dispatch => ({
  turnOffHamburger: () => dispatch(actions.turnOffHamburger()),
  setRecent:      recent => dispatch(actions.setRecent(recent)),
  turnLoadingOn:  ()     => dispatch(actions.turnLoadingOn()),
  turnLoadingOff: ()     => dispatch(actions.turnLoadingOff()),
});

Recent.propTypes = {
  recent: PropTypes.array,
  open: PropTypes.bool,
}

export default connect(mapStateToProps, mapDispatchToProps)(Recent);
