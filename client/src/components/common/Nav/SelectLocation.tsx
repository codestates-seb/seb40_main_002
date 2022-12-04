import React, { Dispatch, SetStateAction } from 'react';
import Tag from '../../common/Tag';
import CommonBtn from '../CommonBtn/CommonBtn';

interface Props {
  locations: Array<string>;
  selects: Array<boolean>;
  setSelects: Dispatch<SetStateAction<Array<boolean>>>;
  modalHandler: () => void;
  onlyTrue: Array<boolean>;
}

const SelectLocation = ({
  locations,
  selects,
  setSelects,
  modalHandler,
  onlyTrue,
}: Props) => {
  const handleSelect = (idx: number) => {
    const newSelects = selects.slice();
    if (onlyTrue.length > 0) {
      if (newSelects[idx]) {
        newSelects[idx] = !newSelects[idx];
        setSelects(newSelects);
      } else {
        alert('1개만 선택 가능합니다.');
      }
    } else {
      newSelects[idx] = !newSelects[idx];
      setSelects(newSelects);
    }
    // console.log(onlyTrue);
  };

  const handleComplete = () => {
    modalHandler();
  };

  return (
    <>
      <div className="flex flex-wrap justify-center items-center w-[340px] h-fit border-solid border-[1px] border-border-color rounded-2xl py-4 absolute top-20 bg-white">
        <div className="text-lg font-medium w-fit mb-[20px]">
          장소를 선택해 주세요.
        </div>
        <div className="flex flex-wrap justify-center items-center mb-4">
          {locations.map((name: string, idx) => (
            <button key={name} onClick={() => handleSelect(idx)}>
              <Tag name={name} isSelected={selects[idx]} />
            </button>
          ))}
        </div>
        {/* <button
          onClick={handleComplete}
          className="hover:bg-point-color hover:text-white w-32 rounded-[15px]"
        >
          선택 완료
        </button> */}
        <CommonBtn
          btnHandler={handleComplete}
          text={'선택 완료'}
          btnSize={'w-20 h-8'}
        />
      </div>
    </>
  );
};

export default SelectLocation;
