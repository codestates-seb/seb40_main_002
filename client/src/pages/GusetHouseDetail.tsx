import Carousel from '../components/common/Carousel/Carousel';
import DetailReview from '../components/common/Comment/DetailReview';
import CommonBtn from '../components/common/CommonBtn/CommonBtn';
import Heart from '../components/common/Heart';
import RatedStar from '../components/common/RatedStar';
import RoomInfo from '../components/common/RoomInfo/RoomInfo';
import Tag from '../components/common/Tag';
import GhDetailComment from '../components/GhDetail/GhDetailComment';
import GhInfo from '../components/GhDetail/GhInfo';
import GhReservation from '../components/GhDetail/GhReservation';
import MapContainer from '../components/KakaoMap';

const GuestHouseDetail = () => {
  const tags = ['안녕', '안녕', '안녕', '안녕'];
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
        <GhInfo tags={tags}></GhInfo>
        <div>
          <div className="my-[20px]">객실정보</div>
          {rooms.map((el, i) => (
            <RoomInfo {...el} key={i}></RoomInfo>
          ))}
        </div>
        <GhReservation rooms={rooms}></GhReservation>
        <GhDetailComment reviewComment={reviewComment}></GhDetailComment>
        <div>
          <div>호스팅 지역</div>
          <div>
            <MapContainer
              latitude={33.450701}
              longitude={126.570667}
            ></MapContainer>
          </div>
          <div>제주특별자치도 제주시 첨단로 242</div>
        </div>
      </div>
    </div>
  );
};

export default GuestHouseDetail;
