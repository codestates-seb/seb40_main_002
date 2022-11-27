import React from 'react';
import CommonBtn from '../common/CommonBtn/CommonBtn';
import DetailReview from '../common/Comment/DetailReview';
import { ReviewProps } from '../../types/ghDetailData';

const GhDetailComment = ({ reviewComment }: ReviewProps) => {
  const commentHandler = () => {
    console.log('모든후기');
  };

  return (
    <div className="mb-[20px] border-b-[2px] ">
      <div className="grid grid-cols-2 justify-center items-center">
        {reviewComment.length < 1 && (
          <div className=" w-full text-base">작성된 후기가 없습니다</div>
        )}
        {reviewComment.map((el, i) => (
          <DetailReview key={i} type={'roomDetail'} reviewComment={el} />
        ))}
      </div>
      <div className="my-[20px]">
        {reviewComment.length < 1 ? (
          <CommonBtn
            text={`후기 작성 하러 가기`}
            btnSize={'w-[210px] h-[40px]'}
            btnHandler={commentHandler}
          />
        ) : (
          <CommonBtn
            text={`${reviewComment.length}개 후기 모두 보기`}
            btnSize={'w-[210px] h-[40px]'}
            btnHandler={commentHandler}
          />
        )}
      </div>
    </div>
  );
};
export default GhDetailComment;
