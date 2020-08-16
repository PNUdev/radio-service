import React from 'react';
import axios from 'axios';

import PropTypes from 'prop-types';
import { connect } from 'react-redux';

import InfiniteScroll from 'react-infinite-scroller';
import PaginationLoader from '../../../components/PaginationLoader';

import Video from '../Video';

import * as actions from '../../../redux/actions';

const RECOMENDED_URL = process.env.REACT_APP_SITE_URL + '/api/v1/videos/recommended?page='
const BG_URL = process.env.REACT_APP_SITE_URL + '/api/v1/backgrounds'

class Recommended extends React.Component {
  constructor(props) {
    super(props);

    this.fetchBackground();
    this.fetchVideos();

    this.fetchBackground = this.fetchBackground.bind(this);
    this.fetchVideos = this.fetchVideos.bind(this);
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
      document.getElementById('content').style.backgroundImage = "url('" + response.data.recommendedVideosPage + "')";
    })
    .catch((errors) => {
      this.props.turnLoadingOff();
      console.error(errors)
    });
  }

  fetchVideos() {
    this.props.turnLoadingOn();

    axios.get(RECOMENDED_URL + this.props.currentPage)
    .then(response => {
      this.props.turnLoadingOff();
      // const videos = [...this.props.recommended, ...response.data.content];

      // console.log(response.data.pageNumber, response.data.totalPages)
      // if(response.data.pageNumber < response.data.totalPages) {
        this.props.setRecommended(response.data.content, this.props.currentPage)
      // }
      // if(response.data.pageNumber  >= response.data.totalPages-1) {
      //   this.props.setHasMore(false);
      // }
    })
  }

  render() {
    const { recommended, currentPage, hasMore } = this.props;

    return (
      <div className="recommended-container">
        {/* <InfiniteScroll
          pageStart={0}
          loadMore={this.fetchVideos}
          hasMore={hasMore}
          loader={<PaginationLoader key={currentPage} />}
          useWindow={true}
          getScrollParent={() => this.props.scrollParentRef}
        > */}
          { recommended && recommended.length > 0 && recommended.map(video => <Video video={video} key={video.id} />) }
        {/* </InfiniteScroll> */}
      </div>
    )
  }
}

const mapStateToProps = state => {
  return {
    currentPage: state.videos.currentPage,
    recommended: state.videos.recommended,
    hasMore:     state.videos.hasMore,

    open:        state.shared.open,
  }
};

const mapDispatchToProps = dispatch => ({
  setRecommended: (recommended,
                  currentPage) => dispatch(actions.setRecommended(recommended, currentPage)),
  setHasMore:     bool         => dispatch(actions.setHasMore(bool)),

  turnOffHamburger: () => dispatch(actions.turnOffHamburger()),
  turnLoadingOn:    () => dispatch(actions.turnLoadingOn()),
  turnLoadingOff:   () => dispatch(actions.turnLoadingOff()),
});

Recommended.propTypes = {
  currentPage: PropTypes.number,
  recommended: PropTypes.array,
  hasMore:     PropTypes.bool,
  open:        PropTypes.bool,

  turnOffHamburger: PropTypes.func,
  turnLoadingOff:   PropTypes.func,
  turnLoadingOn:    PropTypes.func,
}

export default connect(mapStateToProps, mapDispatchToProps)(Recommended);
