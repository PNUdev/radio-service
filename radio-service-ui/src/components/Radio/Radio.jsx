import React from 'react';
import axios from 'axios';

import PropTypes from 'prop-types';
import { connect } from 'react-redux';

import * as actions from '../../redux/actions';

import RadioPlayer from './RadioPlayer';

import clock from '../../images/clock.png'

import './Radio.scss'

const TODAY_PROGRAM_LINK = 'https://radio-service-api-stage.herokuapp.com/api/v1/schedule/today'
const BG_URL = 'https://radio-service-api-stage.herokuapp.com/api/v1/backgrounds'

class Radio extends React.Component {
  constructor(props) {
    super(props);

    this.fetchBackground();
    this.fetchTodayPrograms();

    this.fetchBackground = this.fetchBackground.bind(this);
    this.fetchTodayPrograms = this.fetchTodayPrograms.bind(this);
  }

  fetchBackground() {
    this.props.turnLoadingOn();

    axios.get(BG_URL)
    .then((response) => {
      this.props.turnLoadingOff();
      document.getElementById('content').style.backgroundImage = "url('" + response.data.radioPage + "')";
    })
    .catch((errors) => {
      this.props.turnLoadingOff();
      console.error(errors)
    });
  }

  fetchTodayPrograms() {
    this.props.turnLoadingOn();

    axios.get(TODAY_PROGRAM_LINK)
    .then((response) => {
      this.props.turnLoadingOff();

      let scheduleItems = {
        "scheduleItems": [
          {
              "id": "5f174527b9fccf3bded8d924",
              "programName": "Lorem ipsum dolor",
              "programLink": "/api/v1/programs/5f174489b9fccf3bded8d920",
              "time": {
                  "startTime": "13:45",
                  "endTime": "14:46"
              },
              "comment": "",
              "dayOfWeek": {
                  "urlValue": "monday",
                  "nameUkr": "Понеділок"
              }
          },
          {
              "id": "5f174500b9fccf3bded8d923",
              "programName": "Моя програма",
              "programLink": "/api/v1/programs/5f16f1df5a96ee04f906fd91",
              "time": {
                  "startTime": "22:41",
                  "endTime": "22:42"
              },
              "comment": "",
              "dayOfWeek": {
                  "urlValue": "monday",
                  "nameUkr": "Понеділок"
              }
          }
      ]
      }
      this.props.setTodayPrograms(scheduleItems['scheduleItems']);
      // this.props.setTodayPrograms(response.data.scheduleItems);
    })
    .catch((errors) => {
      this.props.turnLoadingOff();
      console.error(errors)
    });
  }

  render() {
    const { programs } = this.props;

    return (
      <div className="radio-container d-flex flex-column justify-content-between h-100">
        <div className="player-container d-flex flex-column justify-content-between h-100">
          <RadioPlayer />

          <div className="scheduler-day-card mb-3">
            <h1 className="text-center mb-3">Розклад на сьогодні</h1>
            <div className="time-table">
              <div className="program-container">
                { programs.length > 0 && programs.map(item => {
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
          </div>
        </div>
      </div>
    )
  }
}

const mapStateToProps = state => {
  return {
    programs: state.radio.programs,
  }
};

const mapDispatchToProps = dispatch => ({
  setTodayPrograms: programs => dispatch(actions.setTodayPrograms(programs)),
  turnLoadingOn:   () => dispatch(actions.turnLoadingOn()),
  turnLoadingOff:  () => dispatch(actions.turnLoadingOff()),
});

Radio.propTypes = {
  programs: PropTypes.array,
}

export default connect(mapStateToProps, mapDispatchToProps)(Radio);
