import useInfiniteScroll from '../../../hooks/useInfiniteScroll';
import GuesthouseList from '../../Main/GuesthouseList';

function HeartTab() {
  const [list, , ref] = useInfiniteScroll('');

  return (
    <div className="overflow-auto">
      <GuesthouseList guesthouses={list} isMyPage={true} />
      <div ref={ref}>...</div>
    </div>
  );
}

export default HeartTab;
