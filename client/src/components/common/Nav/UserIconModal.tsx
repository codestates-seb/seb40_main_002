import React from 'react';
import useModal from '../../../hooks/useModal';
import Login from '../../../pages/Login';

const UserIconModal = () => {
  const [loginModal, setLoginModal] = useModal();
  return (
    <section className="flex flex-col w-36 h-40 rounded-[15px] absolute top-[80px] right-10 p-4 text-lg font-semibold items-center justify-around border-solid border-2 border-borderline">
      <div className="cursor-pointer p-2 hover:bg-point-color hover:text-white rounded-[15px]">
        마이페이지
      </div>

      {/* 로그인 상태에 따른 조건부 렌더링 필요 */}
      <div className="cursor-pointer p-2" onClick={setLoginModal}>
        로그인
      </div>
      {loginModal && (
        <Login isOpen={loginModal} openModalHandler={setLoginModal} />
      )}
    </section>
  );
};

export default UserIconModal;
