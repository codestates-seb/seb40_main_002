import GhDetailComment from '../components/GhDetail/GhDetailComment';
import GhInfomation from '../components/GhDetail/GhInfomation';
import GhReservation from '../components/GhDetail/GhReservation';
import GhLocation from '../components/GhDetail/GhLocation';
import GhAsk from '../components/GhDetail/GhAsk';
import RatedStar from '../components/common/RatedStar';
import RoomsDetail from '../components/GhDetail/RoomsDetail';
import GhDetailFacilities from '../components/GhDetail/GhDetailFacilities';
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
        roomPrice: 10000,
        roomImage:
          'https://a0.muscache.com/im/pictures/337660c5-939a-439b-976f-19219dbc80c7.jpg?im_w=720',
      },
    },
    {
      room: {
        roomName: '남자 도미토리 6인실',
        roomExplain: '남자 도미토리 6인실 입니다',
        roomPersonnel: 1,
        roomPrice: 20000,
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
  const qwe = [false, true, true, false, true, false, true, true, true];
  return (
    <div className="flex justify-center	items-center xl:p-0 text-xl font-semibold ">
      <div className="flex-row">
        <GhInfomation tags={tags} />
        <RoomsDetail rooms={rooms} />
        <GhReservation rooms={rooms} />
        <div className="flex gap-[10px] my-[20px] items-center">
          <RatedStar star={4} />
          <div className="text-xl ">후기 {reviewComment.length} 개</div>
        </div>
        <GhDetailComment reviewComment={reviewComment} />
        <GhLocation latitude={latitude} longitude={longitude} />
        <div className="flex w-full  mt-[20px]">
          <GhDetailFacilities GhFacilities={qwe}></GhDetailFacilities>
          <GhAsk />
        </div>
      </div>
    </div>
  );
};

export default GuestHouseDetail;
