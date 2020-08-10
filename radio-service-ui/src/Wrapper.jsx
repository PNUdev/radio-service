import React from 'react';
import PropTypes from 'prop-types';

import * as actions from './redux/actions';

import {
  BrowserRouter as Router,
  Switch,
  Route,
  Redirect,
} from "react-router-dom";

import { connect } from 'react-redux';

import HamburgerMenu from 'react-hamburger-menu'

import SideBar     from './components/Sidebar';
import Radio       from "./components/Radio";
import Recent      from "./components/Recent";
import Scheduler   from './components/Scheduler';
import Recommended from './components/Recommended';
import Programs    from './components/Programs';

import bg from './images/main.jpg'
import './Wrapper.scss';

class Wrapper extends React.Component {
  constructor(props) {
    super(props)

    this.handleClick = this.handleClick.bind(this)
  }

  handleClick() {
    this.props.toggleHamburger();
    this.forceUpdate();

    if(this.props.open) {
      document.getElementById('menu').style.width = '0%';
      document.body.style.overflow = 'visible';
    }
  }

  componentDidUpdate() {
    const menu = document.getElementById('menu');

    if(this.props.open) {
      document.body.style.overflow = 'hidden';
      menu.style.width = '100%';
    }
  }

  render() {
    const { loading } = this.props;

    const bg_styles = {
      backgroundImage: `url(${bg})`,
      backgroundPosition: 'center',
      backgroundSize: 'cover',
      backgroundRepeat: 'no-repeat'
    }

    return (
      <Router>
        <div className="wrapper d-flex flex-column flex-lg-row ">
          <div id="menu" className="sidebar">
            <SideBar />
          </div>

          <div className="hamburger d-lg-none p-2">
            <HamburgerMenu
              isOpen={this.props.open}
              menuClicked={this.handleClick}
              color='white'
            />
          </div>

          <div id="content" className="content" style={bg_styles}>
            <div id="bg-wrapper" className="bg-wrapper" ref={(ref) => this.scrollParentRef = ref}>
              <Switch>
                <Route path="/" exact>
                  <Redirect to="/radio" />
                </Route>

                <Route path="/radio"       component={Radio} />
                <Route path="/recent"      component={Recent} />
                <Route path="/schedule"    component={Scheduler} />
                <Route path="/programs"    component={Programs} />
                <Route path="/recommended" component={Recommended} recommended={true} parentRef={this.scrollParentRef} />
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
