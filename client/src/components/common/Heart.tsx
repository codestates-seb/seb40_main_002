import axios from 'axios';
import { useCallback, useEffect, useState } from 'react';
import { IoMdHeart, IoMdHeartEmpty } from 'react-icons/io';
import Api from '../../api2';
import { isLogin } from '../../utils/isLogin';
interface Props {
  isFavorite?: boolean;
  id: number;
}

function Heart({ isFavorite = false, id }: Props) {
  const [isRed, setIsRed] = useState(isFavorite);
  const getHeart = useCallback(async () => {
    await axios
      .get(`/api/auth/guesthouse/${id}/heart`, {
        headers: {
          Authorization: localStorage.getItem('accessToken'),
        },
      })
      .then((res) => {
        // console.log(res);
        setIsRed(res.data.data);
      })
      .catch((err) => console.log(err));
  }, [id]);
  useEffect(() => {
    if (isLogin()) getHeart();
  }, []);
  const handleFavorite = () => {
    if (isLogin()) {
      // 서버에 전송
      // console.log(id);
      axios
        .post(
          `/api/auth/guesthouse/${id}/heart`,
          {},
          {
            headers: {
              Authorization: localStorage.getItem('accessToken'), // interceptors 적용 되는지 테스트
              'Content-Type': '',
            },
          }
        )
        .then((res) => {
          console.log('heartRes:', res);
          setIsRed(!isRed);
        })
        .catch((err) => console.log('heartErr:', err));
    } else {
      window.alert('로그인 후 이용하세요.');
    }
  };
  return (
    <div onClick={(e) => e.stopPropagation()}>
      <div className="cursor-pointer" onClick={handleFavorite}>
        {isRed ? (
          <IoMdHeart color="red" size={'25px'} />
        ) : (
          <IoMdHeartEmpty color="#717171" size={'25px'} />
        )}
      </div>
    </div>
  );
}

export default Heart;
