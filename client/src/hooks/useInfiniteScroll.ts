import { useCallback, useEffect, useState } from 'react';
import { useInView } from 'react-intersection-observer';
import { GuestHouseShort } from '../types/guesthouse';

const testGh: GuestHouseShort = {
  imgSrc:
    'https://images.unsplash.com/photo-1580587771525-78b9dba3b914?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1674&q=80',
  name: '정우네 게스트 하우스',
  price: 105000,
  star: 4.5,
  tags: ['오션뷰', '대리석', '시골'],
  id: 1,
};
const guesthouses: Array<GuestHouseShort> = new Array(100).fill(testGh);

function useInfiniteScroll(path: string): [
  //   inView: boolean
  GuestHouseShort[],
  React.Dispatch<React.SetStateAction<string>>,
  (node?: Element | null | undefined) => void,
  number
] {
  const [totalCount, setTotalCount] = useState(0);

  const [sortType, setSortType] = useState('');
  const [list, setList] = useState<GuestHouseShort[]>([]);
  const [page, setPage] = useState(1);
  const [loading, setLoading] = useState(false);
  const [ref, inView] = useInView();

  // 숙소 리스트 가져오기 (임시)
  const getList = useCallback(async () => {
    setLoading(true);
    // await axios.get(`${Your Server Url}/page=${page}`).then((res) => {
    //   setList(prevState => [...prevState, ...res])
    // })
    setList([
      ...list,
      ...guesthouses.slice((page - 1) * 8, (page - 1) * 8 + 8), // 임시
    ]);
    setTotalCount(guesthouses.length); // 임시
    setLoading(false);
    // console.log(page);
  }, [page]);

  // sortType, page에 따라 다르게 api 요청하기
  useEffect(() => {
    // setPage(1);
    getList();
  }, [sortType, page]);

  // 페이지 설정
  useEffect(() => {
    // 사용자가 마지막 요소를 보고 있고, 로딩 중이 아니라면
    // console.log(inView, loading);
    if (inView && !loading) {
      setPage((page) => page + 1);
      // console.log(page);
    }
  }, [inView, loading]);

  return [list, setSortType, ref, totalCount];
}

export default useInfiniteScroll;
