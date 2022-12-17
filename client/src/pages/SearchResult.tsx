import { useState } from 'react';
import { useLocation } from 'react-router-dom';
import GuesthouseList from '../components/Main/GuesthouseList';
// import useInfiniteScroll from '../hooks/useInfiniteScroll';
import useInfiniteScroll2 from '../hooks/useInfiniteScroll2';
import getTodayToTomorrow from '../utils/getTodayToTomorrow';

function SearchResult() {
  const todayToTomorrow = getTodayToTomorrow();
  const location = useLocation();
  const url = new URLSearchParams(location.search);
  const cityId = url.get('cityId');
  const start = url.get('start');
  const end = url.get('end');
  const tag = url.get('tag');

  const tags = tag?.split('-');

  const [sortType, setSortType] = useState('default');
  const [list, ref, totalCount] =
    // useInfiniteScroll('/api/guesthouse');
    useInfiniteScroll2(
      `/api/guesthouse?size=10&cityId=${cityId}&start=${
        start ? start : todayToTomorrow.today
      }&end=${end ? end : todayToTomorrow.tomorrow}&tag=${
        tags && tags.join('&tag=')
      }`,
      sortType
    );

  return (
    <div className="w-full p-[20px]">
      <GuesthouseList
        header={`${totalCount}개의 검색 결과`}
        guesthouses={list}
        setSortType={setSortType}
        listLength={list.length}
      />
      <div className="text-white" ref={ref}>
        ...
      </div>
    </div>
  );
}

export default SearchResult;
