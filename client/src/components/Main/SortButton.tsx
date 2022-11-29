import React from 'react';

interface Props {
  sortType: string;
  setSortType: React.Dispatch<React.SetStateAction<string>>;
}

// 일단 이렇게 해놨는데 백에서 주는 api 명세 보고 key 이름 바꿀 것

type SortTypeKR = {
  default: string;
  star: string;
  review: string;
};

const sortTypeKR: SortTypeKR = {
  default: '기본',
  star: '별점 순',
  review: '리뷰 순',
};

function SortButton({ sortType, setSortType }: Props) {
  const handleSort = () => {
    setSortType(sortType);
  };
  return (
    <span>
      {sortType && (
        <button
          onClick={handleSort}
          className="ml-[8px] text-base text-font-color"
        >
          {sortTypeKR[sortType as keyof SortTypeKR]}
        </button>
      )}
    </span>
  );
}

export default SortButton;
