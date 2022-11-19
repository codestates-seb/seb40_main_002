import React from 'react';
import CommonBtn from '../common/CommonBtn/CommonBtn';
import RatedStar from '../common/RatedStar';
import DetailReview from '../common/Comment/DetailReview';
type Props = {
  reviewComment: {
    reviewComment: {
      userName: string;
      createBy: string;
      comment: string;
      ProfileImg: string;
      starScore: number;
      admin?: string;
      id?: string; // 해당 댓글 고유 아이디
    };
  }[];
};

const GhDetailComment = ({ reviewComment }: Props) => {
  const commentHandler = () => {
    console.log('모든후기');
  };

  return (
    <div>
      <div className="flex gap-[10px] mb-[20px] items-center">
        <RatedStar star={4}></RatedStar>
        <div className="text-xl ">후기 {reviewComment.length} 개</div>
      </div>
      <div className="grid grid-cols-2 items-center">
        {reviewComment.map((el, i) => (
          <DetailReview key={i} type={'roomDetail'} {...el}></DetailReview>
        ))}
      </div>
      <CommonBtn
        text={`${reviewComment.length}개 후기 모두 보기`}
        btnSize={'w-[210px] h-[50px]'}
        btnHandler={commentHandler}
      ></CommonBtn>
    </div>
  );
};
export default GhDetailComment;
