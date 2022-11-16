import { AiFillStar } from 'react-icons/ai';
interface Props {
  star: number;
}

function RatedStar({ star }: Props) {
  return (
    <div className="flex items-center">
      <AiFillStar size={'15px'} />
      <div className="text-base">{star.toFixed(1)}</div>
    </div>
  );
}

export default RatedStar;
