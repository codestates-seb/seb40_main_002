import React from 'react';
import Navbar from './components/common/Navbar';
import Footer from './components/common/Footer';
import { Route, Routes } from 'react-router-dom';
import PaymentPage from './pages/PaymentPage';
import GhEditpage from './pages/GhEditpage';
import GuestHouseDetail from './pages/GusetHouseDetail';
import Sample from './components/common/Comment/Sample';
import Register from './pages/Register';

export default function App() {
  return (
    <div className="w-[100vw] h-[100vh]">
      <Navbar />
      <div className="flex mx-auto pt-[80px] w-full min-h-[100%] pb-[60px]">
        <Routes>
          <Route path="/paymentPage" element={<PaymentPage />} />
          <Route path="/ghedit" element={<GhEditpage />} />
          <Route path="/ghdetail" element={<GuestHouseDetail />} />
          <Route path="/sample" element={<Sample />} />
          <Route path="/register" element={<Register />} />
        </Routes>
      </div>
      <Footer />
    </div>
  );
}

// import React from 'react';

// export default function App() {
//   return <div className="text-3xl font-bold underline"></div>;
// }
