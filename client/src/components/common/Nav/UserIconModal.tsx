import axios from 'axios';
import React from 'react';
import { Link, useNavigate } from 'react-router-dom';
import useModal from '../../../hooks/useModal';
import Login from '../../../pages/Login';
import { isLogin } from '../../../utils/isLogin';

const UserIconModal = () => {
  const navigate = useNavigate();
  const [loginModal, setLoginModal] = useModal();
  const handleLogout = () => {
    localStorage.removeItem('accessToken');
    localStorage.removeItem('refreshToken');
    localStorage.removeItem('persist:root'); //
    // axios
    //   .post('/api/auth/members/logout', {
    //     headers: {
    //       Authorization: localStorage.getItem('accessToken'),
    //     },
    //   })
    //   .then((res) => {
    //     console.log('logout:', res);
    //     localStorage.removeItem('accessToken');
    //     localStorage.removeItem('refreshToken');
    //     localStorage.removeItem('persist:root'); //
    //     navigate('/');
    //   })
    //   .catch((err) => console.log('logoutErr:', err));
  };
  return (
    <section className="flex flex-col w-36 h-40 rounded-[15px] absolute top-[80px] right-10 p-4 text-lg font-semibold items-center justify-around border-solid border-2 border-borderline">
      <div className="cursor-pointer p-2 hover:bg-point-color hover:text-white rounded-[15px]">
        <Link to="/mypage">마이페이지</Link>
      </div>

      {/* 로그인 상태에 따른 조건부 렌더링 필요 */}
      {!isLogin() ? (
        <div className="cursor-pointer p-2" onClick={setLoginModal}>
          로그인
        </div>
      ) : (
        <div className="cursor-pointer p-2" onClick={handleLogout}>
          로그아웃
        </div>
      )}
      {loginModal && (
        <Login isOpen={loginModal} openModalHandler={setLoginModal} />
      )}
    </section>
  );
};

export default UserIconModal;
