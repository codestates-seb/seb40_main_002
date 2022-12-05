import { useEffect, useState } from 'react';

import MyPageTabs from '../components/MyPage/MyPageTabs/MyPageTabs';
import UserInfoCard from '../components/MyPage/UserInfoCard/UserInfoCard';
import { User1, User2 } from '../types/user';

import { getUser } from '../api2/member';
import { convertURLtoFile } from '../libs/srcToFile';
import { useNavigate } from 'react-router-dom';

function MyPage() {
  const [user, setUser] = useState<User1>({
    memberId: '',
    memberBirth: '',
    memberEmail: '',
    memberImageFile: [],
    memberNationality: '',
    memberNickname: '',
    memberPhone: '',
    memberRegisterKind: '',
    memberRoles: [],
    memberTag: [],
  });
  const [loading, setLoading] = useState(false);
  const navigate = useNavigate();
  useEffect(() => {
    const getGhdata = async () => {
      // 유저 정보 가져 오기
      try {
        const userGet = (await getUser()) as User2;
        if (userGet.memberRoles[0] !== 'USER') {
          return navigate('/');
        }
        const FileData = await convertURLtoFile(
          `${process.env.REACT_APP_SERVER_URL}${userGet.memberImageUrl}`
        );
        setUser({
          ...userGet,
          memberImageFile: [FileData],
        });
        setLoading(true);
      } catch (e) {
        alert('login을 다시 해주세요.');
        localStorage.removeItem('accessToken');
        localStorage.removeItem('refreshToken');
        sessionStorage.removeItem('persist:root');
        navigate('/');
        window.location.reload();
      }
    };
    getGhdata();
  }, []);

  return (
    <div className="flex justify-between w-full h-full py-[20px]">
      {loading && (
        <>
          <UserInfoCard user={user} setUser={setUser} />
          <MyPageTabs />
        </>
      )}
    </div>
  );
}

export default MyPage;
