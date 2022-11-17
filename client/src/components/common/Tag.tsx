import React from 'react';

interface Props {
  name: string;
  isSelected?: boolean;
}

function Tag({ name, isSelected = true }: Props) {
  return (
    <span
      className={`${
        isSelected
          ? 'bg-point-color text-white border-point-color'
          : 'bg-white text-font-color border-font-color'
      } w-fit text-sm rounded-full border-[1px] px-2 py-0.5 mr-[4px] my-[2px] block`}
    >
      {`#${name}`}
    </span>
  );
}

export default Tag;
