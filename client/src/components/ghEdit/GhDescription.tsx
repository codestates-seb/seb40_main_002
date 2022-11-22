import React, { useState } from 'react';

type GhInfo = {
  guestHouseInfo: string;
  setGuestHouseInfo: React.Dispatch<React.SetStateAction<string>>;
};

export default function GhDescription({
  guestHouseInfo,
  setGuestHouseInfo,
}: GhInfo) {
  // 로직 위로 올리기

  return (
    <div>
      <p className="font-bold text-lg mb-2.5">숙소 설명</p>
      <textarea
        // ref={ref}
        className="border mr-[15px] h-20 border-border-color mb-3 md:mb-5 rounded-btnRadius w-full h- resize-none pl-[5px] focus:border-border-color focus : border focus:outline-none"
        onChange={(e) => setGuestHouseInfo(e.target.value)}
        value={guestHouseInfo}
      />
    </div>
  );
}
