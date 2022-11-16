import { useState } from 'react';
import { IoMdHeart, IoMdHeartEmpty } from 'react-icons/io';
interface Props {
  isFavorite?: boolean;
  id: number;
}

function Heart({ isFavorite = false, id }: Props) {
  const [isRed, setIsRed] = useState(isFavorite);
  const handleFavorite = () => {
    setIsRed(!isRed);
    // 서버에 전송
    console.log(id);
  };
  return (
    <div>
      <button onClick={handleFavorite}>
        {isRed ? (
          <IoMdHeart color="red" size={'25px'} />
        ) : (
          <IoMdHeartEmpty color="#717171" size={'25px'} />
        )}
      </button>
    </div>
  );
}

export default Heart;
