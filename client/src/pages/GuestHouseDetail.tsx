import GhDetailComment from '../components/GhDetail/GhDetailComment';
import GhInformation from '../components/GhDetail/GhInformation';
import GhReservation from '../components/GhDetail/GhReservation';
import GhLocation from '../components/GhDetail/GhLocation';
import GhAsk from '../components/GhDetail/GhAsk';
import RatedStar from '../components/common/RatedStar';
import RoomsDetail from '../components/GhDetail/RoomsDetail';
import GhDetailFacilities from '../components/GhDetail/GhDetailFacilities';
import { useEffect, useState } from 'react';
import { getGhDetailData } from '../apis/getGhDetailData';
import { ghDetailProps } from '../types/ghDetailData';
import { useParams, useSearchParams } from 'react-router-dom';
import { useSelector } from 'react-redux';
import { RootState } from '../store/store';
const GuestHouseDetail = () => {
  const [searchParams, setSearchParams] = useSearchParams();
  const [ghdata, setGhData] = useState<ghDetailProps>();
  const [startDay, setStartDay] = useState(searchParams.get('start'));
  const [endDay, setEndDay] = useState(searchParams.get('end'));
  const [dayCal, setDayCal] = useState<number>(0);
  const { ghId } = useParams();
  const mainUser = useSelector((state: RootState) => state.user);

  useEffect(() => {
    const data = async () => {
      const ghData = await getGhDetailData(
        `${ghId}?start=${startDay}&end=${endDay}`
      );
      setGhData(ghData);
    };
    if (endDay !== '' && startDay && endDay) {
      setSearchParams({ start: startDay, end: endDay });
      data();
    } else return;
  }, [endDay]);

  return (
    <div className="text-xl font-semibold w-[1120px] mb-[400px]">
      {ghdata && (
        <div>
          <GhInformation
            tags={ghdata.guestHouseTag}
            ghName={ghdata.guestHouseName}
            ghInfo={ghdata.guestHouseInfo}
            ghImage={ghdata.guestHouseImage}
            ghMemberId={ghdata.memberId}
            guestHouseId={ghdata.guestHouseId}
            userMemberId={mainUser.memberId}
            guestHouseStar={ghdata.guestHouseStar}
          />
          <RoomsDetail rooms={ghdata.rooms} />
          <GhReservation
            memberRoles={mainUser.memberRoles}
            rooms={ghdata.rooms}
            startDay={startDay}
            endDay={endDay}
            setStartDay={setStartDay}
            setEndDay={setEndDay}
            setDayCal={setDayCal}
            dayCal={dayCal}
            guestHouseId={ghdata.guestHouseId}
          />
          <div className="flex gap-[10px] my-[20px] items-center">
            <RatedStar star={ghdata.guestHouseStar} />
            <div className="text-xl ">후기 {ghdata.reviews.length} 개</div>
          </div>
          <GhDetailComment reviewComment={ghdata.reviews} ghId={ghId} />
          <GhLocation
            ghLocation={ghdata.guestHouseLocation.split(',')}
            address={ghdata.guestHouseAddress}
          />
          <div className="flex-row  md:flex mt-[20px]">
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
