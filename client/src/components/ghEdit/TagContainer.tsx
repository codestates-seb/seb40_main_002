import React, { useState } from 'react';
import Tag from '../common/Tag';
import { TagSelectButton } from '../common/TagSelectModal/TagSelectButton';

type GhTag = {
  guestHouseTag: string[];
  setGuestHouseTag: React.Dispatch<React.SetStateAction<string[]>>;
};

export default function TagContainer({
  guestHouseTag,
  setGuestHouseTag,
}: GhTag) {
  return (
    <div className="mb-2.5 w-full md:w-1/2 md:mb-5">
      <p className="font-bold text-lg mb-2.5">태그</p>
      <div className="mb-2.5 flex items-center">
        {guestHouseTag.length > 0 &&
          guestHouseTag.map((x) => <Tag name={x} key={x} />)}
        <TagSelectButton setTags={setGuestHouseTag} />
      </div>
    </div>
  );
}
