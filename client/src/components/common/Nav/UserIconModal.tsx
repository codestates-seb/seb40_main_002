import axios from 'axios';
import React, { useEffect, useState } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { useNavigate } from 'react-router-dom';
import useModal from '../../../hooks/useModal';
import Login from '../../../pages/Login';
import { RootState } from '../../../store/store';
import { clearUser } from '../../../store/reducer/user';
import Api from '../../../api2';
const UserIconModal = () => {
  const navigate = useNavigate();
  const [loginModal, setLoginModal] = useModal();
  const mainUser = useSelector((state: RootState) => state.user);
  const dispatch = useDispatch();

  const handleLogout = () => {
    const accessToken = localStorage.getItem('accessToken');

    Api.post(
      `/api/auth/members/logout`,
      {},
      {
        headers: {
          'Content-Type': '',
        },
      }
    )
      .then((res) => {
        localStorage.removeItem('accessToken');
        localStorage.removeItem('refreshToken');
        dispatch(clearUser());
        navigate('/');
        window.location.reload();
      })
      .catch((err) => {
        navigate('/');
        window.location.reload();
      });
  };
  const myPageEvent = () => {
    // 유저가 로그인 된 상태
    if (mainUser.memberRoles !== undefined && mainUser.memberRoles.length > 0) {
      return userRoleCheck(mainUser.memberRoles[0]);
    } else {
      return alert('로그인을 해주세요.');
    }
  };
  const userRoleCheck = (userRole: string) => {
    if (userRole === 'ADMIN') {
      navigate('/ghadmin');
    } else {
      navigate('/mypage');
    }
  };
  // useEffect(() => {
  // const checkLogin = async () => {
  //   if (localStorage.getItem('accessToken')) {
  //     const userGet = (await settingUser()) as User2;
  //     setUser(userGet);
  //   }
  // };
  // checkLogin();
  // }, []);
  return (
    <section className="flex flex-col w-36 h-40 rounded-[15px] absolute top-[80px] right-10 p-4 text-lg font-semibold items-center justify-around border-solid border-2 border-borderline bg-white">
      <div
        className="cursor-pointer p-2 hover:bg-point-color hover:text-white rounded-[15px]"
        onClick={myPageEvent}
      >
        마이페이지
      </div>

      {/* 로그인 상태에 따른 조건부 렌더링 필요 */}
      {!mainUser.memberId ? (
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
