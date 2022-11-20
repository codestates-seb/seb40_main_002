import React, { useState } from 'react';
import Tag from '../common/Tag';
import { TagSelectButton } from '../common/TagSelectModal/TagSelectButton';

export default function TagContainer() {
  const [tags, setTags] = useState<string[]>([]);

  return (
    <div className="mb-2.5 md:mb-5">
      <p className="font-bold text-lg mb-2.5">태그</p>
      <div className="mb-2.5 flex items-center">
        {tags.length > 0 && tags.map((x) => <Tag name={x} key={x} />)}
        <TagSelectButton setTags={setTags} />
      </div>
    </div>
  );
}
