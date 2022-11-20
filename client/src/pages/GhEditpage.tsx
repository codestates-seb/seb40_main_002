import React from 'react';
import FacilitiesContainer from '../components/ghEdit/FacilitiesContainer';
import GhDescription from '../components/ghEdit/GhDescription';
import Messenger from '../components/ghEdit/Messenger';

export default function GhEditpage() {
  return (
    <div className="mt-8 p-3 w-full flex justify-center md:mt-20">
      <div className="md:max-w-[1120px] w-full">
        <GhDescription />
        <FacilitiesContainer />
        <Messenger />
      </div>
    </div>
  );
}
