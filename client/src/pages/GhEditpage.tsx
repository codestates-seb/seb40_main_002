import React from 'react';
import FacilitiesContainer from '../components/ghEdit/FacilitiesContainer';
import GhDescription from '../components/ghEdit/GhDescription';
import Ghname from '../components/ghEdit/Ghname';
import ImageContainer from '../components/ghEdit/ImageContainer';
import Messenger from '../components/ghEdit/Messenger';
import RoomEdit from '../components/ghEdit/RoomEdit';
import TagContainer from '../components/ghEdit/TagContainer';

export default function GhEditpage() {
  return (
    <div className="mt-8 p-3 w-full flex justify-center md:mt-20">
      <div className="md:max-w-[1120px] w-full">
        <Ghname />
        <TagContainer />
        <ImageContainer />
        <GhDescription />
        <RoomEdit />
        <FacilitiesContainer />
        <Messenger />
      </div>
    </div>
  );
}
