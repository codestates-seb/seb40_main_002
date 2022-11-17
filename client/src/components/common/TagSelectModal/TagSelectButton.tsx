import { useState } from 'react';
import { AiOutlinePlus } from 'react-icons/ai';
import TagSelect from './TagSelect';
const testTags = [
  '오션뷰',
  '노을',
  '대리석',
  '숲',
  '태그1',
  '태그2',
  '태그3',
  '태그4',
  '태그5',
  '태그6',
  '태그7',
];

export const TagSelectButton = () => {
  const [isOpen, setIsOpen] = useState(false);

  const openModalHandler = () => {
    setIsOpen(!isOpen);
  };

  return (
    <div>
      <button
        onClick={openModalHandler}
        className="flex items-center text-base"
      >
        <AiOutlinePlus />
        태그 선택하기
      </button>
      {isOpen && (
        <div
          onClick={openModalHandler}
          className="flex justify-center items-center fixed top-0 left-0 right-0 bottom-0 bg-black/50 z-[100]"
        >
          <TagSelect tags={testTags} openModalHandler={openModalHandler} />
        </div>
      )}
    </div>
  );
};
