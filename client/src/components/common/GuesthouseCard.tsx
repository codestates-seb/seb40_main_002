import { useNavigate } from 'react-router-dom';
import { GuestHouseShort } from '../../types/guesthouse';
import getTodayToTomorrow from '../../utils/getTodayToTomorrow';
import Heart from './Heart';
import RatedStar from './RatedStar';
import Tag from './Tag';

function GuesthouseCard({ guesthouse }: { guesthouse: GuestHouseShort }) {
  const navigate = useNavigate();
  const handleToGuesthouse = () => {
    // 해당 게스트하우스 링크로 이동
    navigate(
      `/ghdetail/${guesthouse.id}?start=${getTodayToTomorrow().today}&end=${
        getTodayToTomorrow().tomorrow
      }`
    ); // start, end 지정 필요
    console.log('gonna move to', guesthouse.id);
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
            <div className="block whitespace-nowrap overflow-hidden text-lg font-bold text-start mr-[2px] my-[2px] text-ellipsis">
              {guesthouse.name}
            </div>
            <RatedStar star={guesthouse.star} />
          </div>
          <div className="w-fit text-base">{`${guesthouse.price.toLocaleString()} KRW / 1박`}</div>
        </div>
      </button>
      <div className="flex px-1 py-1">
        {guesthouse.tags.map((el: string) => (
          <Tag key={el} name={el} />
        ))}
      </div>
    </div>
  );
}

export default GuesthouseCard;
