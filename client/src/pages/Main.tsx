import { useCallback, useEffect, useState } from 'react';
import { useInView } from 'react-intersection-observer';
import GuesthouseList from '../components/Main/GuesthouseList';
import { GuestHouseShort } from '../types/guesthouse';

function Main() {
  // 임시 데이터
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
    setLoading(false);
  }, [page]);

  // sortType에 따라 다르게 api 요청하기
  useEffect(() => {
    setPage(1);
    getList();
  }, [sortType]);

  // 페이지 설정
  useEffect(() => {
    // 사용자가 마지막 요소를 보고 있고, 로딩 중이 아니라면
    // console.log(inView, loading);
    if (inView && !loading) {
      setPage((page) => page + 1);
    }
  }, [inView, loading]);

  return (
    <div className="w-full p-[20px]">
      <GuesthouseList header={'추천'} guesthouses={guesthouses.slice(0, 8)} />
      <GuesthouseList
        header={'일반'}
        guesthouses={list}
        setSortType={setSortType}
        listLength={list.length}
      />
      <div ref={ref}></div>
    </div>
  );
}

export default Main;