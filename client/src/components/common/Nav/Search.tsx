import React, { useState } from 'react';
import DatePicker1 from './DatePicker';
import Keyword_Input from './Keyword_input';
import Location from './Location';
import { FaSearch } from 'react-icons/fa';

const Search = () => {
  const [keyword, setKeyword] = useState('');
  const search = () => {
    // 검색 api 들어갈 자리
  };

  return (
    <>
      <div className="flex justify-between px-3 items-center text-base w-[700px] font-semibold h-[50px] border-solid border-4 border-point-color rounded-[30px]">
        <Location />
        <DatePicker1 />
        <Keyword_Input keyword={keyword} setkeyword={setKeyword} />
        <FaSearch size={25} className="ml-10 mr-2" onClick={search} />
      </div>
    </>
  );
};

export default Search;
