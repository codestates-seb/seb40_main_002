import GuesthouseList from '../components/Main/GuesthouseList';
import useInfiniteScroll from '../hooks/useInfiniteScroll';

function SearchResult() {
  const [list, setSortType, ref, totalCount] =
    useInfiniteScroll('/api/guesthouse');

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
