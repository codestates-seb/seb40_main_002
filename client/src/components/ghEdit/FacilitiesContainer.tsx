import React, { useEffect, useState } from 'react';
import Facilities from '../common/Facilities';
import FacilitiesArr from '../common/FacilitiesArray';

type ghOption = {
  icon: JSX.Element;
  name: string;
  checked: boolean;
};

export default function FacilitiesContainer() {
  // 데이터 상위로 올릴것
  const [icons, setIcons] = useState(FacilitiesArr());

  const selectIcon = (name: string) => {
    setIcons((prev) => {
      const icons = prev.map((facilities) => {
        if (facilities.name === name) {
          return { ...facilities, checked: !facilities.checked };
        } else {
          return { ...facilities };
        }
      });

      return [...icons];
    });
  };

  // 옵션을 boolean 값으로만 보내줘야함. post 로직에 들어가면 될듯
  const ghOptionsArr = (options: ghOption[]) => {
    const optionsBoolean = options.map((x) => x.checked);
    // setOptionsBoolean([...optionsBoolean]);
  };

  // // 상세 페이지에서의 로직
  // const [options, setOptions] = useState(FacilitiesArr());
  // // api 요청을 통해 optionsBoolean을 채우게 된다면?
  // const [optionsBoolean, setOptionsBoolean] = useState<boolean[]>([]);

  // useEffect(() => {
  //   if (optionsBoolean.length !== 0) {
  //     // api 요청을 통해 받은 options 배열을 아이콘들의 checked에 적용시킴
  //     setOptions((prev) => {
  //       const checkOptions = prev.map((x, idx) => {
  //         return { ...x, checked: optionsBoolean[idx] };
  //       });
  //       console.log(checkOptions);
  //       return [...checkOptions];
  //     });
  //   }
  // }, [optionsBoolean]);

  return (
    <div className="flex flex-col">
      <p className="font-bold text-lg md:text-lg mb-2.5">숙소 편의시설 등록</p>
      <div className="flex flex-col w-full justify-around items-center flex-wrap p-2.5 md:flex-row ">
        {icons.map((x) => (
          <div key={x.name} className="w-3/4 md:w-1/4 mr-2.5 mb-2.5">
            <Facilities
              icon={x.icon}
              name={x.name}
              isEditing={true}
              isExist={x.checked}
              selectIcon={selectIcon}
            />
          </div>
        ))}
      </div>
      {/* 상세 페이지 로직 */}
      {/* <div className="flex flex-col w-full justify-around items-center flex-wrap p-2.5 md:flex-row ">
        {options.map((x) => {
          if (x.checked) {
            return (
              <div key={x.name} className="w-3/4 md:w-1/4 mr-2.5 mb-2.5">
                <Facilities
                  icon={x.icon}
                  name={x.name}
                  isEditing={false}
                  isExist={x.checked}
                  selectIcon={selectIcon}
                />
              </div>
            );
          } else {
            return;
          }
        })}
      </div> */}
      <button onClick={() => ghOptionsArr(icons)}>ㅁㄴㅇㄹ</button>
    </div>
  );
}
