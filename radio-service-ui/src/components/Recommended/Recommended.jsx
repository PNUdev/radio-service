import React from 'react';
import axios from 'axios';

import PropTypes from 'prop-types';
import { connect } from 'react-redux';
import InfiniteScroll from 'react-infinite-scroller';

import PaginationLoader from '../PaginationLoader';

import * as actions from '../../redux/actions';

import './Recommended.scss'

const RECOMENDED_URL = 'https://radio-service-api-stage.herokuapp.com/api/v1/videos/recommended'
const DESCRIPTION_LENGTH = 1000;

class Recommended extends React.Component {
  constructor(props) {
    super(props);

    this.fetchVideos(RECOMENDED_URL, 0, true)
    this.fetchNext = this.fetchNext.bind(this);
    this.fetchVideos = this.fetchVideos.bind(this);
  }

  fetchVideos(url, currentPage = 0, initial=false) {
    initial && this.props.turnLoadingOn();

    axios.get(url + "?page=" + currentPage)
    .then((response) => {
      this.props.turnLoadingOff();

      this.props.setRecommended(
        response.data.content,
        response.data.pageNumber,
        response.data.totalPages,
      );
    })
    .catch((errors) => {
      initial && this.props.turnLoadingOff();
      console.error(errors)
    });
  }

  fetchNext(page) {
    console.log('fetching')
    this.fetchVideos(RECOMENDED_URL, page, false)
  }

  render() {
    const { recommended, currentPage, totalPages, } = this.props;

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
          loadMore={this.fetchNext}
          hasMore={totalPages > currentPage + 1}
          loader={<PaginationLoader key={currentPage} />}
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
    totalPages:  state.videos.totalPages,
    recommended: state.videos.recommended,
    bg:          state.shared.bg,
  }
};

const mapDispatchToProps = dispatch => ({
  setRecommended: (recommended, currentPage, totalPages) => dispatch(actions.setRecommended(recommended, currentPage, totalPages)),
  // setBg:          bg                                => dispatch(actions.setBg(bg)),
  turnLoadingOn:  ()                                     => dispatch(actions.turnLoadingOn()),
  turnLoadingOff: ()                                     => dispatch(actions.turnLoadingOff()),
});

Recommended.propTypes = {
  currentPage: PropTypes.number,
  totalPages:  PropTypes.number,
  recommended:      PropTypes.array,
  // bg:          PropTypes.string,
}

export default connect(mapStateToProps, mapDispatchToProps)(Recommended);
