import React, { useState } from 'react';
import SelectLocation from './SelectLocation';

const Location = () => {
  const [isLocationOpen, setIsLocationOpen] = useState(false);
  const modalHandler = (state: boolean, setState: any) => {
    setState(!state);
  };

  return (
    <>
      <div
        className="mx-10 relative"
        onClick={() => {
          modalHandler(isLocationOpen, setIsLocationOpen);
        }}
      >
        지역
      </div>
      {isLocationOpen && <SelectLocation />}
    </>
  );
};

export default Location;
