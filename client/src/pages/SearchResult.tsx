// import { useCallback, useEffect, useState } from 'react';
// import { useInView } from 'react-intersection-observer';
import { useLocation } from 'react-router-dom';
import GuesthouseList from '../components/Main/GuesthouseList';
import useInfiniteScroll from '../hooks/useInfiniteScroll';
// import { GuestHouseShort } from '../types/guesthouse';

function SearchResult() {
  // useParams로 조건 가져오기
  // or useLocation으로 navigate 시 넘겨주는 props 가져오기
  const { state } = useLocation();
  console.log(state);

  const [list, setSortType, ref, totalCount] = useInfiniteScroll('');

  return (
    <div className="w-full p-[20px]">
      <GuesthouseList
        header={`${totalCount}개의 검색 결과`}
        guesthouses={list}
        setSortType={setSortType}
        listLength={list.length}
      />
      <div ref={ref}>...</div>
    </div>
  );
}

export default SearchResult;
