import React from 'react';
import axios from 'axios';

import ReactTooltip from 'react-tooltip';
import PropTypes from 'prop-types';
import { connect } from 'react-redux';

import * as actions from '../../redux/actions';

import './Scheduler.scss';

import clock from '../../images/clock.png'
import program_image from '../../images/menu-items/programs-active.png';

const SCHEDULE_URL = 'https://radio-service-api-stage.herokuapp.com/api/v1/schedule/week'
const SITE_URL = 'https://radio-service-api-stage.herokuapp.com'
const BG_URL = 'https://radio-service-api-stage.herokuapp.com/api/v1/backgrounds'

let tooltips = {}

class Scheduler extends React.Component {
  constructor(props) {
    super(props);
    this.fetchBackground();
    this.fetchSchedule();

    this.fetchBackground = this.fetchBackground.bind(this);
    this.fetchSchedule = this.fetchSchedule.bind(this);
    this.fetchPrograms = this.fetchPrograms.bind(this);
  }

  // componentDidUpdate() {
  //   // !this.props.programs &&
  //   this.props.schedule && this.fetchPrograms();
  // }

  fetchBackground() {
    this.props.turnLoadingOn();

    axios.get(BG_URL)
    .then((response) => {
      this.props.turnLoadingOff();
      document.getElementById('content').style.backgroundImage = "url('" + response.data.schedulePage + "')";
    })
    .catch((errors) => {
      this.props.turnLoadingOff();
      console.error(errors)
    });
  }

  fetchPrograms() {

    const programs = Object.keys(this.props.schedule).map(day => {
      this.props.schedule[day].scheduleItems.map(item => {
        axios.get(SITE_URL + item.programLink)
        .then(response => {
          console.log(response)
          programs['id_' + response.data.id] = {
            description: response.data.description,
            imageUrl: response.data.imageUrl,
            scheduleOccurrences: response.data.scheduleOccurrences,
          }
          console.log(programs)
        })
      })
    })
    // console.log(programs)
    // console.log(Object.keys(programs))
  }

  fetchSchedule = () => {
    this.props.turnLoadingOn();

    axios.get(SCHEDULE_URL)
    .then((response) => {
      this.props.turnLoadingOff();
      this.props.setSchedule(response.data);
      this.fetchPrograms();
    })
    .catch((errors) => {
      this.props.turnLoadingOff();
      console.error(errors)
    });
  }

  render() {
    const { schedule, loading } = this.props;

    const daysOfWeek = ['monday', 'tuesday', 'wednesday', 'thursday', 'friday', 'saturday', 'sunday']

    const dayNamesShort = {
      'Понеділок': 'пн',
      'Вівторок': 'вт',
      'Середа': 'ср',
      'Четвер': 'чт',
      "П'ятниця": 'пт',
      'Субота': 'сб',
      'Неділя': 'нд',
    }

    const renderOccurrence = (occurence, last=false) => {
      return(
        <div className="occurence" key={occurence.dayOfWeek.nameUkr}>
          <span className="day">{dayNamesShort[occurence.dayOfWeek.nameUkr]}</span>
          <span className="time">
            ({occurence.timeRange.startTime} - {occurence.timeRange.endTime}){!last && "," + String.fromCharCode(160)}
          </span>
        </div>
      )
    }

    const renderProgramTooltip = programLink => {
      return (
        <div className="tooltip-wrapper d-flex">

          {/* {
            program &&
            <>
              <img src={program.imageUrl} alt=""/>
              <div className="d-flex-flex-column">
                <div className="description ml-2">
                  {program.description > 50 ? (program.description.substr(0, 50) + '...') : program.description}
                </div>
                <div className="occurences ml-2 d-flex">
                  {
                    program.scheduleOccurrences &&
                    program.scheduleOccurrences.map(occurence => renderOccurrence(occurence, (program.scheduleOccurrences.indexOf(occurence) == program.scheduleOccurrences.length - 1)))
                  }
                </div>
              </div>
            </>
          } */}
        </div>
      )
    }

    const renderScheduleDay = (key, day) => {
      return(
        <div className="scheduler-day-card" key={key}>
          <h1 className="d-flex justify-content-center">{day["dayOfWeek"]["nameUkr"]}</h1>

          <div className="time-table">
            {day["scheduleItems"].length === 0 && <h3 className='no-programs'>Немає запланованих програм на цей день</h3>}

            {day["scheduleItems"].map(item => {
              return(
                <div className="d-flex program" key={item.id}>
                  <div className="duration">
                    <div className="from h-50 p-3">
                      <img src={clock} alt=""/>
                      {item.time.startTime}
                    </div>

                    <div className="to h-50 p-3">
                      <img src={clock} alt=""/>
                      {item.time.endTime}
                    </div>
                  </div>

                  <div className="p-3 d-flex flex-column justify-content-center">
                    <div className="d-flex align-items-center">
                      <h3 className="d-flex">{item.programName}</h3>

                      <div data-tip="React-tooltip" className="program-tooltip d-flex justify-content-center align-items-center ml-2">
                        <img src={program_image} alt=""/>
                      </div>

                      <ReactTooltip place="top" type="dark" effect="solid">
                        {renderProgramTooltip(item.programLink)}
                      </ReactTooltip>
                    </div>

                    {
                      item.comment.length > 0 &&
                      <div className="description">{item.comment}</div>
                    }
                  </div>
                </div>
              )
            })}
          </div>
        </div>
      )
    }

    return (
      <div className="scheduler-page">
        {!loading &&
          Object.keys(schedule).length > 0 &&
          daysOfWeek.map(key => renderScheduleDay(key, schedule[key]))
        }
      </div>
    )
  }
}

const mapStateToProps = state => {
  return {
    schedule: state.schedule.schedule,
    tooltips: state.schedule.tooltips,
    loading: state.shared.loading,
    }
};

const mapDispatchToProps = dispatch => ({
  setSchedule:       schedule => dispatch(actions.setSchedule(schedule)),
  setTooltips:       tooltips => dispatch(actions.setTooltips(tooltips)),
  turnLoadingOn:  () => dispatch(actions.turnLoadingOn()),
  turnLoadingOff: () => dispatch(actions.turnLoadingOff()),

});

Scheduler.propTypes = {
  schedule: PropTypes.object,
  tooltips: PropTypes.object,
  loading: PropTypes.bool,
}

export default connect(mapStateToProps, mapDispatchToProps)(Scheduler);
