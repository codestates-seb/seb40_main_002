import GhAdminList from '../components/GhAdmin/GhAdminList';
import { MyPageUser } from '../types/user';
import UserInfoCard from '../components/MyPage/UserInfoCard/UserInfoCard';
import { useState } from 'react';

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
const GhAdminPage = () => {
  const ghAdminData = [
    {
      guestHouseId: 143,
      guestHouseName: '게스트하우스의이름',
      memberId: '업주',
      memberNickname: '테스터닉네임',
      memberPhone: '010-2222-3333',
      memberImageUrl: null,
      guestHouseLocation: '22.33333 , 43.2223',
      guestHouseAddress: ['제주시', '성북구', '을왕리 31'],
      guestHousePhone: '010-7777-1111',
      guestHouseStatus: 'OPEN',
      guestHouseDetails: [
        true,
        true,
        false,
        true,
        false,
        true,
        false,
        true,
        false,
      ],
      guestHouseStar: 4.5,
      guestHouseTag: ['오션뷰', '일출'],
      guestHouseImage: [
        'http://3.37.58.81:8080/images/guesthouse/143/%EC%8A%A4%ED%81%AC%EB%A6%B0%EC%83%B7%20-%2022%20-2%202%20-2%20.jpg',
      ],
      guestHouseInfo: '이곳은 아무나 올 수 있는 게스트 하우스가 아닙니다.',
      rooms: [{ roomPrice: 1000 }],
      reviews: null,
      guestHouseReviewCount: 11111,
      createdAt: '2022-11-23T08:29:43',
      modifiedAt: '2022-11-24T20:30:20.039736',
    },
  ];
  const [user, setUser] = useState(testUser);

  return (
    <div className="flex justify-between w-full h-full py-[20px]">
      <UserInfoCard user={user} setUser={setUser} />
      <div className="mx-[20px]">
        <GhAdminList ghAdminData={ghAdminData} />
      </div>
    </div>
  );
};
export default GhAdminPage;
