import React from 'react';
import CarouselDot from './CarouselDot';
import arrowLeft from '../../../assets/img/arrowLeft.png';
import arrowRight from '../../../assets/img/arrowRight.png';
type CarouselBtnprops = {
  current: number;
  images: string[];
  btnHover: boolean;
  moveSlide(num: number): void;
};
const CarouselBtn = ({
  btnHover,
  current,
  moveSlide,
  images,
}: CarouselBtnprops) => {
  return (
    <div className={`${btnHover ? 'hover:block' : 'hidden'}`}>
      <button
        onClick={() => {
          moveSlide(-1);
        }}
        className={`absolute inset-y-0 left-0 cursor-pointer ${
          current === 0 ? 'hidden' : 'block'
        }`}
      >
        <img src={arrowLeft}></img>
      </button>
      <button
        onClick={() => {
          moveSlide(1);
        }}
        className={`absolute inset-y-0 right-0 cursor-pointer ${
          current === images.length - 1 ? 'hidden' : 'block'
        }`}
      >
        <img src={arrowRight}></img>
      </button>
      <CarouselDot current={current} images={images} />
    </div>
  );
};
export default CarouselBtn;
