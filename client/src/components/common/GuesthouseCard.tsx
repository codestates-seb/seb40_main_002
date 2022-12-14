import { useNavigate } from 'react-router-dom';
import { GuestHouseShort } from '../../types/guesthouse';
import getTodayToTomorrow from '../../utils/getTodayToTomorrow';
import Heart from './Heart';
import RatedStar from './RatedStar';
import Tag from './Tag';

function GuesthouseCard({
  guesthouse,
  start,
  end,
}: {
  guesthouse: GuestHouseShort;
  start?: string;
  end?: string;
}) {
  const navigate = useNavigate();
  const handleToGuesthouse = () => {
    // 해당 게스트하우스 링크로 이동
    if (start === undefined && end === undefined) {
      const startEnd = getTodayToTomorrow();
      start = startEnd.today;
      end = startEnd.tomorrow;
    }
    navigate(`/ghdetail/${guesthouse.id}?start=${start}&end=${end}`); // start, end 지정 필요
  };
  return (
    <div className="w-full h-full">
      <button onClick={handleToGuesthouse}>
        <div className="relative">
          <img
            src={guesthouse.imgSrc}
            className="aspect-[27/25] h-full rounded-[15px] object-cover"
          />
          <span className="absolute top-[12px] right-[12px]">
            <Heart id={guesthouse.id} />
          </span>
        </div>
        <div className="px-1">
          <div className="flex justify-between ">
            <div className="block whitespace-wrap overflow-hidden text-lg font-bold text-start mr-[2px] my-[2px] text-ellipsis">
              {guesthouse.name}
            </div>
            <RatedStar star={guesthouse.star} />
          </div>
          <div className="w-fit text-base">{`${guesthouse.price.toLocaleString()} KRW / 1박`}</div>
        </div>
      </button>
      <div className="flex px-1 py-1 whitespace-wrap">
        {guesthouse.tags.map((el: string) => (
          <Tag key={el} name={el} />
        ))}
      </div>
    </div>
  );
}

export default GuesthouseCard;
