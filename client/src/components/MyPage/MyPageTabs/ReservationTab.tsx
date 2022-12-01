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
  const [reservationData, setReservationData] = useState<ReservationType>();
  const [page, setPage] = useState<number | null>(1);
  useEffect(() => {
    const data = async () => {
      const userData = await getReservationData(`?page=${page}&size=4`);
      setReservationData(userData);
    };
    data();
  }, [page]);

  return (
    <div className="mb-[100px]">
      <div>
        {reservationData && reservationData.data.length > 0 ? (
          reservationData.data.map((el, i) => {
            return (
              <div key={i} className="mb-[10px]">
                <Comment
                  houseName={el.guestHouseName}
                  date={`${el.roomReservationStart}~${el.roomReservationEnd}`}
                  imgsrc={el.roomImageUrl}
                  room={el.roomName}
                  roomLink={`/ghdetail/${el.guestHouseId}?start=${el.roomReservationStart}&end=${el.roomReservationEnd}`}
                  reviewLink={'/review'}
                  type="myPage"
                />
              </div>
            );
          })
        ) : (
          <div className="text-center">예약 내역이 없습니다</div>
        )}
      </div>
      <div className="text-center">
        {reservationData && reservationData.data.length > 0
          ? Array(reservationData?.pageInfo.totalPages)
              .fill(0)
              .map((el, i) => (
                <button
                  className={`${
                    page == i + 1 ? 'border-b-[2px] border-black' : null
                  } ml-[10px] py-[2px] px-[12px] mb-[20px] pointer-events-auto`}
                  key={i}
                  onClick={() => setPage(i + 1)}
                  value={i + 1}
                >
                  {i + 1}
                </button>
              ))
          : null}
      </div>
    </div>
  );
}
export default ReservationTab;
