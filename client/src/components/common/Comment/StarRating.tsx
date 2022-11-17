import React, { Dispatch, SetStateAction } from 'react';

import { FaStar } from 'react-icons/fa';
import { TiStarOutline } from 'react-icons/ti';
type Props = {
  star: boolean[];
  setStar: Dispatch<SetStateAction<boolean[]>>;
  // setScore: (prev: number) => void;
};

export default function StarRating({ star, setStar }: Props) {
  const onClick = (starIdx: number) => {
    const clicked = [...star].map((x, idx) => {
      if (starIdx >= idx) {
        return true;
      } else {
        return false;
      }
    });
    setStar([...clicked]);
  };
  return (
    <div className="flex">
      {star.map((x, idx) => {
        if (x) {
          return (
            <FaStar
              key={idx}
              size={'18px'}
              className="fill-yellow-300"
              onClick={() => {
                onClick(idx);
              }}
            />
          );
        } else {
          return (
            <TiStarOutline
              key={idx}
              size={'18px'}
              className="hover:fill-yellow-300"
              onClick={() => {
                onClick(idx);
              }}
            />
          );
        }
      })}
    </div>
  );
}
