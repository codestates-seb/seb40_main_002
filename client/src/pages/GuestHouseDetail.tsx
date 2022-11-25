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
import { getGhDetailData } from '../apis/getGhDetailData';
interface Props {
  guestHouseId: number;
  guestHouseName: string;
  memberId: string;
  memberNickname: string;
  memberPhone: string;
  memberImageUrl: null;
  guestHouseLocation: string;
  guestHouseAddress: string;
  guestHousePhone: string;
  guestHouseStatus: string;
  guestHouseDetails: boolean[];
  guestHouseStar: number;
  guestHouseTag: string[];
  guestHouseImage: string[];
  guestHouseInfo: string;
  rooms: [
    {
      roomId: number;
      roomName: string;
      roomPrice: number;
      roomImageUrl: string;
      roomInfo: string;
      reservePossible: boolean;
    }
  ];
  reviews: string[];
  guestHouseReviewCount: number;
  createdAt: string;
  modifiedAt: string;
}
const GuestHouseDetail = () => {
  const [ghdata, setGhData] = useState<Props>();

  useEffect(() => {
    const data = async () => {
      const ghData = await getGhDetailData(`1?start=2022-11-24&end=2022-11-25`);
      setGhData(ghData);
    };
    data();
  }, []);

  return (
    <div className="  text-xl font-semibold mx-[160px]">
      {ghdata && (
        <div className="flex-row ">
          <GhInformation
            tags={ghdata.guestHouseTag}
            ghName={ghdata.guestHouseName}
            ghInfo={ghdata.guestHouseInfo}
            ghImage={ghdata.guestHouseImage}
            ghNickname={ghdata.memberNickname}
          />
          <RoomsDetail rooms={ghdata.rooms} />
          <GhReservation rooms={ghdata.rooms} />
          <div className="flex gap-[10px] my-[20px] items-center">
            <RatedStar star={ghdata.guestHouseStar} />
            <div className="text-xl ">
              후기 {ghdata.guestHouseReviewCount} 개
            </div>
          </div>
          {/* <GhDetailComment reviewComment={reviewComment} /> */}
          <GhLocation
            ghLocation={ghdata.guestHouseLocation.split(',')}
            address={ghdata.guestHouseAddress}
          />
          <div className="flex-row justify-between  md:flex mt-[20px]">
            <GhDetailFacilities
              GhFacilities={ghdata.guestHouseDetails}
            ></GhDetailFacilities>
            <GhAsk
              ghName={ghdata.guestHouseName}
              GhPhone={ghdata.guestHousePhone}
            />
          </div>
        </div>
      )}
    </div>
  );
};
export default GuestHouseDetail;
