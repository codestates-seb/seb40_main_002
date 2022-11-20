import React from 'react';
import CommonBtn from '../common/CommonBtn/CommonBtn';
import DetailReview from '../common/Comment/DetailReview';
type Props = {
  reviewComment: {
    reviewComment: {
      userName: string;
      createBy: string;
      comment: string;
      ProfileImg: string;
      starScore: number;
    };
  }[];
};

const GhDetailComment = ({ reviewComment }: Props) => {
  const commentHandler = () => {
    console.log('모든후기');
  };

  return (
    <div className="mb-[20px] border-b-[2px] ">
      <div className="grid grid-cols-2 items-center">
        {reviewComment.map((el, i) => (
          <DetailReview key={i} type={'roomDetail'} {...el} />
        ))}
      </div>
      <div className="my-[20px]">
        <CommonBtn
          text={`${reviewComment.length}개 후기 모두 보기`}
          btnSize={'w-[210px] h-[40px]'}
          btnHandler={commentHandler}
        />
      </div>
    </div>
  );
};
export default GhDetailComment;
