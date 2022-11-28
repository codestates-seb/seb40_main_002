import DetailReview from '../components/common/Comment/DetailReview';
import EditReply from '../components/common/Comment/EditReply';

export default function ReviewPage() {
  // 더미데이터
  const dummy = [
    {
      reviewId: 1,
      gusetHouseId: 0,
      comment: '좋아요',
      userName: '정우허',
      memberId: 0,
      createAt: '2022/09/11',
      modifiedAt: '2022/11/12',
      memberImageUrl: 'http://gravatar.com/avatar/5',
      star: 4,
      reviewComment: [
        { replyComment: '안녕하세요 사장입니다.', createBy: '2022년 6월' },
        { replyComment: '안녕하세요', createBy: '2022년 6월' },
        { replyComment: '안녕하세요', createBy: '2022년 6월' },
      ],
    },
    {
      reviewId: 2,
      gusetHouseId: 0,
      comment: '그닥이에요',
      userName: '김코딩',
      memberId: 1,
      createAt: '2022/11/11',
      modifiedAt: '2022/11/12',
      memberImageUrl: 'http://gravatar.com/avatar/4',
      star: 2,
      reviewComment: [
        { replyComment: '안녕하세요 사장입니다.', createBy: '2022년 6월' },
        { replyComment: 'ㅎㅇ', createBy: '2022년 6월' },
        { replyComment: 'ㅎㅇㅇㅎㅇ', createBy: '2022년 6월' },
      ],
    },
    {
      reviewId: 3,
      gusetHouseId: 0,
      comment: '좋아요',
      userName: '박해커',
      memberId: 2,
      createAt: '2022/10/02',
      modifiedAt: '2022/11/12',
      memberImageUrl: 'http://gravatar.com/avatar/3',
      star: 3,
      reviewComment: [
        { replyComment: '안녕하세요 사장입니다.', createBy: '2022년 6월' },
        { replyComment: '안녕하세요', createBy: '2022년 6월' },
        { replyComment: '안녕하세요', createBy: '2022년 6월' },
      ],
    },
    {
      reviewId: 4,
      gusetHouseId: 0,
      comment: '좋아요',
      userName: '이버그',
      memberId: 3,
      createAt: '2022/03/14',
      modifiedAt: '2022/11/12',
      memberImageUrl: 'http://gravatar.com/avatar/2',
      star: 5,
    },
  ];
  return (
    <div className="mt-8 p-3 w-full flex justify-center md:mt-20">
      <div className="mb-[10px] mt-[10px] ml-[10px] w-[60%]">
        <div className="border-b-2 border-border-color mb-8">
          {/* 게스트 하우스 이름 */}
          <span className="font-bold text-xl"> 정우 게스트하우스 </span>
        </div>
        <div className="mb-8">
          <EditReply type="user" id={123} />
        </div>
        <div className="mb-4">
          <span className="font-bold text-lg"> 후기 {dummy.length}개</span>
        </div>
        {dummy.map((review) => {
          return (
            <div key={review.reviewId} className="mb-6">
              <DetailReview
                type="reviewPage"
                reviewComment={{
                  userName: review.userName,
                  createBy: review.createAt,
                  comment: review.comment,
                  ProfileImg: review.memberImageUrl,
                  admin: 'mk', // 해당 리뷰가 달린 게스트하우스의 호스트 닉네임이 들어가야함.
                  starScore: review.star,
                  id: review.reviewId,
                  adminComment: review.reviewComment,
                }}
              />
            </div>
          );
        })}
      </div>
    </div>
  );
}
