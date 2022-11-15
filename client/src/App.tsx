import React from 'react';
import Navbar from './components/common/Navbar';
import Location from './components/common/Nav/Location';
import DatePicker1 from './components/common/Nav/DatePicker';

export default function App() {
  return (
    <>
      <Navbar />
      <Location />
      <DatePicker1 />
    </>
  );
}

// import React from 'react';

// export default function App() {
//   return <div className="text-3xl font-bold underline"></div>;
// }
