import { useCallback, useEffect, useState } from 'react';
import { useInView } from 'react-intersection-observer';
import { getGuesthouseList } from '../apis/guesthouse';
import { GuestHouseShort } from '../types/guesthouse';

function useInfiniteScroll2(
  path: string,
  sortType: string
): [GuestHouseShort[], (node?: Element | null | undefined) => void, number] {
  const [totalCount, setTotalCount] = useState(11);

  const [list, setList] = useState<GuestHouseShort[]>([]);
  const [page, setPage] = useState(1);
  const [loading, setLoading] = useState(false);
  const [ref, inView] = useInView();

  // 숙소 리스트 가져오기
  const getList = useCallback(async () => {
    setLoading(true);
    const newGuesthouses = await getGuesthouseList(
      `${path}&page=${page}&sort=${sortType}`,
      setTotalCount
    );
    if (page > 1) setList([...list, ...newGuesthouses]);
    else setList([...newGuesthouses]);

    setLoading(false);
  }, [page, sortType]);

  let isCalled = false;

  // page에 따라 다르게 api 요청하기
  useEffect(() => {
    if (!isCalled) {
      isCalled = true;
      getList();
    }
  }, [page]);

  // sortType에 따라 다르게 api 요청하기
  useEffect(() => {
    if (list.length > 0) {
      setPage(1);
      getList();
    }
  }, [sortType]);

  // 페이지 설정
  useEffect(() => {
    // 사용자가 마지막 요소를 보고 있고, 로딩 중이 아니라면
    if (inView && !loading && totalCount > list.length) {
      setPage((page) => page + 1);
    }
  }, [inView, loading]);

  return [list, ref, totalCount];
}

export default useInfiniteScroll2;
