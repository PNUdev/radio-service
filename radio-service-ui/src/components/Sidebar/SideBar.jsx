import React from 'react';
import { Link, NavLink, useLocation } from "react-router-dom";

import logo from '../../images/logo.png';

import radio from '../../images/menu-items/radio.png';
import broadcast from '../../images/menu-items/broadcast.png';
import scheduler from '../../images/menu-items/scheduler.png';
import video from '../../images/menu-items/video.png';
import programs from '../../images/menu-items/programs.png';

import radioActive from '../../images/menu-items/radio-active.png';
import broadcastActive from '../../images/menu-items/broadcast-active.png';
import schedulerActive from '../../images/menu-items/scheduler-active.png';
import videoActive from '../../images/menu-items/video-active.png';
import programsActive from '../../images/menu-items/programs-active.png';

import facebook from '../../images/socials/facebook.svg';
import instagram from '../../images/socials/instagram.svg';
import youtube from '../../images/socials/youtube.svg';

import './SideBar.scss'

const SideBar = () => {
  const currentPath = useLocation().pathname;

  return(
    <div className="sidebar-content pt-5">
      <header>
        <div className="logo-wrapper d-flex justify-content-center">
          <Link to="/radio">
            <img src={logo} alt="" className="logo"/>
          </Link>
        </div>

        <div className="socials d-flex justify-content-center mt-3">
          <a href="https://www.facebook.com/profile.php?id=100006156246012" target='_blank'>
            <img src={facebook} alt=""/>
          </a>
          <a href="https://www.instagram.com/vasyl_mutskaniuk/" target='_blank'>
            <img src={instagram} alt="" className="mx-3"/>
          </a>
          <a href="https://www.youtube.com/channel/UCy677jxqQGAvbxPrMHEF4iA/" target='_blank'>
            <img src={youtube} alt=""/>
          </a>
        </div>
      </header>

      <nav className="mt-4">
        <NavLink to="/radio" className="d-flex align-items-center radio">
          <img src={currentPath === '/radio' ? radioActive : radio} alt="" className="mr-2"/>
          Радіо
        </NavLink>

        <NavLink to="recent" className="d-flex align-items-center recent">
          <img src={currentPath === '/recent' ? broadcastActive : broadcast} alt="" className="mr-2"/>
          Нещодавні відео
        </NavLink>

        <NavLink to="schedule" className="d-flex align-items-center scheduler">
          <img src={currentPath === '/schedule' ? schedulerActive : scheduler} alt="" className="mr-2"/>
          Розклад
        </NavLink>

        <NavLink to="recommended" className="d-flex align-items-center recommended">
          <img src={currentPath === '/recommended' ? videoActive : video} alt="" className="mr-2"/>
          Рекомендовані відео
        </NavLink>

        <NavLink to="programs" className="d-flex align-items-center programs">
          <img src={currentPath === '/programs' ? programsActive : programs} alt="" className="mr-2"/>
          Програми
        </NavLink>
      </nav>
    </div>
  )
}

export default SideBar;
