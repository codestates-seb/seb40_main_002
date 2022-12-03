import React, { useEffect } from 'react';
import Navbar from './components/common/Navbar';
import Footer from './components/common/Footer';
import { Route, Routes } from 'react-router-dom';
import PaymentPage from './pages/PaymentPage';
import MyPage from './pages/MyPage';
import Main from './pages/Main';
import SearchResult from './pages/SearchResult';
import GuestHouseDetail from './pages/GuestHouseDetail';
import Hostingpage from './pages/Hostingpage';
import ReviewPage from './pages/ReviewPage';
import GhEditPage2 from './pages/GhEditPage2';
import Register from './pages/Register';
import UserData from './pages/UserData';
import GhAdminPage from './pages/GhAdminPage';
import Reservation from './pages/Reservation';
import Api from './api2';
import { setUser } from './store/reducer/user';
import { useDispatch } from 'react-redux';
import { User } from './types/user';
import axios from 'axios';

export default function App() {
  const dispatch = useDispatch();
  useEffect(() => {
    const getUser = async () => {
      if (localStorage.getItem('accessToken')) {
        try {
          axios
            .get(`/api/auth/members`, {
              headers: { Authorization: localStorage.getItem('accessToken') },
            })
            .then((res) => {
              dispatch(setUser(res.data.data as User));
            })
            .catch((err) => {
              localStorage.removeItem('accessToken');
              localStorage.removeItem('refreshToken');
              sessionStorage.removeItem('persist:root');
            });
        } catch (e) {
          console.log(e);
        }
      }
    };
    getUser();
  }, []);

  return (
    <div className="w-[100vw] h-[100vh]">
      <Navbar />
      <div className="flex mx-auto pt-[80px] w-full min-h-[100%] pb-[60px] h-full max-w-[1120px]">
        <Routes>
          <Route path="/" element={<Main />} />
          <Route path="/ghedit" element={<Hostingpage />} />
          <Route path="/ghedit/:id" element={<GhEditPage2 />} />
          <Route path="/ghdetail/:ghId" element={<GuestHouseDetail />} />
          <Route path="/review/:ghId" element={<ReviewPage />} />
          <Route path="/search" element={<SearchResult />} />
          <Route path="/mypage/*" element={<MyPage />} />
          <Route path="/paymentpage" element={<PaymentPage />} />
          <Route path="/userdata" element={<UserData />} />
          <Route path="/register" element={<Register />} />
          <Route path="/ghadmin" element={<GhAdminPage />} />
          <Route path="/reservation" element={<Reservation />} />
          <Route path="/ghadmin" element={<GhAdminPage />} />
          {/* 동적 라우팅 필요 */}
        </Routes>
      </div>
      <Footer />
    </div>
  );
}

// [감성,오션뷰].join('&=') '감성&=오션뷰'
// import React from 'react';

// export default function App() {
//   return <div className="text-3xl font-bold underline"></div>;
// }
