import React from 'react';
import { GuestHouseShort } from '../../types/guesthouse';
import GuesthouseCard from '../common/GuesthouseCard';
import SortButton from './SortButton';

interface Props {
  guesthouses: Array<GuestHouseShort>;
  header?: string;
  setSortType?: React.Dispatch<React.SetStateAction<string>>;
  listLength?: number;
  isMyPage?: boolean;
}

function GuesthouseList({
  guesthouses,
  header,
  setSortType,
  isMyPage = false,
}: Props) {
  return (
    <div className="w-full mb-[48px]">
      <div className="flex items-center justify-between">
        <h1 className="text-lg font-semibold mb-[12px]">{header}</h1>
        {setSortType && (
          <div>
            <SortButton sortType={'default'} setSortType={setSortType} />
            <SortButton sortType={'star'} setSortType={setSortType} />
            <SortButton sortType={'review'} setSortType={setSortType} />
          </div>
        )}
      </div>
      <div
        className={`grid ${
          isMyPage
            ? 'sm:grid-cols-1 md:grid-cols-2 lg:grid-cols-3'
            : 'sm:grid-cols-2 md:grid-cols-3 lg:grid-cols-4'
        } gap-[20px] w-full h-full`}
      >
        {guesthouses.map((el, idx) => (
          <React.Fragment key={idx}>
            <GuesthouseCard guesthouse={el} />
          </React.Fragment>
        ))}
      </div>
    </div>
  );
}

export default GuesthouseList;
