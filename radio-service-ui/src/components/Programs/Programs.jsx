import React from 'react';
import axios from 'axios';

import PropTypes from 'prop-types';
import { connect } from 'react-redux';
import InfiniteScroll from 'react-infinite-scroller';

import PaginationLoader from '../PaginationLoader';

import * as actions from '../../redux/actions';
import { PROGRAMS_URL, BG_URL } from '../shared/endpointConstants';

import './Programs.scss'

const DESCRIPTION_LENGTH = 1000;

class Programs extends React.Component {
  constructor(props) {
    super(props);

    this.fetchBackground();
    this.fetchPrograms();

    this.fetchBackground = this.fetchBackground.bind(this);
    this.fetchPrograms = this.fetchPrograms.bind(this);
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
      document.getElementById('content').style.backgroundImage = "url('" + response.data.programsPage + "')";
    })
    .catch((errors) => {
      this.props.turnLoadingOff();
      console.error(errors)
    });
  }

  fetchPrograms() {
    axios.get(PROGRAMS_URL + "?page=" + 0)
    .then((response) => {
      this.props.turnLoadingOff();

      this.props.setPrograms(
        response.data.content,
        response.data.pageNumber,
        response.data.totalPages,
      );
    })
    .catch((errors) => {
      this.props.turnLoadingOff();
      console.error(errors)
    });
  }

  render() {
    const { programs, currentPage, totalPages, } = this.props;

    const dayNamesShort = {
      'Понеділок': 'пн',
      'Вівторок':  'вт',
      'Середа':    'ср',
      'Четвер':    'чт',
      "П'ятниця":  'пт',
      'Субота':    'сб',
      'Неділя':    'нд',
    }

    const renderOccurrence = (occurence, last=false) => {
      return (
        <div className="occurence" key={occurence["dayOfWeek"]["nameUkr"]}>
          <span className="day">{dayNamesShort[occurence["dayOfWeek"]["nameUkr"]]}</span>

          <span className="time">
            ({occurence["timeRange"]["startTime"]} - {occurence["timeRange"]["endTime"]}){!last && "," + String.fromCharCode(160)}
          </span>
        </div>
      )
    }

    const renderProgram = program => {
      let occurences = program['scheduleOccurrences']

      return (
        <div className="program-card mb-4 p-3" key={program.id}>
          <h3 className="title mb-3 pb-2 d-flex flex-column">
            <div className="mb-2">
              {program.title}
            </div>

            <div className="occurences d-flex flex-wrap">
              {
                occurences.length > 0 &&
                occurences.map(occurence => renderOccurrence(occurence, (occurences.indexOf(occurence) === occurences.length -1)))
              }
            </div>
          </h3>

          <div className="d-flex flex-column program-body">
            <img src={program.imageUrl} alt="" className="mb-2 mb-lg-0 image-fluid"/>
            <div className="description ml-2">{program.description.substring(0, DESCRIPTION_LENGTH)}</div>
          </div>
        </div>
      )
    }

    return (
      <div className="programs-container">
        {/* <InfiniteScroll
          pageStart={0}
          loadMore={this.fetchNext}
          hasMore={totalPages > currentPage + 1}
          loader={<PaginationLoader key={currentPage} />}
        > */}
          { programs && programs.length > 0 && programs.map(program => renderProgram(program)) }
        {/* </InfiniteScroll> */}
      </div>
    )
  }
}

const mapStateToProps = state => {
  return {
    currentPage: state.programs.currentPage,
    totalPages:  state.programs.totalPages,
    programs:    state.programs.programs,
    open:        state.shared.open,

  }
};

const mapDispatchToProps = dispatch => ({
  setPrograms: (programs,
               currentPage,
               totalPages) => dispatch(actions.setPrograms(programs, currentPage, totalPages)),

  turnOffHamburger: () => dispatch(actions.turnOffHamburger()),
  turnLoadingOn:    () => dispatch(actions.turnLoadingOn()),
  turnLoadingOff:   () => dispatch(actions.turnLoadingOff()),
});

Programs.propTypes = {
  currentPage: PropTypes.number,
  totalPages:  PropTypes.number,
  programs:    PropTypes.array,
  open:        PropTypes.bool,

  turnOffHamburger: PropTypes.func,
  turnLoadingOff:   PropTypes.func,
  turnLoadingOn:    PropTypes.func,
}

export default connect(mapStateToProps, mapDispatchToProps)(Programs);
