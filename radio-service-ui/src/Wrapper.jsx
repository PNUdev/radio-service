import React from 'react';
import PropTypes from 'prop-types';
import axios from 'axios';

import * as actions from './redux/actions';

import {
  BrowserRouter as Router,
  Switch,
  Route,
  Redirect,
} from "react-router-dom";

import { connect } from 'react-redux';

import SideBar     from './components/Sidebar';
import Radio       from "./components/Radio";
import Recent      from "./components/Recent";
import Scheduler   from './components/Scheduler';
import Recommended from './components/Recommended';
import Programs    from './components/Programs';

import bg from './images/main.jpg'
import './Wrapper.scss';

const BACKGROUNDS_URL = 'https://radio-service-api-stage.herokuapp.com/api/v1/backgrounds'

class Wrapper extends React.Component {
  constructor(props) {
    super(props)

    this.fetchBackGrounds()
    this.fetchBackGrounds = this.fetchBackGrounds.bind(this)
  }

  fetchBackGrounds() {
    this.props.turnLoadingOn();

    axios.get(BACKGROUNDS_URL)
    .then((response) => {
      this.props.turnLoadingOff();
      this.props.setBackGrounds(response.data)
    })
    .catch((errors) => {
      this.props.turnLoadingOff();
      console.error(errors)
    });
  }

  render() {
    const { loading, backgrounds } = this.props;

    const bg_styles = {
      backgroundImage: `url(${bg})`,
      backgroundPosition: 'right',
      backgroundSize: 'cover',
      backgroundRepeat: 'no-repeat'
    }

    return (
      <Router>
        <div className="wrapper d-flex ">
          <div className="sidebar">
            <SideBar />
          </div>

          <div id="content" className="content" style={bg_styles}>
            <div className="bg-wrapper">
              <Switch>
                <Route path="/" exact>
                  <Redirect to="/radio" />
                </Route>

                <Route path="/radio"       component={Radio}       bg={backgrounds.radioPage} />
                <Route path="/recent"      component={Recent}      bg={backgrounds.recentVideosPage}/>
                <Route path="/scheduler"   component={Scheduler}   bg={backgrounds.schedulePage} />
                <Route path="/programs"    component={Programs}    bg={backgrounds.programsPage} />
                <Route path="/recommended" component={Recommended} bg={backgrounds.recommendedVideosPage} recommended={true} />
              </Switch>
            </div>
          </div>

          {loading && <div className="loader-container"><div className="loader"></div></div>}
        </div>
      </Router>
    )
  }
}

const mapStateToProps = state => {
  return {
    loading:     state.shared.loading,
    backgrounds: state.shared.backgrounds,
  }
};

const mapDispatchToProps = dispatch => ({
  setBackGrounds: backgrounds => dispatch(actions.setBackGrounds(backgrounds)),
  turnLoadingOn:  ()          => dispatch(actions.turnLoadingOn()),
  turnLoadingOff: ()          => dispatch(actions.turnLoadingOff()),
});

Wrapper.propTypes = {
  loading:     PropTypes.bool,
  backgrounds: PropTypes.object,
};

export default connect(mapStateToProps, mapDispatchToProps)(Wrapper);
