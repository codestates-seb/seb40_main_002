import React from 'react';
// 프롭스 값을 전해주지 않으면 기본값으로 사용할 수 있습니다.
// 버튼의 text,width,height,font-size,bg-color,핸들러 함수를 프롭스로 내려주면 됩니다.
// width,height는 tailwind className값으로 전해주면 됩니다.
type BtnProps = {
  btnBg?: string;
  btnSize?: string;
  btnFs?: string;
  text: string;
  btnHandler(): void;
} & typeof defaultProps;
const defaultProps = {
  btnHandler: function asd() {
    console.log('hello');
  },
  btnSize: 'w-32 h-16',
  btnFs: 'text-base',
  text: 'button',
  btnBg: 'bg-point-color',
};

const CommonBtn = ({ btnHandler, btnSize, btnFs, text, btnBg }: BtnProps) => {
  return (
    <button
      onClick={btnHandler}
      className={`${btnSize} ${btnFs} ${btnBg} rounded text-white text-center
      delay-150 transition ease-in-out duration-500 hover:bg-pink-700 `}
    >
      {text}
    </button>
  );
};
CommonBtn.defaultProps = defaultProps;
export default CommonBtn;
