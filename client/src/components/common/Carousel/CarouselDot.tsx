import React from 'react';
type Dotprops = {
  current: number;
  images: string[];
};
const CarouselDot = ({ current, images }: Dotprops) => {
  return (
    <div className="absolute inset-x-0 bottom-2.5	 ">
      <div className="flex justify-center	gap-1.5 ">
        {images.map((x, i) => (
          <div
            key={i}
            className={` ${
              i === current
                ? ' w-2 h-2 rounded-full bg-white'
                : ' w-2 h-2 rounded-full bg-zinc-400'
            }`}
          ></div>
        ))}
      </div>
    </div>
  );
};
export default CarouselDot;
