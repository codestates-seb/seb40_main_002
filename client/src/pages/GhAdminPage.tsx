import GhAdminList from '../components/GhAdmin/GhAdminList';
import { User2, User1 } from '../types/user';
import UserInfoCard from '../components/MyPage/UserInfoCard/UserInfoCard';
import { useEffect, useState } from 'react';
import Api from '../api';
import { getUser as settingUser } from '../api/member';
import { convertURLtoFile } from '../libs/srcToFile';

interface GhData {
  ghAdminData: {
    memberNickname: string;
    guestHouseImage: string[];
    guestHouseName: string;
    guestHouseStar: number;
    guestHouseTag: Array<string>;
    guestHouseId: number;
    rooms: { roomPrice: number }[];
  }[];
}

interface PageInfo {
  page: number;
  size: number;
  totalElements: number;
  totalPages: number;
}

interface GhList {
  guestHouseId: number;
  guestHouseName: string;
  memberId: string;
  memberNickname: string;
  memberPhone: string;
  memberImageUrl: string;
  guestHouseLocation: string;
  guestHouseAddress: string[];
  guestHousePhone: string;
  guestHouseStatus: string;
  guestHouseDetails: boolean[];
  guestHouseStar: number;
  guestHouseTag: string[];
  guestHouseImage: string[];
  guestHouseInfo: string;
  rooms: object[];
  reviews: object[] | null;
  guestHouseReviewCount: number;
  createdAt: string;
  modifiedAt: string;
}

const GhAdminPage = () => {
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
  const [ghList, setGhList] = useState<GhData | null>(null);
  const [pagenation, setPagenation] = useState<PageInfo | null>(null);
  const [loading, setLoading] = useState(false);

  useEffect(() => {
    const getGhdata = async () => {
      // 유저 정보 가져 오기
      const userGet = (await settingUser()) as User2;
      // 해당 호스트가 가지고 있는 데이터 가져오기
      const data = await Api.get(
        `/api/auth/members/${userGet.memberId}/guesthouse?page=1&size=10`
      );

      const ghData = data.data.data.map((x: GhList) => {
        return {
          memberNickname: x.memberNickname,
          guestHouseImage: x.guestHouseImage,
          guestHouseName: x.guestHouseName,
          guestHouseStar: x.guestHouseStar,
          guestHouseTag: x.guestHouseTag,
          guestHouseId: x.guestHouseId,
          rooms: x.rooms.map((x: any) => {
            return { roomPrice: x.roomPrice };
          }),
        };
      });
      const FileData = await convertURLtoFile(
        `${process.env.REACT_APP_SERVER_URL}${userGet.memberImageUrl}`
      );
      // 유저 정보
      setUser({
        ...userGet,
        memberImageFile: [FileData],
      });

      // 게하 정보 갱신
      setGhList({
        ghAdminData: [...ghData],
      });
      setLoading(true);
    };
    getGhdata();
  }, []);
  console.log(user);
  return (
    <div className="flex justify-between w-full h-full py-[20px]">
      {loading && ghList && user && (
        <>
          <UserInfoCard user={user} setUser={setUser} />
          <div className="mx-[20px]">
            <GhAdminList ghAdminData={ghList.ghAdminData} />
          </div>
        </>
      )}
    </div>
  );
};
export default GhAdminPage;
