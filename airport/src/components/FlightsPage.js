import React, { useEffect, useState } from 'react'
import axios from 'axios'
import WorldMap from './WorldMap.js'

export default function FlightsPage() {

  return (
    <div className="flcontainer">
      <div className="flleft-div">
        <WorldMap />
      </div>
    </div>
  )
}
