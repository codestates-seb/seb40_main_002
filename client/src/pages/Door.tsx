import * as Hangul from 'hangul-js';
import { useEffect, useState } from 'react';
const phrases = '제주의 특별한 손님이 되어 주세요.';

function Door() {
  const [nowSentence, setNowSentence] = useState('');
  const [nowLetters, setNowLetters] = useState<string[]>([]);
  const [nowIdx, setNowIdx] = useState(0);
  const [opacity, setOpacity] = useState('opacity-100');
  const [zIndex, setZIndex] = useState('z-[100]');
  const letters: Array<string> = Hangul.disassemble(phrases);

  const plusIdx = () => {
    setTimeout(() => setNowIdx(nowIdx + 1), 50);
  };

  useEffect(() => {
    if (letters[nowIdx] !== undefined) {
      setNowLetters([...nowLetters, letters[nowIdx]]);
      plusIdx();
    }
  }, [nowIdx]);

  useEffect(() => {
    setNowSentence(Hangul.assemble(nowLetters));
  }, [nowIdx]);

  useEffect(() => {
    setTimeout(() => {
      setOpacity('opacity-0');
    }, 2500);
    setTimeout(() => {
      setZIndex('z-[0]');
    }, 4000);
  }, []);

  return (
    <div
      className={`transition-all duration-[1500ms] ${opacity} ease-out fixed top-0 left-0 ${zIndex} flex justify-center items-center w-[100vw] h-[100vh] bg-no-repeat bg-center bg-cover bg-[url('https://cdn.pixabay.com/photo/2016/11/18/07/45/mark-1833559_1280.jpg')]`}
    >
      <div className="text-white text-xl ">{nowSentence}</div>
    </div>
  );
}

export default Door;
