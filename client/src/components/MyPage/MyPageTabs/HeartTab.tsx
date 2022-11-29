import useInfiniteScroll from '../../../hooks/useInfiniteScroll';
import { GuestHouseShort } from '../../../types/guesthouse';
import GuesthouseList from '../../Main/GuesthouseList';

function HeartTab() {
  // const [list, , ref] = useInfiniteScroll('');
  const testGh: GuestHouseShort = {
    imgSrc:
      'https://images.unsplash.com/photo-1580587771525-78b9dba3b914?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1674&q=80',
    name: '정우네 게스트 하우스',
    price: 105000,
    star: 4.5,
    tags: ['오션뷰', '대리석', '시골'],
    id: 1,
  };
  const guesthouses: Array<GuestHouseShort> = new Array(10).fill(testGh);

  return (
    <div className="overflow-auto">
      <GuesthouseList guesthouses={guesthouses} isMyPage={true} />
      {/* <div ref={ref}>...</div> */}
    </div>
  );
}

export default HeartTab;
