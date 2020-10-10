import React from 'react';

import './Video.scss';

const DESCRIPTION_LENGTH = 1000;

const Video = ({video}) => {
  return (
    <div className="video-card mb-4 p-3">
      <h3 className="title mb-3 pb-2">{video.title}</h3>
      <div className="d-flex flex-column video">
        <iframe width="560"
                height="315"
                src={"https://www.youtube.com/embed/" + video.id}
                frameBorder="0"
                allow="accelerometer; autoplay; encrypted-media; gyroscope; picture-in-picture"
                allowFullScreen
                className="youtube-video"></iframe>

        <div className="description ml-2">{video.description.substring(0, DESCRIPTION_LENGTH)}</div>
      </div>
    </div>
  )
};

export default Video;
