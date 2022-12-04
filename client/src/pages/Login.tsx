// import { useState } from 'react';
import LoginButton from '../components/Login/LoginButton';
// import useModal from '../hooks/useModal';
interface Props {
  isOpen: boolean;
  openModalHandler: () => void;
}

export default function Login({ isOpen, openModalHandler }: Props) {
  return (
    <div>
      {isOpen && (
        <div
          onClick={openModalHandler}
          className="flex justify-center items-center fixed top-0 left-0 right-0 bottom-0 bg-black/50 z-[100]"
        >
          <div
            onClick={(e) => e.stopPropagation()}
            className="flex flex-col items-center justify-center h-fit w-fit p-[20px] bg-white rounded-[15px]"
          >
            <div className="text-lg mb-[20px]">
              로그인 또는 회원가입 하세요.
            </div>
            <div className="w-[300px] flex flex-col">
              <LoginButton socialType="naver" />
              <LoginButton socialType="kakao" />
              <LoginButton socialType="google" />
            </div>
          </div>
        </div>
      )}
    </div>
  );
}
