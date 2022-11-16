import Heart from './Heart';
import RatedStar from './RatedStar';
import Tag from './Tag';

interface Props {
  imgSrc: string;
  name: string;
  price: number;
  star: number;
  tags: Array<string>;
  id: number;
}

function GuesthouseCard({ imgSrc, name, price, star, tags, id }: Props) {
  //   console.log(imgSrc);
  const handleToGuesthouse = () => {
    // 해당 게스트하우스 링크로 이동
    // navigate('~/id');
    console.log('gonna move to', id);
  };
  return (
    <div className="w-full h-full">
      <button onClick={handleToGuesthouse}>
        <div className="relative">
          <img
            src={imgSrc}
            className="aspect-[27/25] h-full rounded-[15px] object-cover"
          />
          <span className="absolute top-[12px] right-[12px]">
            <Heart id={id} />
          </span>
        </div>
        <div className="px-1">
          <div className="flex justify-between ">
            <div className="block w-[200px] whitespace-nowrap overflow-hidden text-lg font-bold text-start mr-[2px] my-[2px] text-ellipsis">
              {name}
            </div>
            <RatedStar star={star} />
          </div>
          <div className="w-fit text-base">{`${price.toLocaleString()} KRW / 1박`}</div>
        </div>
      </button>
      <div className="flex px-1 py-1">
        {tags.map((el) => (
          <Tag key={el} name={el} />
        ))}
      </div>
    </div>
  );
}

export default GuesthouseCard;
