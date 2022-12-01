import React, { useState, SetStateAction, Dispatch, useEffect } from 'react';

interface Props {
  totalPages: number;
  page: number | null;
  setPage: Dispatch<SetStateAction<number | null>>;
}

const MyPagePagination = ({ totalPages, page, setPage }: Props) => {
  return (
    <div>
      {Array(totalPages)
        .fill(0)
        .map((el, i) => (
          <button
            className={`${
              page == i + 1 ? 'border-b-[2px] border-black' : null
            } ml-[10px] py-[2px] px-[12px] mb-[20px] pointer-events-auto`}
            key={i}
            onClick={() => setPage(i + 1)}
          >
            {i + 1}
          </button>
        ))}
    </div>
  );
};
export default MyPagePagination;
