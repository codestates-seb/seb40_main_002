import React, { useEffect, useState } from 'react';
import CarouselBtn from './CarouselBtn';
//carouselSize 값으로 width,height값을 문자열로 넣어 주시면 됩니다(e.g {w-[250px] h-[250px]}),images 값은 이미지 배열을 프롭스로 내려주면 됩니다.
type CarouselProps = {
  carouselSize?: string;
  images: string[];
} & typeof defaultProps;
const defaultProps = {
  carouselSize: 'w-[300px] h-[300px]',
  images: [
    'https://a0.muscache.com/im/pictures/337660c5-939a-439b-976f-19219dbc80c7.jpg?im_w=720',
    'https://a0.muscache.com/im/pictures/4f70b681-a792-4530-8c52-f2a8d262942d.jpg?im_w=720',
    'https://a0.muscache.com/im/pictures/d7c1f140-c33a-4d68-aaf8-b7b8d7292b11.jpg?im_w=720',
  ],
};
const Carousel = ({ carouselSize, images }: CarouselProps) => {
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
      className={`relative ${carouselSize}`}
      onMouseEnter={() => {
        setBtnHover(true);
      }}
      onMouseLeave={() => {
        setBtnHover(false);
      }}
    >
      <div className={`overflow-hidden w-full h-full`}>
        <CarouselBtn
          btnHover={btnHover}
          current={current}
          moveSlide={moveSlide}
          images={images}
        />
        <div
          className={`flex transition-all duration-700	ease-out w-full h-full`}
          style={style}
        >
          {images.map((img, i) => (
            <img
              key={i}
              className={`bg-center bg-no-repeat bg-cover flex-none	rounded-xl w-full h-full`}
              src={`${img}`}
            ></img>
          ))}
        </div>
      </div>
    </div>
  );
};
Carousel.defaultProps = defaultProps;
export default Carousel;
