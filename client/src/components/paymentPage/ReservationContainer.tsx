import React from 'react';
import { Props } from '../../types/payment';
// { ghData }: Props
export default function ReservationContainer({ ghData }: { ghData: Props }) {
  // console.log(ghData);
  return (
    <div className="mb-[34px] w-full">
      <div className="font-bold text-[22px] mb-[22px]">예약 정보</div>
      <div className="p-[8px] border-b border-border-color">
        <div className="font-bold text-lg mb-[5px]">날짜</div>
        <div className="text-base ml-[8px] mb-[5px]">
          {ghData.filterDate && ghData.filterDate[1]} (
          {ghData.filterDate && ghData.filterDate[0]}박)
        </div>
        <div className="font-bold text-lg mb-[5px]">게스트</div>
        <div className="text-base mb-[24px] ml-[8px]">게스트 1명</div>
      </div>
    </div>
  );
}
