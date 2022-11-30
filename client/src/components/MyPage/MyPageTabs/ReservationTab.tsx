import Comment from '../../common/Comment/Comment';
import { useEffect, useState } from 'react';
import { getReservationData } from '../../../apis/getReservationData';
type ReservationType = {
  data: {
    guestHouseId: number;
    guestHouseName: string;
    roomName: string;
    roomImageUrl: string;
    roomReservationStart: string;
    roomReservationEnd: string;
  }[];
  pageInfo: {
    page: number;
    size: number;
    totalElements: number;
    totalPages: number;
  };
};
function ReservationTab() {
  const id = '해당객실주소';
  const detailReviewPage = '유동적으로 설정';
  const [reservationData, setReservationData] = useState<any>();
  useEffect(() => {
    const data = async () => {
      const userData = await getReservationData(`?page=1&size=4`);
      setReservationData(userData);
    };
    data();
  }, []);
  console.log(reservationData);

  return (
    <>
      <div>
        {reservationData.data.length > 0 ? (
          reservationData.data.map((el: any) => {
            <Comment
              key={el.guestHouseId}
              // type 종류 : myPage, roomDetail, reviewPage
              houseName={el.guestHouseName}
              date={`${el.roomReservationStart}~${el.roomReservationEnd}`}
              imgsrc={el.roomImageUrl}
              room={el.roomName}
              roomLink={`/ghdetail/${el.guestHouseId}?start=${el.roomReservationStart}end=${el.roomReservationEnd}`}
              // 이동되는 라우터를 적어주시면 됩니다. 해당 게스트하우스 페이지로 이동됩니다.
              // reviewLink={`${detailReviewPage}`}
              type="myPage"
            />;
          })
        ) : (
          <div>asdasd</div>
        )}
      </div>
    </>
  );
}
export default ReservationTab;
