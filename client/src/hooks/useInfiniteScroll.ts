import { useCallback, useEffect, useState } from 'react';
import { useInView } from 'react-intersection-observer';
import { useLocation, useSearchParams } from 'react-router-dom';
import { getGuesthouseList } from '../apis/guesthouse';
import { GuestHouseShort } from '../types/guesthouse';
import { SearchOption } from '../types/search';

function useInfiniteScroll(
  path: string
): [
  GuestHouseShort[],
  React.Dispatch<React.SetStateAction<string>>,
  (node?: Element | null | undefined) => void,
  number
] {
  const [totalCount, setTotalCount] = useState(11);

  const [sortType, setSortType] = useState('default');
  const [list, setList] = useState<GuestHouseShort[]>([]);
  const [page, setPage] = useState(1);
  const [loading, setLoading] = useState(false);
  const [ref, inView] = useInView();

  const location = useLocation();
  const url = new URLSearchParams(location.search);
  const cityId = url.get('cityId');
  const start = url.get('start');
  const end = url.get('end');
  const tag = url.get('tag');

  const tags = tag?.split('-');
  const [option, setOption] = useState<SearchOption>({
    cityId: Number(cityId),
    start: start ? start : '',
    end: end ? end : '',
    tags: tags ? tags : [],
  });

  // const [searchParams] = useSearchParams();

  // useEffect(() => {
  //   const paramCityId = searchParams.get('cityId');
  //   const paramStart = searchParams.get('start');
  //   const paramEnd = searchParams.get('end');
  //   const paramTag = searchParams.get('tag');

  //   if (paramCityId && paramStart && paramEnd && paramTag) {
  //     setOption({
  //       cityId: Number(paramCityId),
  //       start: paramStart,
  //       end: paramEnd,
  //       tags: paramTag.split('-'),
  //     });
  //   }
  // }, [searchParams]);

  // useEffect(() => {
  //   console.log('changed!');
  //   setPage(1);
  //   setList([]);
  //   getList();
  // }, [option]);

  // 숙소 리스트 가져오기
  const getList = useCallback(async () => {
    setLoading(true);
    let optionApi, tagApi;
    if (option && option.cityId !== 0) {
      optionApi = `&cityId=${option.cityId}&start=${option.start}&end=${option.end}`;
      tagApi = option.tags.join('&tag=');
    }
    const newGuesthouses = await getGuesthouseList(
      `${path}?page=${page}&size=10&sort=${sortType}&tag=${
        tagApi ? tagApi : ''
      }${optionApi ? optionApi : ''}`, // option 있을 경우 추가
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

  return [list, setSortType, ref, totalCount];
}

export default useInfiniteScroll;
