import React from 'react';

const Search = () => {
  return (
    <>
      <div className="flex justify-center items-center text-2xl w-search h-search border-solid border-2 border-point rounded-search">
        <div className="mx-10"> 지역 </div>
        <div className="mx-10"> 날짜 </div>
        <div className="mx-10"> 키워드 </div>
      </div>
    </>
  );
};

export default Search;
