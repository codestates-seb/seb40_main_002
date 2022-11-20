import React, { useState } from 'react';

export default function Ghname() {
  const [title, setTitle] = useState('');
  return (
    <div className="flex">
      <p className="font-bold text-lg mb-2.5">숙소 명</p>
      <input
        type="text"
        // ref={ref}
        className="border pl-2.5 ml-[15px] border-border-color mb-3 md:mb-5 rounded-btnRadius w-1/2 focus:border-border-color focus : border focus:outline-none"
        onChange={(e) => setTitle(e.target.value)}
        value={title}
        placeholder="숙소명을 입력해주세요"
      />
    </div>
  );
}
