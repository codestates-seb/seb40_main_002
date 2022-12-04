import { ReactElement, useState } from 'react';
import Checkbox from './Checkbox';

function Facilities({
  icon,
  name,
  isEditing = false,
  isExist = false,
  selectIcon,
}: {
  icon: ReactElement;
  name: string;
  isEditing?: boolean;
  isExist?: boolean;
  selectIcon?: (e: string) => void;
}) {
  const [isChecked, setIsChecked] = useState(isExist);
  // console.log(isChecked);
  return (
    <div className="flex items-center text-lg text-font-color">
      <div className="mr-[10px]">{icon}</div>
      <div>{name}</div>
      {isEditing && (
        <Checkbox
          isChecked={isChecked}
          setIsChecked={setIsChecked}
          selectIcon={selectIcon}
          name={name}
        />
      )}
    </div>
  );
}

export default Facilities;
