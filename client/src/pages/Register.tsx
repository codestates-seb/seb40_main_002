import CommonBtn from '../components/common/CommonBtn/CommonBtn';
import RightSide from '../components/Register/RightSide';
import UserImg from '../components/Register/UserImg';
import { useNavigate } from 'react-router-dom';
import { useState } from 'react';

export default function Register() {
  const navigate = useNavigate();
  const submitHandler = () => {
    // axios 회원가입 요청
    navigate('/');
  };
  const [nickname, setNickname] = useState('');
  const [phoneNum, setPhoneNum] = useState('');

  const [userImg, setUserImg] = useState(
    // 소셜 로그인 유저 이미지
    'https://a0.muscache.com/im/pictures/337660c5-939a-439b-976f-19219dbc80c7.jpg?im_w=720'
  );

  return (
    <div className="flex flex-col items-center w-full h-screen mt-20">
      <div className="font-bold text-xl border-b-[1.5px] w-[1120px] border-#dddddd mb-5">
        회원님의 정보를 입력해주세요
      </div>
      <section className="flex justify-evenly items-center w-[1120px] mb-8">
        <UserImg userImg={userImg} setUserImg={setUserImg} />
        <RightSide setNickname={setNickname} setPhoneNum={setPhoneNum} />
      </section>
      <CommonBtn
        btnSize="w-[200px] h-[40px]"
        text="회원가입 완료"
        btnFs="test-xl"
        btnHandler={submitHandler}
      />
    </div>
  );
}
