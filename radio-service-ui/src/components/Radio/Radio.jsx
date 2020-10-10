import React from 'react';
import axios from 'axios';

import PropTypes from 'prop-types';
import { connect } from 'react-redux';
import ReactMarkdown from "react-markdown";

import RadioPlayer from './RadioPlayer';

import * as actions from '../../redux/actions';

import clock from '../../images/clock.png'

import './Radio.scss'

const TODAY_PROGRAM_LINK = process.env.REACT_APP_SITE_URL + '/api/v1/schedule/today'
const BANNER_LINK = process.env.REACT_APP_SITE_URL + '/api/v1/banners'
const BG_URL = process.env.REACT_APP_SITE_URL + '/api/v1/backgrounds'

class Radio extends React.Component {
  constructor(props) {
    super(props);

    this.fetchBackground();
    this.fetchTodayPrograms();
    this.fetchBanner();

    this.fetchBackground = this.fetchBackground.bind(this);
    this.fetchTodayPrograms = this.fetchTodayPrograms.bind(this);
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

  fetchBanner() {
    this.props.turnLoadingOn();

    axios.get(BANNER_LINK)
    .then((response) => {
      this.props.turnLoadingOff();
      this.props.setSecondaryBanner(response.data["secondary-banner"])
    })
    .catch((errors) => {
      this.props.turnLoadingOff();
      console.error(errors)
    })
  }

  render() {
    const { programs, secondaryBanner } = this.props;

    return (
      <div className="radio-container d-flex flex-column justify-content-between h-100">
        <div className="player-container d-flex flex-column justify-content-between h-100">
          <RadioPlayer />

          <div className="secondary-banner my-3">
            <ReactMarkdown source={secondaryBanner}/>
          </div>

          <div className="scheduler-day-card mb-3">
            <h1 className="text-center mb-3">
              {programs.length == 0 ?'Розклад на сьогоднішній день відсутній' : 'Розклад на сьогодні'}
            </h1>

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
    open:     state.shared.open,

    secondaryBanner: state.banners.secondaryBanner,
  }
};

const mapDispatchToProps = dispatch => ({
  setTodayPrograms:   programs => dispatch(actions.setTodayPrograms(programs)),
  setSecondaryBanner: banner   => dispatch(actions.setSecondaryBanner(banner)),

  turnOffHamburger: () => dispatch(actions.turnOffHamburger()),
  turnLoadingOn:    () => dispatch(actions.turnLoadingOn()),
  turnLoadingOff:   () => dispatch(actions.turnLoadingOff()),
});

Radio.propTypes = {
  programs: PropTypes.array,
  open:     PropTypes.bool,
  secondaryBanner: PropTypes.string,

  setTodayPrograms:   PropTypes.func,
  setSecondaryBanner: PropTypes.func,

  turnOffHamburger: PropTypes.func,
  turnLoadingOff:   PropTypes.func,
  turnLoadingOn:    PropTypes.func,
}

export default connect(mapStateToProps, mapDispatchToProps)(Radio);
