import React, { useEffect, useState, useRef } from 'react'
import axios from 'axios'

export default function AboutPage() {

    const [images, setImages] = useState([]);

    var ims_n;

    const getImages = async () => {
        try {
          const response = await axios.get('http://localhost:8080/api/airport/getallpictures');
          const imageData = response.data;
          console.log(response.data);

          const imageUrls = imageData.map((image) =>
            'data:image/jpeg;base64,' + image
          );

          // response.data.map(datapiece => {
          //   imagesdatas += 'data:image/jpeg;base64,' + datapiece;
          // })
    
          // setImages('data:image/jpeg;base64,' + response.data[1]);

          setImages(imageUrls);

        } catch (error) {
          console.error(error);
        }
      };

    useEffect(() => {
        getImages();
    }, []);

    return (
        // console.log(images.length),
        // console.log(images),
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
            {images.map((image) =>
              <img className="aboutPicture" src={image} alt="Airport" />
            )}
          </div>
        </div>
    )
}
