import React, { useEffect, useState } from 'react'
import axios from 'axios'
import BookingForm from './BookingForm'

export default function MainPage() {

  return (
    <div className="mainlogo">
      <div className="mainleft-div">
      </div>
      <div className="mainright-div">
        <BookingForm />
      </div>
    </div>
  )
}
