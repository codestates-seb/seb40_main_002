import React from 'react';
import CarouselDot from './CarouselDot';
import arrowLeft from '../../../assets/img/arrowLeft.png';
import arrowRight from '../../../assets/img/arrowRight.png';
import { AiOutlineRightCircle, AiOutlineLeftCircle } from 'react-icons/ai';

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
        className={`absolute inset-y-0 left-0 cursor-pointer ml-[12px] ${
          current === 0 ? 'hidden' : 'block'
        }`}
      >
        <AiOutlineLeftCircle color="white" size={'45px'} />
      </button>
      <button
        onClick={() => {
          moveSlide(1);
        }}
        className={`absolute inset-y-0 right-0 cursor-pointer mr-[12px] ${
          current === images.length - 1 ? 'hidden' : 'block'
        }`}
      >
        <AiOutlineRightCircle color="white" size={'45px'} />
      </button>
      <CarouselDot current={current} images={images} />
    </div>
  );
};
export default CarouselBtn;
