import React, { Dispatch, SetStateAction } from 'react';

interface Props {
  setkeyword: Dispatch<SetStateAction<string>>;
  keyword: string;
}

const Keyword_Input = ({ keyword, setkeyword }: Props) => {
  return (
    <>
      <div className="mx-3">
        <input
          onChange={(e) => {
            setkeyword(e.target.value);
            console.log(keyword);
          }}
          placeholder="검색어를 입력해주세요"
        />
      </div>
    </>
  );
};

export default Keyword_Input;
