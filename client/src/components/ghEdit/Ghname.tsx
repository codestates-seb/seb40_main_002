import React from 'react';

type GhName = {
  guestHouseName: string;
  setGuestHouseName: React.Dispatch<React.SetStateAction<string>>;
};
export default function Ghname({ guestHouseName, setGuestHouseName }: GhName) {
  return (
    <div className="flex flex-col">
      <p className="font-bold text-lg mb-2.5">숙소 명</p>
      <input
        type="text"
        // ref={ref}
        className="border pl-2.5 mx-2 border-border-color mb-3 md:mb-5 rounded-btnRadius md:w-1/2 focus:border-border-color focus : border focus:outline-none"
        onChange={(e) => setGuestHouseName(e.target.value)}
        value={guestHouseName}
        placeholder="숙소명을 입력해주세요"
      />
    </div>
  );
}
