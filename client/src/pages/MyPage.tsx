import { useEffect, useState } from 'react';
import MyPageTabs from '../components/MyPage/MyPageTabs/MyPageTabs';
import UserInfoCard from '../components/MyPage/UserInfoCard/UserInfoCard';
import { MyPageUser } from '../types/user';
import { useSelector } from 'react-redux';
import { RootState } from '../store/store';
const testUser: MyPageUser = {
  memberImageUrl:
    'https://cdn.pixabay.com/photo/2015/11/16/14/43/cat-1045782_1280.jpg',
  memberTag: ['오션뷰', '한적한', '나무'],
  memberId: '45423243244@kakao',
  memberNickname: '찰나의지루함',
  memberEmail: 'wotddd@naver.com',
  memberPhone: '010-2222-5555',
  memberReservation: [
    {
      guestHouserName: '숙소명1',
      guestHouseRoomStart: '2022-11-01',
      guestHouseRoomEnd: '2022-11-05',
    },
    {
      guestHouserName: '숙소명2',
      guestHouseRoomStart: '2022-11-07',
      guestHouseRoomEnd: '2022-11-09',
    },
  ],
  memberReview: [
    {
      guestHouseName: '숙소명1',
      reviewContent: '리뷰내용1',
    },
    {
      guestHouseName: '숙소명2',
      reviewContent: '리뷰내용2',
    },
  ],
  memberComunity: [
    {
      postTitle: '게시글 제목1',
    },
    {
      postTitle: '게시글 제목2',
    },
  ],
};

function MyPage() {
  const [user, setUser] = useState(testUser);
  const mainUser = useSelector((state: RootState) => state.user);

  useEffect(() => {
    // 회원 정보 요청
    setUser(testUser);
  }, []);
  return (
    <div className="flex justify-between w-full h-full py-[20px]">
      <UserInfoCard user={user} setUser={setUser} />
      <MyPageTabs />
    </div>
  );
}

export default MyPage;
