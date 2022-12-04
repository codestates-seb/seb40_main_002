import React from 'react';

import { AiOutlineArrowRight } from 'react-icons/ai';
import { useNavigate } from 'react-router-dom';

type Props = {
  houseName: string;
  date: string;
  roomLink: string;
};
//
export default function TopLabel({ houseName, date, roomLink }: Props) {
  const navigate = useNavigate();
  return (
    <div className="flex justify-between items-center pl-[22px] pr-[22px] h-[45px] border-b border-color text-base">
      <div className="flex">
        <p className="mr-[25px]">{houseName} </p>
        <p className="mr-[7px]">방문 일자</p>
        <p className="text-font-color">{date}</p>
      </div>

      <AiOutlineArrowRight
        className="cursor-pointer text-font-color"
        onClick={() => navigate(roomLink)}
      />
    </div>
  );
}
