import React from 'react';
import Navbar from './components/common/Navbar';
import Footer from './components/common/Footer';
import { Route, Routes } from 'react-router-dom';
import PaymentPage from './pages/PaymentPage';
import GuestHouseDetail from './pages/GusetHouseDetail';

export default function App() {
  return (
    <div className="w-[100vw] h-[100vh]">
      <GuestHouseDetail></GuestHouseDetail>
    </div>
  );
}

// import React from 'react';

// export default function App() {
//   return <div className="text-3xl font-bold underline"></div>;
// }
