import { ReactElement } from 'react';
import Checkbox from './Checkbox';

function Facilities({
  icon,
  name,
  isEditing = false,
}: {
  icon: ReactElement;
  name: string;
  isEditing?: boolean;
}) {
  return (
    <div className="flex items-center text-base text-font-color">
      <div className="mr-[2px]">{icon}</div>
      <div>{name}</div>
      {isEditing && <Checkbox />}
    </div>
  );
}

export default Facilities;
