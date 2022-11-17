import { ReactElement, useState } from 'react';
import Checkbox from './Checkbox';

function Facilities({
  icon,
  name,
  isEditing = false,
  isExist = false,
}: {
  icon: ReactElement;
  name: string;
  isEditing?: boolean;
  isExist?: boolean;
}) {
  const [isChecked, setIsChecked] = useState(isExist);
  // console.log(isChecked);
  return (
    <div className="flex items-center text-base text-font-color">
      <div className="mr-[2px]">{icon}</div>
      <div>{name}</div>
      {isEditing && (
        <Checkbox isChecked={isChecked} setIsChecked={setIsChecked} />
      )}
    </div>
  );
}

export default Facilities;
