import React, { useState } from 'react';
import Tag from '../Tag';

interface Props {
  tags: Array<string>;
  openModalHandler: () => void;
  setTags?: React.Dispatch<React.SetStateAction<string[]>>;
}
const checkMaxSelect = (selectedTags: Array<string>) => {
  if (selectedTags.length > 3) return false;
  else return true;
};

function TagSelect({ tags, openModalHandler, setTags }: Props) {
  const [selects, setSelects] = useState(new Array(tags.length).fill(false));
  const handleSelect = (idx: number) => {
    const newSelects = selects.slice();
    newSelects[idx] = !newSelects[idx];
    setSelects(newSelects);
  };
  const handleComplete = () => {
    // 선택한 tag들
    const selectedTags = tags.filter((_, idx) => selects[idx]);

    // 3개 이하인지 체크
    const check = checkMaxSelect(selectedTags);
    if (check) {
      // 상태로 저장하고
      setTags && setTags([...selectedTags]);
      openModalHandler();
    } else {
      alert('태그는 최대 3개까지 선택할 수 있습니다.');
    }
  };

  return (
    <div
      className="flex flex-col items-center p-[20px] w-[400px] rounded-[15px] bg-white"
      onClick={(e) => e.stopPropagation()}
    >
      <div className="text-lg font-medium w-fit mb-[20px]">
        선호하는 태그를 선택해 주세요. (최대 3개)
      </div>
      <div className="block mb-[20px] text-center">
        {tags.map((name: string, idx) => (
          <button key={name} onClick={() => handleSelect(idx)}>
            <Tag name={name} isSelected={selects[idx]} />
          </button>
        ))}
      </div>
      <button onClick={handleComplete}>선택 완료</button>
    </div>
  );
}

export default TagSelect;
