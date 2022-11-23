import React, { useState } from 'react';
import Facilities from '../common/Facilities';
import FacilitiesArr from '../common/FacilitiesArray';

export default function GhDetailFacilities({
  GhFacilities,
}: {
  GhFacilities: boolean[];
}) {
  const [icons, setIcons] = useState(FacilitiesArr());

  return (
    <div className="flex flex-col">
      <p className="font-bold  mb-2.5">숙소 편의시설</p>
      <div className="flex flex-col w-full justify-around items-center flex-wrap p-2.5 md:flex-row ">
        {GhFacilities.map((x, i) => {
          if (x) {
            return (
              <div key={icons[i].name} className="w-3/4 md:w-1/4 mr-2.5 mb-2.5">
                <Facilities
                  icon={icons[i].icon}
                  name={icons[i].name}
                  isEditing={false}
                  isExist={icons[i].checked}
                />
              </div>
            );
          } else {
            return;
          }
        })}
      </div>
    </div>
  );
}
