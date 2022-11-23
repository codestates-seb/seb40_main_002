import React from 'react';
import Comment from './Comment';
import DetailReview from './DetailReview';
import EditReply from './EditReply';

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
          // 이동되는 라우터를 적어주시면 됩니다. 해당 게스트하우스 페이지로 이동됩니다.
          reviewLink={`${detailReviewPage}`}
          type="myPage"
        />
      </div>

      {/* 후기 작성 페이지에서의 리뷰 목록(관리자) */}
      <div className="mb-[10px] mt-[10px] ml-[10px] w-[1000px]">
        <DetailReview
          type="reviewPage"
          reviewComment={{
            userName: '정우허',
            createBy: '2022년 6월',
            comment: '정말 즐거웠어요',
            ProfileImg: 'http://gravatar.com/avatar/5',
            starScore: 5,
            admin: 'mk',
            id: 1,
            adminComment: [
              { replyComment: '안녕하세요', createBy: '2022년 6월' },
              { replyComment: '안녕하세요', createBy: '2022년 6월' },
              { replyComment: '안녕하세요', createBy: '2022년 6월' },
            ],
          }}
        />
      </div>

      {/* 후기 작성 페이지에서의 리뷰 목록 로그인 유저 상태관리 시 admin(관리자)와 현재 로그인 유저를 검사하기 때문에 따로 분기하지 않아도 됩니다.*/}
      <div className="mb-[10px] mt-[10px] ml-[10px] w-[1000px]">
        <DetailReview
          type="reviewPage"
          reviewComment={{
            userName: '정우허',
            createBy: '2022년 6월',
            comment: '정말 즐거웠어요',
            ProfileImg: 'http://gravatar.com/avatar/5',
            starScore: 5,
            admin: 'm2',
            id: 2,
            adminComment: [
              { replyComment: '안녕하세요', createBy: '2022년 6월' },
              { replyComment: '안녕하세요', createBy: '2022년 6월' },
              { replyComment: '안녕하세요', createBy: '2022년 6월' },
            ],
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
            id: 3,
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
      {/* 후기 작성 창에서 유저가 후기를 남길 때 */}
      <div className="mb-[10px] mt-[10px] ml-[10px]">
        <EditReply type="user" id={3} />
      </div>
    </>
  );
}
