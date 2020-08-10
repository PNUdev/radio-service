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
import $ from 'jquery';

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

    this.state = { open: false }
    this.handleClick = this.handleClick.bind(this)

    let links = document.getElementsByClassName('header-link');

    for (var i=0; i < links.length; i++) {
      links[i].onclick = function(){
        alert('hi')
        this.setState({open: false})
        document.getElementById('menu').style.width = '0%';
        document.body.style.overflow = 'visible';
      }
    };
  }

  handleClick() {
    const menu = document.getElementById('menu')

    this.setState({open: !this.state.open}, () => {
      if(this.state.open) {
        document.body.style.overflow = 'hidden';
        menu.style.width = '100%';
      } else {
        menu.style.width = '0%';
        document.body.style.overflow = 'visible';
      }
    });
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
              isOpen={this.state.open}
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
    loading:     state.shared.loading,
  }
};

const mapDispatchToProps = dispatch => ({
  turnLoadingOn:  ()          => dispatch(actions.turnLoadingOn()),
  turnLoadingOff: ()          => dispatch(actions.turnLoadingOff()),
});

Wrapper.propTypes = {
  loading:     PropTypes.bool,
};

export default connect(mapStateToProps, mapDispatchToProps)(Wrapper);
