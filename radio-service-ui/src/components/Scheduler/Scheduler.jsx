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

class Scheduler extends React.Component {
  constructor(props) {
    super(props);

    this.fetchSchedule = this.fetchSchedule.bind(this);
    this.fetchSchedule();
  }

  fetchSchedule() {
    this.props.turnLoadingOn();

    axios.get(SCHEDULE_URL)
    .then((response) => {
      this.props.turnLoadingOff();
      this.props.setSchedule(response.data);
    })
    .catch((errors) => {
      this.props.turnLoadingOff();
      console.error(errors)
    });
  }
  render() {
    const { schedule } = this.props;

    const daysOfWeek = ['monday', 'tuesday', 'wednesday', 'thursday', 'friday', 'saturday', 'sunday']

    const renderProgramTooltip = id => {
    }

    const renderScheduleDay = (key, day) => {
      return(
        <div className="scheduler-day-card" key={key}>
          <h1 className="d-flex justify-content-center">{day["dayOfWeek"]["nameUkr"]}</h1>

          <div className="time-table">
            {day["scheduleItems"].length == 0 && <h3 className='no-programs'>Немає запланованих програм на цей день</h3>}

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
                        {renderProgramTooltip(item.id)}
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
        {
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
    }
};

const mapDispatchToProps = dispatch => ({
  setSchedule:             schedule => dispatch(actions.setSchedule(schedule)),
  turnLoadingOn:  ()       => dispatch(actions.turnLoadingOn()),
  turnLoadingOff: ()       => dispatch(actions.turnLoadingOff()),

});

Scheduler.propTypes = {
  schedule: PropTypes.object,
}

export default connect(mapStateToProps, mapDispatchToProps)(Scheduler);
