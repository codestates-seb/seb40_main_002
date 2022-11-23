import Comment from '../../common/Comment/Comment';

function ReviewTab() {
  const id = '해당객실주소';
  // const detailReviewPage = '유동적으로 설정';
  return (
    <div>
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
  );
}

export default ReviewTab;
