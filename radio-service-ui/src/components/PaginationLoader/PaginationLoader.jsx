import React from "react";
import './PaginationLoader.scss'

const PaginationLoader = () => {
  return(
  <div className="pagination-loading d-flex flex-column align-items-center justify-content-center">
    <div className="circles-wrapper d-flex justify-content-between">
      <div className="circle"></div>
      <div className="circle"></div>
      <div className="circle"></div>
    </div>
  </div>
  )
}

export default PaginationLoader
