import GhAdminList from '../components/GhAdmin/GhAdminList';
import { User2, User1 } from '../types/user';
import UserInfoCard from '../components/MyPage/UserInfoCard/UserInfoCard';
import { useEffect, useState } from 'react';
import Api from '../api2';
import { getUser as settingUser } from '../api2/member';
import { convertURLtoFile } from '../libs/srcToFile';
import { useNavigate } from 'react-router-dom';

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
  const [currentpageNum, setCurrentPageNum] = useState(1);
  const navigate = useNavigate();
  useEffect(() => {
    const getGhdata = async () => {
      // 유저 정보 가져 오기
      try {
        const userGet = (await settingUser()) as User2;
        if (userGet.memberRoles[0] !== 'ADMIN') {
          return navigate('/');
        }
        // 해당 호스트가 가지고 있는 데이터 가져오기
        const data = await Api.get(
          `/api/auth/members/${userGet.memberId}/guesthouse?page=${currentpageNum}&size=7`
        );
        setPagenation({ ...data.data.pageInfo });
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
      } catch (e) {
        alert('login을 다시 해주세요.');
        localStorage.removeItem('accessToken');
        localStorage.removeItem('refreshToken');
        localStorage.removeItem('persist:root');
        navigate('/');
        window.location.reload();
      }
    };
    getGhdata();
  }, [currentpageNum]);

  return (
    <div className="flex justify-between w-full h-full py-[20px]">
      {loading && ghList && user && (
        <div className="flex flex-col">
          <div className="flex">
            <UserInfoCard user={user} setUser={setUser} />
            <div className="mx-[20px]">
              <GhAdminList
                ghAdminData={ghList.ghAdminData}
                AdminName={user.memberNickname}
              />
            </div>
          </div>
          <div className="text-right ">
            {pagenation &&
              new Array(pagenation.totalPages).fill(0).map((x, idx) => (
                <button
                  className={`${
                    currentpageNum === idx + 1
                      ? 'border-b-[2px] border-black border-b-2'
                      : ''
                  } ml-[10px] py-[2px] px-[12px] mb-[20px] pointer-events-auto}`}
                  key={idx}
                  onClick={() => {
                    setCurrentPageNum(idx + 1);
                  }}
                >
                  {idx + 1}
                </button>
              ))}
          </div>
        </div>
      )}
    </div>
  );
};
export default GhAdminPage;
