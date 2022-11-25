import React from 'react';
import Navbar from './components/common/Navbar';
import Footer from './components/common/Footer';
import { Route, Routes } from 'react-router-dom';
import PaymentPage from './pages/PaymentPage';
import MyPage from './pages/MyPage';
import Main from './pages/Main';
import SearchResult from './pages/SearchResult';
import GhEditpage from './pages/GhEditpage';
import GuestHouseDetail from './pages/GuestHouseDetail';
import Sample from './components/common/Comment/Sample';
import ReviewPage from './pages/ReviewPage';

export default function App() {
  return (
    <div className="w-[100vw] h-[100vh]">
      <Navbar />
      <div className="flex mx-auto pt-[80px] w-full min-h-[100%] pb-[60px] h-full max-w-[1120px]">
        <Routes>
          <Route path="/paymentPage" element={<PaymentPage />} />
          <Route path="/mypage/*" element={<MyPage />} />
          <Route path="/" element={<Main />} />
          <Route path="/search" element={<SearchResult />} />
          <Route path="/ghedit" element={<GhEditpage />} />
          <Route path="/ghdetail" element={<GuestHouseDetail />} />
          <Route path="/sample" element={<Sample />} />
          {/* 동적 라우팅 필요 */}
          <Route path="/review" element={<ReviewPage />} />
        </Routes>
      </div>
      <Footer />
    </div>
  );
}
