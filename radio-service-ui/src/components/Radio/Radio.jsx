import React from 'react';
import axios from 'axios';

import PropTypes from 'prop-types';
import { connect } from 'react-redux';

import * as actions from '../../redux/actions';

import RadioPlayer from './RadioPlayer';

import clock from '../../images/clock.png'

import './Radio.scss'

const TODAY_PROGRAM_LINK = process.env.REACT_APP_SITE_URL + '/api/v1/schedule/today'
const BG_URL = process.env.REACT_APP_SITE_URL + '/api/v1/backgrounds'

class Radio extends React.Component {
  constructor(props) {
    super(props);

    this.fetchBackground();
    this.fetchTodayPrograms();

    this.fetchBackground = this.fetchBackground.bind(this);
    this.fetchTodayPrograms = this.fetchTodayPrograms.bind(this);
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
      this.props.setTodayPrograms(response.data.scheduleItems);
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
                { programs && programs.length > 0 && programs.map(item => {
                  return(
                    <div className="d-flex program flex-column flex-lg-row" key={item.id}>
                      <div className="duration d-flex flex-row flex-lg-column">
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
    open: state.shared.open,
  }
};

const mapDispatchToProps = dispatch => ({
  turnOffHamburger: () => dispatch(actions.turnOffHamburger()),
  setTodayPrograms: programs => dispatch(actions.setTodayPrograms(programs)),
  turnLoadingOn:   () => dispatch(actions.turnLoadingOn()),
  turnLoadingOff:  () => dispatch(actions.turnLoadingOff()),
});

Radio.propTypes = {
  programs: PropTypes.array,
  open: PropTypes.bool,
}

export default connect(mapStateToProps, mapDispatchToProps)(Radio);
