import React, { useEffect, useState } from 'react';
import Facilities from '../common/Facilities';
import FacilitiesArr from '../common/FacilitiesArray';

type GhOption = {
  icon: JSX.Element;
  name: string;
  checked: boolean;
};

type GhOptionProps = {
  icons: GhOption[];
  setIcons: React.Dispatch<React.SetStateAction<GhOption[]>>;
};

export default function FacilitiesContainer({
  icons,
  setIcons,
}: GhOptionProps) {
  // 옵션을 boolean 값으로만 보내줘야함. post 로직에 들어가면 될듯

  const selectIcon = (name: string) => {
    setIcons((prev) => {
      const icons = prev.map((facilities) => {
        if (facilities.name === name) {
          return { ...facilities, checked: !facilities.checked };
        } else {
          return { ...facilities };
        }
      });

      return [...icons];
    });
  };

  return (
    <div className="flex flex-col">
      <p className="font-bold text-lg md:text-lg mb-2.5">숙소 편의시설 등록</p>
      <div className="flex flex-col w-full justify-around items-center flex-wrap p-2.5 md:flex-row ">
        {icons.map((x) => (
          <div key={x.name} className="w-3/4 md:w-1/4 mr-2.5 mb-2.5">
            <Facilities
              icon={x.icon}
              name={x.name}
              isEditing={true}
              isExist={x.checked}
              selectIcon={selectIcon}
            />
          </div>
        ))}
      </div>
    </div>
  );
}
