import Comment from '../../common/Comment/Comment';
import { getReviewData } from '../../../apis/getReviewData';
import { useEffect, useState } from 'react';
import { useSelector } from 'react-redux';
import { RootState } from '../../../store/store';
import MyPagePagination from './MyPagePagination';
import { Reviews } from '../../../types/MyPage';

function ReviewTab() {
  // const detailReviewPage = '유동적으로 설정';
  const [page, setPage] = useState<number | null>(1);
  const [reviewData, setReviewData] = useState<Reviews>();
  const mainUser = useSelector((state: RootState) => state.user);
  useEffect(() => {
    const data = async () => {
      const userData = await getReviewData(`?page=${page}&size=4`);
      setReviewData(userData);
    };
    data();
  }, [page]);

  return (
    <div className="mb-[100px]">
      <div>
        {reviewData && reviewData.data.length > 0 ? (
          reviewData.data.map((el) => {
            return (
              <div key={el.reviewId} className="mb-[10px]">
                <Comment
                  // type 종류 : myPage, roomDetail, review
                  houseName={el.guestHouse.guestHouseName}
                  date={`${el.roomReservation.roomReservationStart}~${el.roomReservation.roomReservationEnd}`}
                  imgsrc={el.roomReservation.room.roomImageUrl}
                  room={el.roomReservation.room.roomName}
                  roomLink={`/ghdetail/${el.guestHouse.guestHouseId}?start=${el.roomReservation.roomReservationStart}&end=${el.roomReservation.roomReservationEnd}`}
                  type="review"
                  reviewComment={{
                    userName: mainUser.memberNickname,
                    createBy: el.createdAt,
                    comment: el.comment,
                    ProfileImg: mainUser.memberImageUrl,
                    starScore: el.star,
                  }}
                />
              </div>
            );
          })
        ) : (
          <div className="text-center">작성한 후기가 없습니다</div>
        )}
        <div className="text-center">
          {reviewData && reviewData.data.length > 0 ? (
            <MyPagePagination
              totalPages={reviewData.pageInfo.totalPages}
              page={page}
              setPage={setPage}
            />
          ) : null}
        </div>
      </div>
    </div>
  );
}

export default ReviewTab;
