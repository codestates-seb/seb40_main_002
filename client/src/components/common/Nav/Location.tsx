import React, { useState } from 'react';
import SelectLocation from './SelectLocation';
import Tag from '../Tag';

const locations = [
  '제주',
  '애월',
  '성산',
  '장소1',
  '장소2',
  '장소3',
  '장소4',
  '장소5',
  '장소6',
  '장소7',
];

const Location = () => {
  const [isLocationOpen, setIsLocationOpen] = useState(false);
  const [selects, setSelects] = useState(
    new Array(locations.length).fill(false)
  );
  const modalHandler = () => {
    setIsLocationOpen(!isLocationOpen);
  };
  const onlyTrue = selects.filter((isSelets) => isSelets && true);

  return (
    <>
      <div className="relative flex" onClick={modalHandler}>
        {onlyTrue.length === 0 ? (
          <button className="w-fit"> 지역을 선택해주세요 </button>
        ) : (
          selects.map(
            (type: boolean, idx) =>
              type && <Tag key={idx} name={locations[idx]} />
          )
        )}
      </div>
      {isLocationOpen && (
        <SelectLocation
          locations={locations}
          selects={selects}
          setSelects={setSelects}
          modalHandler={modalHandler}
          onlyTrue={onlyTrue}
        />
      )}
    </>
  );
};

export default Location;
