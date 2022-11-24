import React, { useEffect, useState } from 'react';
import CarouselBtn from './CarouselBtn';
//carouselSize 값으로 width,height값을 문자열로 넣어 주시면 됩니다(e.g {w-[250px] h-[250px]}),images 값은 이미지 배열을 프롭스로 내려주면 됩니다.
type CarouselProps = {
  images: string[];
};
const Carousel = ({ images }: CarouselProps) => {
  const [btnHover, setBtnHover] = useState<boolean>(false); //Carousel hover  state
  const [current, setCurrent] = useState<number>(0);
  const [style, setStyle] = useState<{ marginLeft: string }>({
    marginLeft: `-${current}00%`,
  });
  const moveSlide = (i: number) => {
    const nextIndex = current + i;
    if (nextIndex < 0) {
      return;
    } else if (nextIndex > images.length - 1) return;

    setCurrent(nextIndex);
  };
  // 버튼을 누르면 moveSlide 함수가 실행->  current 값 변경-> marginLeft {current}00% 만큼 이동
  useEffect(() => {
    setStyle({ marginLeft: `-${current}00%` });
  }, [current]);

  return (
    <div
      className={`relative w-full`}
      onMouseEnter={() => {
        setBtnHover(true);
      }}
      onMouseLeave={() => {
        setBtnHover(false);
      }}
    >
      <div className={`overflow-hidden w-full `}>
        <CarouselBtn
          btnHover={btnHover}
          current={current}
          moveSlide={moveSlide}
          images={images}
        />
        <div
          className={`flex transition-all duration-700	ease-out w-full `}
          style={style}
        >
          {images.map((img, i) => (
            <img
              key={i}
              className={`bg-center bg-no-repeat bg-cotain flex-none	rounded-xl  w-full h-[560px]`}
              src={`${img}`}
            ></img>
          ))}
        </div>
      </div>
    </div>
  );
};

export default Carousel;
