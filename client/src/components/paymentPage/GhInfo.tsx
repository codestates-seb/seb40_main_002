import React from 'react';
import { Props } from '../../types/payment';
import RatedStar from '../common/RatedStar';
export default function GhInfo({ ghData }: { ghData: Props }) {
  console.log(ghData.ratedScore);
  return (
    <>
      {ghData.url && (
        <div className="flex w-full pb-[20px] border-b border-border-color mb-[20px]">
          <img
            src={ghData.url}
            alt=""
            className="w-[80px] h-[60px] mr-[10px] md:mr-[30px] rounded-CommentRadius md:w-[200px] md:h-[160px] md:rounded-ImgRadius"
          />
          <div className="flex flex-col justify-between">
            <div>
              <p className="font-bold text-base md:text-xl">{ghData.ghname}</p>
              <p className="font-bold text-sm md:text-base">
                {ghData.ghRoomname}
              </p>
            </div>
            <div className="flex items-center">
              {ghData.ratedScore !== null && (
                <RatedStar star={ghData.ratedScore} />
              )}
              {ghData.reply && ghData.reply.length >= 0 && (
                <p className="text-font-color text-base">
                  (후기 {ghData.reply.length}개)
                </p>
              )}
            </div>
          </div>
        </div>
      )}
    </>
  );
}
