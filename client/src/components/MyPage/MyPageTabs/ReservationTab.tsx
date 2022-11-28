import Comment from '../../common/Comment/Comment';

function ReservationTab() {
  const id = '해당객실주소';
  const detailReviewPage = '유동적으로 설정';
  return (
    <div>
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
  );
}
export default ReservationTab;
