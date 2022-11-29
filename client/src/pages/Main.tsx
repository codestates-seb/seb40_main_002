// import React, { useCallback, useEffect, useState } from 'react';
// import { useInView } from 'react-intersection-observer';
import { useEffect } from 'react';
import GuesthouseList from '../components/Main/GuesthouseList';
import useInfiniteScroll from '../hooks/useInfiniteScroll';
import { GuestHouseShort } from '../types/guesthouse';
import { useSelector } from 'react-redux';
import { RootState } from '../store/store';

function Main() {
  const mainUser = useSelector((state: RootState) => state.user);
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
  const [list, setSortType, ref] = useInfiniteScroll('/api/all-guesthouse');
  useEffect(() => {
    // console.log('Main User: ', mainUser);
    // 추천 게스트하우스 지정
  }, [mainUser]);

  return (
    <div className="w-full p-[20px] h-full">
      <GuesthouseList header={'추천'} guesthouses={guesthouses.slice(0, 8)} />
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
