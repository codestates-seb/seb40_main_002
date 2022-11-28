import CommonBtn from '../components/common/CommonBtn/CommonBtn';
import RightSide from '../components/Register/RightSide';
import UserImg from '../components/Register/UserImg';
import { useNavigate } from 'react-router-dom';
import { useEffect, useState } from 'react';
import { convertURLtoFile } from '../libs/srcToFile';
import axios from 'axios';

type Social = {
  memberId: string;
  memberEmail: string;
  memberImgurl: string;
  memberRegisterKind: string;
  memberImage: File[];
};

export default function Register() {
  const [guestHouseTag, setGuestHouseTag] = useState<string[]>([]);
  const [nickname, setNickname] = useState('');
  const [phoneNum, setPhoneNum] = useState('');
  const [socialData, setSocialData] = useState<Social | null>(null);
  const [form, setForm] = useState({
    year: new Date().getFullYear(),
    month: '01',
    day: '01',
  });
  const [userImg, setUserImg] = useState<File[] | []>([]);

  const birth = `${form.year}-${form.month}-${form.day}`;

  const nicknameChecker = () => {
    if (nickname.length >= 2) return true;
    else return false;
  };

  const navigate = useNavigate();
  const submitHandler = async () => {
    // if (!nicknameChecker()) {
    //   alert('닉네임은 2글자 이상이어야 합니다.');
    // }
    // // axios 회원가입 요청
    // // navigate('/');
    // console.log(birth, phoneNum, nickname, guestHouseTag, userImg);
  };

  useEffect(() => {
    const userData = sessionStorage.getItem('userData');

    if (!userData && socialData) {
      navigate('/');
    } else if (userData) {
      const getImage = async () => {
        const data = JSON.parse(userData);
        const imgUrl = data.memberImgurl.split('/').slice(3).join('/');
        const memberImage = await convertURLtoFile(imgUrl);
        setSocialData({ ...data, memberImage: [memberImage] });
        sessionStorage.removeItem('userData');
      };
      getImage();
    }
  }, []);

  return (
    <>
      {socialData && (
        <section className="flex flex-col items-center w-full h-screen mt-20">
          <div className="font-bold text-xl border-b-[1.5px] w-[1120px] border-#dddddd mb-5">
            회원님의 정보를 입력해주세요
          </div>
          <div className="flex justify-evenly items-center w-[1120px] mb-8">
            <UserImg
              userImg={URL.createObjectURL(socialData.memberImage[0])}
              setUserImg={setUserImg}
            />
            <RightSide
              setNickname={setNickname}
              setPhoneNum={setPhoneNum}
              guestHouseTag={guestHouseTag}
              setGuestHouseTag={setGuestHouseTag}
              form={form}
              setForm={setForm}
            />
          </div>
          <CommonBtn
            btnSize="w-[200px] h-[40px]"
            text="회원가입 완료"
            btnFs="test-xl"
            btnHandler={submitHandler}
          />
        </section>
      )}
    </>
  );
}
