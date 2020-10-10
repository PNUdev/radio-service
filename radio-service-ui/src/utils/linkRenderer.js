import React from 'react';

export function LinkRenderer(props) {
  return <a href={props.href} target="_blank">{props.children}</a>
}
