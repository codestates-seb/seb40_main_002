import React from 'react';
import Comment from './Comment';
import DetailReview from './DetailReview';
export default function Sample() {
  const id = '해당객실주소';
  const detailReviewPage = '유동적으로 설정';
  return (
    <>
      {/* myPage 예약 목록 */}
      <div className="mb-[10px] mt-[10px] ml-[10px]">
        <Comment
          // type 종류 : myPage, roomDetail, reviewPage
          houseName={'정우네 게스트하우스'}
          date={'2022-11-16~2022-11-18'}
          imgsrc={'http://gravatar.com/avatar/1'}
          room={'도미토리 4인실'}
          roomLink={`/room/${id}`}
          reviewLink={`${detailReviewPage}`}
          type="myPage"
        />
      </div>

      {/* 후기 작성 페이지에서의 리뷰 목록 */}
      <div className="mb-[10px] mt-[10px] ml-[10px] w-[1000px]">
        <DetailReview
          type="reviewPage"
          reviewComment={{
            userName: '정우허',
            createBy: '2022년 6월',
            comment: '정말 즐거웠어요',
            ProfileImg: 'http://gravatar.com/avatar/5',
            starScore: 5,
          }}
        />
      </div>

      {/* 상세 페이지에서의 리뷰 목록 */}
      <div className="mb-[10px] mt-[10px] ml-[10px] w-[560px]">
        <DetailReview
          type="roomDetail"
          reviewComment={{
            userName: '정우허',
            createBy: '2022년 6월',
            comment: '정말 즐거웠어요',
            ProfileImg: 'http://gravatar.com/avatar/5',
            starScore: 5,
          }}
        />
      </div>

      {/* myPage 후기 */}
      <div className="mb-[10px] mt-[10px] ml-[10px]">
        <Comment
          // type 종류 : myPage, roomDetail, review
          houseName={'정우네 게스트하우스'}
          date={'2022-11-16~2022-11-18'}
          imgsrc={'http://gravatar.com/avatar/1'}
          room={'도미토리 4인실'}
          roomLink={`/room/${id}`}
          // reviewLink={'정우네 게하 링크'}
          type="myReview"
          reviewComment={{
            userName: '정우허',
            createBy: '2022년 6월',
            comment: '정말 즐거웠어요',
            ProfileImg: 'http://gravatar.com/avatar/5',
            starScore: 5,
          }}
        />
      </div>
    </>
  );
}
