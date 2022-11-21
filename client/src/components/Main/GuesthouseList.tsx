import React from 'react';
import { GuestHouseShort } from '../../types/guesthouse';
import GuesthouseCard from '../common/GuesthouseCard';
import SortButton from './SortButton';

interface Props {
  guesthouses: Array<GuestHouseShort>;
  header?: string;
  setSortType?: React.Dispatch<React.SetStateAction<string>>;
  listLength?: number;
}

function GuesthouseList({ guesthouses, header, setSortType }: Props) {
  return (
    <div className="w-full">
      <div className="flex items-center justify-between">
        <h1 className="text-lg font-semibold my-[12px]">{header}</h1>
        {setSortType && (
          <div>
            <SortButton sortType={'standard'} setSortType={setSortType} />
            <SortButton sortType={'star'} setSortType={setSortType} />
            <SortButton sortType={'review'} setSortType={setSortType} />
          </div>
        )}
      </div>
      <div className="grid sm:grid-cols-2 md:grid-cols-3 lg:grid-cols-4 gap-[20px] w-full h-full">
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
