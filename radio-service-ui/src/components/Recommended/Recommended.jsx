import React from 'react';
import axios from 'axios';

import PropTypes from 'prop-types';
import { connect } from 'react-redux';
import InfiniteScroll from 'react-infinite-scroller';

import PaginationLoader from '../PaginationLoader';

import * as actions from '../../redux/actions';

import './Recommended.scss'

const RECOMENDED_URL = 'https://radio-service-api-stage.herokuapp.com/api/v1/videos/recommended?page='
const DESCRIPTION_LENGTH = 1000;
const BG_URL = 'https://radio-service-api-stage.herokuapp.com/api/v1/backgrounds'

class Recommended extends React.Component {
  constructor(props) {
    super(props);

    this.fetchBackground();

    this.fetchBackground = this.fetchBackground.bind(this);
    this.fetchVideos = this.fetchVideos.bind(this);
  }

  fetchBackground() {
    this.props.turnLoadingOn();

    axios.get(BG_URL)
    .then((response) => {
      this.props.turnLoadingOff();
      document.getElementById('content').style.backgroundImage = "url('" + response.data.recommendedVideosPage + "')";
    })
    .catch((errors) => {
      this.props.turnLoadingOff();
      console.error(errors)
    });
  }

  fetchVideos() {
    console.log(this.props.currentPage)
    this.props.turnLoadingOn();

    axios.get(RECOMENDED_URL + this.props.currentPage)
    .then((response) => {
      this.props.turnLoadingOff();
      let videos = this.props.recommended;

      response.data.content.map(video => videos.push(video));

      if(response.data.pageNumber !== response.data.totalPages - 1) {
        console.log('setting...')
        this.props.setRecommended(videos, this.props.currentPage + 1)
        console.log(this.props.currentPage)
      } else {
        this.props.setHasMore(false);
      }
    })
    .catch((errors) => {
      this.props.turnLoadingOff();
      console.error(errors)
    });
  }

  render() {
    const { recommended, currentPage, hasMore } = this.props;

    const renderVideo = video => {
      return (
        <div className="video-card mb-4 p-3" key={video.id}>
          <h3 className="title mb-3 pb-2">{video.title}</h3>
          <div className="d-flex">
            <iframe width="560"
                    height="315"
                    src={"https://www.youtube.com/embed/" + video.id}
                    frameBorder="0"
                    allow="accelerometer; autoplay; encrypted-media; gyroscope; picture-in-picture"
                    allowFullScreen></iframe>
            <div className="description ml-2">{video.description.substring(0, DESCRIPTION_LENGTH)}</div>
          </div>
        </div>
      )
    };

    return (
      <div className="recommended-container">
        <InfiniteScroll
          pageStart={0}
          loadMore={this.fetchVideos}
          hasMore={this.props.hasMore}
          loader={<PaginationLoader key={currentPage} />}
          useWindow={true}
          // getScrollParent={() => this.props.scrollParentRef}
        >
          { recommended.length > 0 && recommended.map(video => renderVideo(video)) }
        </InfiniteScroll>
      </div>
    )
  }
}

const mapStateToProps = state => {
  return {
    currentPage: state.videos.currentPage,
    recommended: state.videos.recommended,
    hasMore:     state.videos.hasMore,
  }
};

const mapDispatchToProps = dispatch => ({
  setRecommended: (recommended, currentPage) => dispatch(actions.setRecommended(recommended, currentPage)),
  setHasMore:     bool                       => dispatch(actions.setHasMore(bool)),
  turnLoadingOn:  ()                         => dispatch(actions.turnLoadingOn()),
  turnLoadingOff: ()                         => dispatch(actions.turnLoadingOff()),
});

Recommended.propTypes = {
  currentPage: PropTypes.number,
  recommended: PropTypes.array,
  hasMore:     PropTypes.bool,
}

export default connect(mapStateToProps, mapDispatchToProps)(Recommended);
