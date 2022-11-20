import RoomInfo from '../components/common/RoomInfo/RoomInfo';
import GhDetailComment from '../components/GhDetail/GhDetailComment';
import GhInfomation from '../components/GhDetail/GhInfomation';
import GhReservation from '../components/GhDetail/GhReservation';
import GhLocation from '../components/GhDetail/GhLocation';
import GhAsk from '../components/GhDetail/GhAsk';
import RatedStar from '../components/common/RatedStar';
const GuestHouseDetail = () => {
  //임시 데이터 입니다 (수정예정)
  const tags = ['안녕', '안녕', '안녕', '안녕'];
  const latitude = 33.450701;
  const longitude = 126.570667;
  const rooms = [
    {
      room: {
        roomName: '남자 도미토리 6인실',
        roomExplain: '남자 도미토리 6인실 입니다',
        roomPersonnel: 1,
        roomPrice: 1,
        roomImage:
          'https://a0.muscache.com/im/pictures/337660c5-939a-439b-976f-19219dbc80c7.jpg?im_w=720',
      },
    },
  ];
  const reviewComment = [
    {
      reviewComment: {
        userName: '정우허',
        createBy: '2022년 6월',
        comment: '정말 즐거웠어요',
        ProfileImg: 'http://gravatar.com/avatar/5',
        starScore: 5,
        id: 'hi',
      },
    },
  ];
  //숙소편의시설 데이터
  const Ghdetail = {
    guestHouseDetails: {
      guestHouseDetailsId: 1,
      guestHouseId: 1,
      guestHouseParty: false,
      guestHouseKitchen: true,
      guestHouseWashing: true,
      guestHouseOcean: true,
      guestHouseTask: true,
      guestHouseEssential: true,
      guestHouseWifi: true,
      guestHouseBoard: true,
      guestHouseCook: true,
      guestHouseParking: true,
    },
  };

  const facilities = Object.entries(Ghdetail.guestHouseDetails).filter((el) =>
    el.includes(true)
  );

  return (
    <div className="flex justify-center	items-center xl:p-0 text-xl font-semibold ">
      <div className="flex-row">
        <GhInfomation tags={tags} />
        <div className="border-b-[2px] mb-[20px]">
          <div className="my-[20px]">객실정보</div>
          {rooms.map((el, i) => (
            <RoomInfo {...el} key={i} />
          ))}
        </div>
        <GhReservation rooms={rooms} />
        <div className="flex gap-[10px] my-[20px] items-center">
          <RatedStar star={4} />
          <div className="text-xl ">후기 {reviewComment.length} 개</div>
        </div>
        <div className="border-b-[2px] mb-[20px]">
          <div className="mb-[20px]">
            <GhDetailComment reviewComment={reviewComment} />
          </div>
        </div>
        <GhLocation latitude={latitude} longitude={longitude} />
        <div className="flex w-full  mt-[20px]">
          <div className="w-6/12  ">
            <div>숙소편의시설</div>
            <div className="grid grid-cols-2 gap-4 text-base">
              {facilities.map((el, i) => (
                <div key={i}>{el[0]}</div>
              ))}
            </div>
          </div>
          <GhAsk />
        </div>
      </div>
    </div>
  );
};

export default GuestHouseDetail;
