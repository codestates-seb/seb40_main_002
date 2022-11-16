import React, { useState } from 'react';
import DatePicker1 from './DatePicker';
import Keyword_Input from './Keyword_input';
import Location from './Location';

const Search = () => {
  const [keyword, setKeyword] = useState('');

  return (
    <>
      <div className="flex justify-between items-center text-base w-search h-search border-solid border-4 border-point rounded-search">
        <Location />
        <DatePicker1 />
        <Keyword_Input keyword={keyword} setkeyword={setKeyword} />
      </div>
    </>
  );
};

export default Search;
