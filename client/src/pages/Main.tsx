// import React, { useCallback, useEffect, useState } from 'react';
// import { useInView } from 'react-intersection-observer';
import { useCallback, useEffect, useState } from 'react';
import GuesthouseList from '../components/Main/GuesthouseList';
import useInfiniteScroll from '../hooks/useInfiniteScroll';
import { GuestHouseShort } from '../types/guesthouse';
import { useSelector } from 'react-redux';
import { RootState } from '../store/store';
import { isLogin } from '../utils/isLogin';
import { getGuesthouseList } from '../apis/guesthouse';

function Main() {
  const mainUser = useSelector((state: RootState) => state.user);
  const [list, setSortType, ref] = useInfiniteScroll('/api/all-guesthouse');
  const [myRecommended, setMyRecommended] = useState<GuestHouseShort[]>([]);

  const getRecommended = useCallback(async () => {
    const myTags = mainUser.memberTag.join('&tag=');
    const path = `/api/all-guesthouse?page=1&size=10&tag=${myTags}&sort=default`;
    // const path = `/api/all-guesthouse?page=1&size=10&tag=${'오션뷰'}&sort=default`;

    console.log(path);
    const recommended = await getGuesthouseList(path);
    setMyRecommended(recommended);
  }, [mainUser.memberTag]);

  useEffect(() => {
    getRecommended();
  }, [mainUser.memberTag]);

  return (
    <div className="w-full p-[20px] h-full">
      {isLogin() && (
        <GuesthouseList header={'추천'} guesthouses={myRecommended} />
      )}
      <GuesthouseList
        header={'일반'}
        guesthouses={list}
        setSortType={setSortType}
        listLength={list.length}
      />
      <div ref={ref} className="text-white">
        ...
      </div>
    </div>
  );
}

export default Main;
