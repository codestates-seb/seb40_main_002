import CommonBtn from '../components/common/CommonBtn/CommonBtn';
import RightSide from '../components/Register/RightSide';
import UserImg from '../components/Register/UserImg';
import { useNavigate } from 'react-router-dom';
import { useState, useEffect } from 'react';
import axios from 'axios';
import { convertURLtoFile } from '../libs/srcToFile';

type Social = {
  memberId: string;
  memberEmail: string;
  memberImgurl: string;
  memberRegisterKind: string;
  memberImage: File[];
};

export default function Register() {
  const navigate = useNavigate();
  const [guestHouseTag, setGuestHouseTag] = useState<string[]>([]);
  const [nickname, setNickname] = useState('');
  const [isDup, setIsDup] = useState<boolean | string>('null');
  const [phoneNum, setPhoneNum] = useState('');
  const [isHost, setIsHost] = useState<string>('');
  const [isLocal, setIsLocal] = useState('');
  const [form, setForm] = useState({
    year: new Date().getFullYear(),
    month: '01',
    day: '01',
  });
  const birth = `${form.year}-${form.month}-${form.day}`;
  const [userImg, setUserImg] = useState<string>('');
  const [imgFile, setImgFile] = useState<File | string>('');
  const [socialData, setSocialData] = useState<Social | null>(null);

  const submitHandler = async () => {
    if (isDup === 'null') {
      alert('닉네임 중복 검사가 필요합니다.');
      return;
    }
    const formData = new FormData();
    const memberData = {
      memberId: socialData?.memberId,
      memberNickname: nickname,
      memberEmail: socialData?.memberEmail,
      memberPhone: phoneNum,
      memberBirth: birth,
      memberNationality: isLocal,
      memberRegisterKind: socialData?.memberRegisterKind,
      memberRole: [isHost],
      memberTag: guestHouseTag,
    };
    const memberDto = JSON.stringify(memberData);

    if (socialData) {
      formData.append(
        'member-dto',
        new Blob([memberDto], { type: 'application/json' })
      );

      if (memberData.memberRegisterKind === 'KAKAO') {
        if (userImg.slice(0, 21) !== 'http://k.kakaocdn.net') {
          formData.append('memberImageFile', imgFile);
        } else if (userImg.slice(0, 21) === 'http://k.kakaocdn.net') {
          const convertFile = await convertURLtoFile(userImg.slice(21));
          formData.append('memberImageFile', convertFile);
        }
      }

      if (memberData.memberRegisterKind === 'NAVER') {
        if (userImg.slice(0, 23) !== 'https://ssl.pstatic.net') {
          formData.append('memberImageFile', imgFile);
        } else if (userImg.slice(0, 23) === 'https://ssl.pstatic.net') {
          const convertFile = await convertURLtoFile(userImg);
          console.log('네이버 url', userImg);
          console.log('네이버 파일', convertFile);
          formData.append('memberImageFile', convertFile);
        }
      }

      if (memberData.memberRegisterKind === 'GOOGLE') {
        if (userImg.slice(0, 33) !== 'https://lh3.googleusercontent.com') {
          formData.append('memberImageFile', imgFile);
        } else if (
          userImg.slice(0, 33) === 'https://lh3.googleusercontent.com'
        ) {
          const convertFile = (await convertURLtoFile(userImg)) as File;

          formData.append('memberImageFile', convertFile);
        }
      }
    }

    try {
      const res = await axios.post('/api/members', formData, {
        headers: { 'Content-Type': 'multipart/form-data' },
      });
      navigate('/');
      window.location.reload();
    } catch (err) {
      console.log(err);
    }
  };

  useEffect(() => {
    const userData = sessionStorage.getItem('userData');
    if (!userData && socialData) {
      navigate('/');
    } else if (userData) {
      const getImage = async () => {
        const data = JSON.parse(userData);
        setUserImg(data.memberImgurl);
        setSocialData({ ...data });
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
              userImg={userImg}
              setUserImg={setUserImg}
              setImgFile={setImgFile}
            />
            <RightSide
              nickname={nickname}
              setNickname={setNickname}
              phoneNum={phoneNum}
              setPhoneNum={setPhoneNum}
              guestHouseTag={guestHouseTag}
              setGuestHouseTag={setGuestHouseTag}
              form={form}
              setForm={setForm}
              setIsHost={setIsHost}
              setIsLocal={setIsLocal}
              isDup={isDup}
              setIsDup={setIsDup}
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
