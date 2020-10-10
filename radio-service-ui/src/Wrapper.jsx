import React from 'react';
import PropTypes from 'prop-types';

import {
  BrowserRouter as Router,
  Switch,
  Route,
  Redirect,
  Link,
} from "react-router-dom";

import { connect } from 'react-redux';

import * as actions from './redux/actions';

import SideBar     from './components/Sidebar';
import Radio       from "./components/Radio";
import Recent      from "./components/Videos/Recent";
import Scheduler   from './components/Scheduler';
import Recommended from './components/Videos/Recommended';
import Programs    from './components/Programs';

import InstallPWA from './components/InstallPWA'

import bg from './images/main.jpg'
import miniLogo from './images/logo-mini.png'

import './Wrapper.scss';

class Wrapper extends React.Component {
  constructor(props) {
    super(props)

    this.handleClick = this.handleClick.bind(this)
  }

  handleClick() {
    document.querySelector('.toggle').classList.toggle('active');
    this.props.toggleHamburger();

    if(this.props.open) {
      document.getElementById('menu').style.width = '0%';
      document.body.style.overflow = 'visible';
    }
  }

  componentDidUpdate() {
    if(this.props.open) {
      document.body.style.overflow = 'hidden';
      document.getElementById('menu').style.width = '100%';
    }
  }


  render() {
    const { loading } = this.props;

    const admin_regex = /^\/admin/

    if (admin_regex.test(window.location.pathname)) {
      return <div></div>
    } else {
      return (
        <Router>
          <div className="wrapper d-flex flex-column flex-lg-row ">
            <div id="menu" className="sidebar">
              <SideBar />
              <InstallPWA />
            </div>

            <div className="hamburger d-flex align-items-center justify-content-between d-lg-none px-3 py-2">
              <Link to="/radio" className="logo-link">
                <img src={miniLogo} alt="" />
              </Link>

              <div className="toggle" onClick={this.handleClick}></div>
            </div>

            <div id="content" className="content w-100 h-100">
              <div id="bg-wrapper" className="bg-wrapper" ref={(ref) => this.scrollParentRef = ref}>
                <Switch>
                  <Route path="/" exact>
                    <Redirect to="/radio" />
                  </Route>

                  <Route path="/radio"       component={Radio} />
                  <Route path="/recent"      component={Recent} />
                  <Route path="/schedule"    component={Scheduler} />
                  <Route path="/programs"    component={Programs} />
                  <Route path="/recommended" component={Recommended} parentRef={this.scrollParentRef} />

                  <Redirect to="/radio" />
                </Switch>
              </div>
            </div>

            {loading && <div className="loader-container"><div className="loader"></div></div>}
          </div>
        </Router>
      )
    }
  }
}

const mapStateToProps = state => {
  return {
    loading: state.shared.loading,
    open: state.shared.open,
  }
};

const mapDispatchToProps = dispatch => ({
  toggleHamburger: () => dispatch(actions.toggleHamburger()),
  turnLoadingOn:   () => dispatch(actions.turnLoadingOn()),
  turnLoadingOff:  () => dispatch(actions.turnLoadingOff()),
});

Wrapper.propTypes = {
  loading: PropTypes.bool,
  open: PropTypes.bool,
};

export default connect(mapStateToProps, mapDispatchToProps)(Wrapper);
