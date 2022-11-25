import { useEffect } from 'react';
import { Link, Route, Routes, useNavigate } from 'react-router-dom';
import HeartTab from './HeartTab';
import ReservationTab from './ReservationTab';
import ReviewTab from './ReviewTab';

function MyPageTabs() {
  const navigate = useNavigate();
  useEffect(() => {
    navigate('/mypage/heart');
  }, []);
  return (
    <div className="flex flex-col w-full px-[40px]">
      <div className="flex text-xl font-semibold">
        <Link to="/mypage/heart" className="mr-[20px]">
          찜 목록
        </Link>
        <Link to="/mypage/reservation" className="mr-[20px]">
          예약 내역
        </Link>
        <Link to="/mypage/myreview" className="mr-[20px]">
          후기 목록
        </Link>
        <Link to="/mypage/heart" className="mr-[20px]">
          커뮤니티
        </Link>
      </div>
      <div className="my-[12px]">
        <Routes>
          <Route path="/heart" element={<HeartTab />} />
          <Route path="/reservation" element={<ReservationTab />} />
          <Route path="/myreview" element={<ReviewTab />} />
        </Routes>
      </div>
    </div>
  );
}

export default MyPageTabs;
