import React, { useEffect, useState } from 'react'
import axios from 'axios'

export default function AboutPage() {
    return (
        <div className="aboutcontainer">
            <div className="aboutLeft">
                <p className="aboutParagraph">
                    &emsp;The bustling hub of ABC International Airport, nestled amidst the vibrant metropolis of ABC City,
                    stands as a testament to the city's meteoric rise as a global aviation nexus. Conceived in the 1960s as a
                    modest regional airfield, ABC International Airport has undergone a remarkable transformation, mirroring
                    the city's own growth and aspirations.<br/><br/>
                </p>
                <p className="aboutParagraph">
                    &emsp;In its nascent stages, the airport catered to a handful of domestic
                    and regional flights, its facilities modest yet functional. However, as ABC City's economic and cultural
                    influence expanded, so did the demands on its aviation infrastructure. The airport underwent a series of
                    expansions, its runway extended, its terminal enlarged, and its facilities upgraded to accommodate the
                    ever-increasing volume of passengers and cargo.<br/><br/>
                </p>
            </div>
            <div className="aboutRight">
                <div className="aboutPicRow">
                    <img className="aboutPicture" src="https://github.com/ivanbazhko/potatis/blob/master/airport_1.jpg?raw=true" alt="airport" />
                    <img className="aboutPicture" src="https://github.com/ivanbazhko/potatis/blob/master/airport_2.jpg?raw=true" alt="airport" />
                    <img className="aboutPicture" src="https://github.com/ivanbazhko/potatis/blob/master/airport_3.jpg?raw=true" alt="airport" />
                </div>
                <div className="aboutPicRow">
                    <img className="aboutPicture" src="https://github.com/ivanbazhko/potatis/blob/master/airport_4.jpg?raw=true" alt="airport" />
                    <img className="aboutPicture" src="https://github.com/ivanbazhko/potatis/blob/master/airport_5.jpg?raw=true" alt="airport" />
                    <img className="aboutPicture" src="https://github.com/ivanbazhko/potatis/blob/master/airport_6.jpg?raw=true" alt="airport" />
                </div>
                <div className="aboutPicRow">
                    <img className="aboutPicture" src="https://github.com/ivanbazhko/potatis/blob/master/airport_7.jpg?raw=true" alt="airport" />
                    <img className="aboutPicture" src="https://github.com/ivanbazhko/potatis/blob/master/airport_8.jpg?raw=true" alt="airport" />
                    <img className="aboutPicture" src="https://github.com/ivanbazhko/potatis/blob/master/airport_9.jpg?raw=true" alt="airport" />
                </div>
                <div className="aboutPicRow">
                    <img className="aboutPicture" src="https://github.com/ivanbazhko/potatis/blob/master/airport_10.jpg?raw=true" alt="airport" />
                    <img className="aboutPicture" src="https://github.com/ivanbazhko/potatis/blob/master/airport_11.jpg?raw=true" alt="airport" />
                    <img className="aboutPicture" src="https://github.com/ivanbazhko/potatis/blob/master/airport_12.jpg?raw=true" alt="airport" />
                </div>
            </div>
        </div>
    )
}
