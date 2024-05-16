import React, { useState } from 'react';
import axios from 'axios';

const AirportForm = () => {
  const [selectedFile, setSelectedFile] = useState(null);

  const handleChange = (event) => {
    setSelectedFile(event.target.files[0]);
  };

  const handleSubmit = async (event) => {
    event.preventDefault();

    try {
      const formData = new FormData();
      formData.append('picture', selectedFile);

      const response = await axios.post('http://localhost:8080/api/airport/savepicture', formData, {
        headers: {
          'Content-Type': 'multipart/form-data',
        },
      });

      console.log(response.data);
    } catch (error) {
      console.error(error);
    }
  };

  return (
    <div className="airport-form-small">
      <div className="form-header">
        <h2 className="form-title">Add An Image</h2>
      </div>

      <form className="form-body" onSubmit={handleSubmit}>
        <div className="form-field">
          <label className="form-label" htmlFor="name">
            File:
          </label>
          <input
            type="file"
            id="name"
            name="name"
            className="login-input"
            accept="image/png, image/jpeg"
            onChange={handleChange}
          />
        </div>

        <button className="airp-button" type="submit">
          Upload
        </button>
      </form>
    </div>
  );
};

export default AirportForm;