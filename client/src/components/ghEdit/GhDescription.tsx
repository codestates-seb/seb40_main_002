import React, { useState } from 'react';

export default function GhDescription() {
  // 로직 위로 올리기
  const [description, setDescription] = useState('');
  return (
    <div>
      <p className="font-bold text-lg mb-2.5">숙소 설명</p>
      <textarea
        // ref={ref}
        className="border mr-[15px] h-20 border-border-color mb-3 md:mb-5 rounded-btnRadius w-full h- resize-none pl-[5px] focus:border-border-color focus : border focus:outline-none"
        onChange={(e) => setDescription(e.target.value)}
        value={description}
      />
    </div>
  );
}
