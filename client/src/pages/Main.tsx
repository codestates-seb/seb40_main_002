import { useCallback, useEffect, useState } from 'react';
import GuesthouseList from '../components/Main/GuesthouseList';
import useInfiniteScroll from '../hooks/useInfiniteScroll';
import { GuestHouseShort } from '../types/guesthouse';
import { useDispatch, useSelector } from 'react-redux';
import { RootState } from '../store/store';
import { isLogin } from '../utils/isLogin';
import { getGuesthouseList } from '../apis/guesthouse';
import Door from './Door';
import { visited } from '../store/reducer/door';

function Main() {
  const dispatch = useDispatch();
  const doorOpened = useSelector((state: RootState) => state.door.isOpened);
  const [openDoor, setOpenDoor] = useState(true);

  useEffect(() => {
    if (doorOpened) setOpenDoor(false);
    else {
      setTimeout(() => {
        dispatch(visited());
        setOpenDoor(!openDoor);
      }, 5000);
    }
  }, [openDoor]);
  const mainUser = useSelector((state: RootState) => state.user);
  const [list, setSortType, ref] = useInfiniteScroll('/api/all-guesthouse');
  const [myRecommended, setMyRecommended] = useState<GuestHouseShort[]>([]);

  const getRecommended = useCallback(async () => {
    const myTags = mainUser.memberTag.join('&tag=');
    const path = `/api/all-guesthouse?page=1&size=10&tag=${myTags}&sort=default`;

    const recommended = await getGuesthouseList(path);
    setMyRecommended(recommended);
  }, [mainUser.memberTag]);

  let isRecommended = false;

  useEffect(() => {
    // console.log('in');
    if (!isRecommended) {
      isRecommended = true;
      getRecommended();
    }
  }, [getRecommended]);

  return (
    <div className="w-full p-[20px] h-full">
      {openDoor && <Door />}
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
