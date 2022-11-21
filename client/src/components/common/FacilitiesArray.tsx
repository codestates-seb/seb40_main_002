import {
  TbToolsKitchen2,
  TbDeviceGamepad,
  TbDeviceDesktop,
} from 'react-icons/tb';
import React from 'react';
import { GiWashingMachine, GiTowel, GiPartyFlags } from 'react-icons/gi';
import { AiOutlineWifi, AiOutlineCar } from 'react-icons/ai';
import { WiSunrise } from 'react-icons/wi';

interface Obj {
  icon: JSX.Element;
  name: string;
  checked: boolean;
}

export default function FacilitiesArr(): Obj[] {
  return [
    { icon: <TbToolsKitchen2 size={'20px'} />, name: '주방', checked: false },
    {
      icon: <GiWashingMachine size={'20px'} />,
      name: '세탁기',
      checked: false,
    },
    {
      icon: <AiOutlineWifi size={'20px'} />,
      name: '무선 인터넷',
      checked: false,
    },
    { icon: <WiSunrise size={'20px'} />, name: '오션뷰', checked: false },
    { icon: <GiTowel size={'20px'} />, name: '어메니티', checked: false },
    { icon: <AiOutlineCar size={'20px'} />, name: '주차장', checked: false },
    {
      icon: <TbDeviceGamepad size={'20px'} />,
      name: '보드게임 제공',
      checked: false,
    },
    {
      icon: <TbDeviceDesktop size={'20px'} />,
      name: '업무 전용 공간',
      checked: false,
    },
    { icon: <GiPartyFlags size={'20px'} />, name: '파티 여부', checked: false },
  ];
}
