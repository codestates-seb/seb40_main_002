import GhDetailComment from '../components/GhDetail/GhDetailComment';
import GhInformation from '../components/GhDetail/GhInformation';
import GhReservation from '../components/GhDetail/GhReservation';
import GhLocation from '../components/GhDetail/GhLocation';
import GhAsk from '../components/GhDetail/GhAsk';
import RatedStar from '../components/common/RatedStar';
import RoomsDetail from '../components/GhDetail/RoomsDetail';
import GhDetailFacilities from '../components/GhDetail/GhDetailFacilities';
import axios from 'axios';
import { useEffect, useState } from 'react';
const GuestHouseDetail = () => {
  const [ghdata, setGhData] = useState();

  useEffect(() => {
    axios
      .get(
        'http://3.37.58.81:8080/api/guesthouse/2?start=2022-11-24&end=2022-11-25'
      )
      .then((el) => setGhData(el.data.data));
    console.log(ghdata);
  }, []);

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
  // const ghdata = {
  //   data: {
  //     guestHouseId: 5,
  //     guestHouseName: '게스트하우스의이름',
  //     memberId: '업주',
  //     memberNickname: '텍사스호미',
  //     memberPhone: '010-2222-4444',
  //     memberImageUrl: null,
  //     guestHouseLocation: '22.33333 , 43.2223',
  //     guestHouseAddress: '제주시 성북구 을왕리 31',
  //     guestHousePhone: '010-7777-1111',
  //     guestHouseStatus: 'OPEN',
  //     guestHouseDetails: [
  //       true,
  //       true,
  //       false,
  //       true,
  //       false,
  //       true,
  //       false,
  //       true,
  //       false,
  //     ],
  //     guestHouseStar: 0.0,
  //     guestHouseTag: ['오션뷰', '일출'],
  //     guestHouseImage: [],
  //     guestHouseInfo: '이곳은 아무나 올 수 있는 게스트 하우스가 아닙니다.',
  //     rooms: [
  //       {
  //         roomId: 1,
  //         roomName: '좋은룸',
  //         roomPrice: 40000,
  //         roomImageUrl: '/스크린샷 2022-11-18 오전 9.41.26.png',
  //         roomInfo: '참 좋은 방입니다',
  //         reservePossible: true,
  //       },
  //     ],
  //     createdAt: '2022-11-23T17:43:57',
  //     modifiedAt: '2022-11-23T17:43:57',
  //   },
  // };

  return (
    <div className="flex justify-center	items-center xl:p-0 text-xl font-semibold mx-[160px]">
      {/* <div className="flex-row">
        <GhInformation
          tags={ghdata.guestHouseTag}
          ghName={ghdata.guestHouseName}
          ghInfo={ghdata.guestHouseInfo}
          ghImage={ghdata.guestHouseImage}
        />
        <RoomsDetail rooms={ghdata.rooms} />
        <GhReservation rooms={ghdata.rooms} />
        <div className="flex gap-[10px] my-[20px] items-center">
          <RatedStar star={ghdata.guestHouseStar} />
          <div className="text-xl ">후기 {reviewComment.length} 개</div>
        </div>
        <GhDetailComment reviewComment={reviewComment} />
        <GhLocation ghLocation={ghdata.ta.guestHouseLocation.split(',')} />
        <div className="flex-row justify-between  md:flex mt-[20px]">
          <GhDetailFacilities
            GhFacilities={Object.values(ghdata.guestHouseDetails)
              .filter((el) => typeof el == 'boolean')
              .slice(1)}
          ></GhDetailFacilities>
          <GhAsk
            ghName={ghdata.data.guestHouseName}
            GhPhone={ghdata.data.guestHousePhone}
          />
        </div>
      </div> */}
    </div>
  );
};

export default GuestHouseDetail;
