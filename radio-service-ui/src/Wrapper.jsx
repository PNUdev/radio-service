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
import { isSSL, isPWA } from './utils/checker';
import InstallButton from './components/InstallButton';
import InstallerWindow from './components/InstallerWindow';

import SideBar     from './components/Sidebar';
import Radio       from "./components/Radio";
import Recent      from "./components/Videos/Recent";
import Scheduler   from './components/Scheduler';
import Recommended from './components/Videos/Recommended';
import Programs    from './components/Programs';

import miniLogo from './images/logo-mini.png'

import './Wrapper.scss';

class Wrapper extends React.Component {
  constructor(props) {
    super(props)
    this.updateMenuSize = this.updateMenuSize.bind(this);
    this.handleClick = this.handleClick.bind(this)
    this.menuToggled = false;
  }

  componentDidMount() {
    this.updateMenuSize();
    window.addEventListener('resize', this.updateMenuSize);
  }

  componentWillUnmount() {
    window.removeEventListener('resize', this.updateMenuSize);
  }

  updateMenuSize() {
    if (window.innerWidth >= 992) {
      document.getElementById('menu').style.width = '380px';
      this.menuToggled = false;
    } else if (!this.menuToggled) {
      if (document.querySelector('.toggle').classList.contains('active')) {
        document.querySelector('.toggle').classList.toggle('active');
      }
      document.getElementById('menu').style.width = '0%';
      this.menuToggled = true;
    }
  }

  handleClick() {
    document.querySelector('.toggle').classList.toggle('active');
    this.props.toggleHamburger();
    document.body.style.overflow = 'visible';
    if (this.props.open) {
      document.getElementById('menu').style.width = '0%';
    } else {
      document.getElementById('menu').style.width = '100%';
    }
  }

  componentDidUpdate() {
    document.body.style.overflow = 'hidden';
  }

  render() {
    const { loading } = this.props;

    if (isSSL && !isPWA) {
      return <InstallerWindow />
    } else {
      return (
        <Router>
          <div className="wrapper d-flex flex-column flex-lg-row ">
            <div id="menu" className="sidebar">
              <SideBar />
            </div>

            <div className="hamburger d-flex align-items-center justify-content-between d-lg-none px-3 py-2">
              <Link to="/" className="logo-link">
                <img src={miniLogo} alt="" />
              </Link>

              <div className="toggle" onClick={this.handleClick}></div>
            </div>

            <div id="content" className="content w-100 h-100">
              <div id="bg-wrapper" className="bg-wrapper" ref={(ref) => this.scrollParentRef = ref}>
                <Switch>
                  <Route path="/"            component={Radio} exact/>
                  <Route path="/recent"      component={Recent} />
                  <Route path="/schedule"    component={Scheduler} />
                  <Route path="/programs"    component={Programs} />
                  <Route path="/recommended" component={Recommended} parentRef={this.scrollParentRef} />

                  <Redirect to="/" />
                </Switch>
              </div>
            </div>

            {loading && <div className="loader-container"><div className="loader"></div></div>}
          </div>

          {!isPWA && <InstallButton />}
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
