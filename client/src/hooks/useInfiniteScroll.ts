import { useCallback, useEffect, useState } from 'react';
import { useInView } from 'react-intersection-observer';
import { useLocation } from 'react-router-dom';
import { getGuesthouseList } from '../apis/guesthouse';
import { GuestHouseShort } from '../types/guesthouse';
import { SearchOption } from '../types/search';
import getTodayToTomorrow from '../utils/getTodayToTomorrow';

function useInfiniteScroll(
  path: string
  // option?: SearchOption
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
  const start = url.get('start');
  const end = url.get('end');
  const tag = url.get('tag');

  const tags = tag?.split('-');

  // let option: SearchOption = {
  //   cityId: 1, // 변경해야 함
  //   start: start ? start : getTodayToTomorrow().today,
  //   end: end ? end : getTodayToTomorrow().tomorrow,
  //   tags: tags ? tags : [],
  // };
  const [option, setOption] = useState<SearchOption>({
    cityId: 1, // 변경해야 함
    start: start ? start : getTodayToTomorrow().today,
    end: end ? end : getTodayToTomorrow().tomorrow,
    tags: tags ? tags : [],
  });

  // 왜 안 돼
  // useEffect(() => {
  //   if (list.length > 0) {
  //     console.log(url.get('start'), url.get('end'), url.get('tag'));
  //     const start = url.get('start');
  //     const end = url.get('end');
  //     const tag = url.get('tag');

  //     const tags = tag?.split('-');

  //     setOption({
  //       cityId: 1, // 변경해야 함
  //       start: start ? start : getTodayToTomorrow().today,
  //       end: end ? end : getTodayToTomorrow().tomorrow,
  //       tags: tags ? tags : [],
  //     });

  //     setPage(1);
  //     setList([]);
  //     getList();
  //   }
  // }, [url.get('start'), url.get('end'), url.get('tag')]);

  // 숙소 리스트 가져오기
  const getList = useCallback(async () => {
    setLoading(true);
    let optionApi, tagApi;
    if (option) {
      optionApi = `&cityId=${option.cityId}&start=${option.start}&end=${option.end}`;
      tagApi = option.tags.join('&tag=');
      // console.log(optionApi, tagApi);
    }
    if (totalCount > list.length) {
      const newGuesthouses = await getGuesthouseList(
        `${path}?page=${page}&size=10&sort=${sortType}&tag=${
          tagApi ? tagApi : ''
        }${optionApi ? optionApi : ''}`, // option 있을 경우 추가
        setTotalCount
      );
      setList([...list, ...newGuesthouses]);
    }
    setLoading(false);
  }, [page]);

  // page에 따라 다르게 api 요청하기
  useEffect(() => {
    getList();
  }, [page]);

  // sortType에 따라 다르게 api 요청하기
  useEffect(() => {
    if (list.length > 0) {
      setPage(1);
      setList([]);
      getList();
      // console.log('sortType: ', sortType);
    }
  }, [sortType]);

  // 왜 안 돼 2
  // useEffect(() => {
  //   if (list.length > 0) {
  //     setPage(1);
  //     setList([]);
  //     getList();
  //     // console.log('option: ', option);
  //   }
  // }, [
  //   option?.start,
  //   option?.end,
  //   option?.tags[0],
  //   option?.tags[1],
  //   option?.tags[2],
  // ]);

  // 페이지 설정
  useEffect(() => {
    // 사용자가 마지막 요소를 보고 있고, 로딩 중이 아니라면
    if (inView && !loading) {
      setPage((page) => page + 1);
    }
  }, [inView, loading]);

  return [list, setSortType, ref, totalCount];
}

export default useInfiniteScroll;
