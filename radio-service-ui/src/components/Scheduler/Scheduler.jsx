import React from 'react';
import axios from 'axios';

import ReactTooltip from 'react-tooltip';
import PropTypes from 'prop-types';
import { connect } from 'react-redux';

import * as actions from '../../redux/actions';

import './Scheduler.scss';

import clock from '../../images/clock.png'
import program_image from '../../images/menu-items/programs-active.png';

const SCHEDULE_URL = process.env.REACT_APP_SITE_URL + '/api/v1/schedule/week'
const SITE_URL = process.env.REACT_APP_SITE_URL
const BG_URL = process.env.REACT_APP_SITE_URL + '/api/v1/backgrounds'

class Scheduler extends React.Component {
  constructor(props) {
    super(props);
    this.fetchBackground();
    this.fetchSchedule();

    this.fetchBackground = this.fetchBackground.bind(this);
    this.fetchSchedule = this.fetchSchedule.bind(this);
    this.fetchPrograms = this.fetchPrograms.bind(this);
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
      document.getElementById('content').style.backgroundImage = "url('" + response.data.schedulePage + "')";
    })
    .catch((errors) => {
      this.props.turnLoadingOff();
      console.error(errors)
    });
  }

  fetchPrograms() {
    const items = Object.keys(this.props.schedule)
      .map(day => this.props.schedule[day].scheduleItems)
      .flat();

    const programs = items.map(item => axios.get(SITE_URL + item.programLink))

    this.props.setEmbeddedPrograms(Promise.allSettled(programs))
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

    // const dayNamesShort = {
    //   'Понеділок': 'пн',
    //   'Вівторок': 'вт',
    //   'Середа': 'ср',
    //   'Четвер': 'чт',
    //   "П'ятниця": 'пт',
    //   'Субота': 'сб',
    //   'Неділя': 'нд',
    // }

    // const renderOccurrence = (occurence, last=false) => {
    //   return(
    //     <div className="occurence" key={occurence.dayOfWeek.nameUkr}>
    //       <span className="day">{dayNamesShort[occurence.dayOfWeek.nameUkr]}</span>
    //       <span className="time">
    //         ({occurence.timeRange.startTime} - {occurence.timeRange.endTime}){!last && "," + String.fromCharCode(160)}
    //       </span>
    //     </div>
    //   )
    // }

    const renderProgramTooltip = programLink => {
      const program = axios.get(SITE_URL + programLink).then(response => response.data)
      return (
        <div className="tooltip-wrapper d-flex">
          {
            program &&
            <>
              <img src={program.imageUrl} alt=""/>
              <div className="d-flex-flex-column">
                <div className="description ml-2">
                  {program.description > 50 ? (program.description.substr(0, 50) + '...') : program.description}
                </div>
                <div className="occurences ml-2 d-flex">
                  {/* {
                    program.scheduleOccurrences &&
                    program.scheduleOccurrences.map(occurence => renderOccurrence(occurence, (program.scheduleOccurrences.indexOf(occurence) == program.scheduleOccurrences.length - 1)))
                  } */}
                </div>
              </div>
            </>
          }
        </div>
      )
    }

    const renderScheduleDay = (key, day) => {
      return(
        <>
        {
          day &&
          <div className="scheduler-day-card" key={key}>
            <h1 className="d-flex justify-content-center">{day["dayOfWeek"]["nameUkr"]}</h1>

            <div className="time-table">
              {day["scheduleItems"].length === 0 && <h3 className='no-programs'>Немає запланованих програм на цей день</h3>}

              {day["scheduleItems"].map(item => {
                return(
                  <div className="d-flex program flex-column flex-lg-row" key={item.id}>
                    <div className="duration  d-flex flex-row flex-lg-column">
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


                        <div className="d-none d-lg-block">
                          <div data-tip="React-tooltip" className="program-tooltip d-flex justify-content-center align-items-center ml-2">
                            <img src={program_image} alt=""/>
                          </div>
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
          }
        </>
      )
    }

    return (
      <div className="scheduler-page">
        {!loading && schedule &&
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
    programs: state.schedule.programs,
    open: state.shared.open,
  }
};

const mapDispatchToProps = dispatch => ({
  turnOffHamburger: () => dispatch(actions.turnOffHamburger()),
  setSchedule: schedule => dispatch(actions.setSchedule(schedule)),
  setTooltips: tooltips => dispatch(actions.setTooltips(tooltips)),
  setEmbeddedPrograms: programs => dispatch(actions.setEmbeddedPrograms(programs)),
  turnLoadingOn:  () => dispatch(actions.turnLoadingOn()),
  turnLoadingOff: () => dispatch(actions.turnLoadingOff()),

});

Scheduler.propTypes = {
  schedule: PropTypes.object,
  tooltips: PropTypes.object,
  programs: PropTypes.object,

  loading: PropTypes.bool,
  open: PropTypes.bool,
}

export default connect(mapStateToProps, mapDispatchToProps)(Scheduler);
