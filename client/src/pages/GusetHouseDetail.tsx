import RoomInfo from '../components/common/RoomInfo/RoomInfo';
import GhDetailComment from '../components/GhDetail/GhDetailComment';
import GhInfomation from '../components/GhDetail/GhInfomation';
import GhReservation from '../components/GhDetail/GhReservation';
import GhLocation from '../components/GhDetail/GhLocation';
import GhAsk from '../components/GhDetail/GhAsk';
const GuestHouseDetail = () => {
  //임시 데이터 입니다
  const tags = ['안녕', '안녕', '안녕', '안녕'];
  const latitude = 33.450701;
  const longitude = 126.570667;
  const rooms = [
    {
      room: {
        roomName: 'asd',
        roomExplain: 'asd',
        roomPersonnel: 1,
        roomPrice: 1,
        roomImage:
          'https://a0.muscache.com/im/pictures/337660c5-939a-439b-976f-19219dbc80c7.jpg?im_w=720',
      },
    },
    {
      room: {
        roomName: 'asd',
        roomExplain: 'asd',
        roomPersonnel: 1,
        roomPrice: 1,
        roomImage:
          'https://a0.muscache.com/im/pictures/337660c5-939a-439b-976f-19219dbc80c7.jpg?im_w=720',
      },
    },
    {
      room: {
        roomName: 'asd',
        roomExplain: 'asd',
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

  return (
    <div className="flex justify-center	items-center xl:p-0 text-xl font-semibold ">
      <div className="flex-row">
        <GhInfomation tags={tags} />
        <div>
          <div className="my-[20px]">객실정보</div>
          {rooms.map((el, i) => (
            <RoomInfo {...el} key={i} />
          ))}
        </div>
        <GhReservation rooms={rooms} />
        <GhDetailComment reviewComment={reviewComment} />
        <GhLocation latitude={latitude} longitude={longitude} />
        <div className="flex w-full  mt-[20px]">
          <div className="w-6/12  ">숙소 편의시설</div>
          <GhAsk />
        </div>
      </div>
    </div>
  );
};

export default GuestHouseDetail;
