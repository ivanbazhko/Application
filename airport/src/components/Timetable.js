import React from 'react';

const Table = ({ data }) => {
  return (
    <div style={{ height: '300px', overflowY: 'scroll' }}>
      <table style={{ width: '100%' }}>
        <thead>
          <tr>
            <th>Logo</th>
            <th>Route</th>
            <th>Number</th>
            <th>Time</th>
            <th>Airplane</th>
          </tr>
        </thead>
        <tbody>
          {data.map((item) => (
            <tr key={item.column2}>
              <td><img src={item.logo} alt="Logo" style={{ width: '50px', height: '50px' }} /></td>
              <td>{item.direction}</td>
              <td>{item.number}</td>
              <td>{item.time}</td>
              <td>{item.airplane}</td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
};

export default Table;
