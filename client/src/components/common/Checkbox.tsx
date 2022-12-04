import { ReactElement } from 'react';

function Checkbox({
  isChecked,
  setIsChecked,
  name,
  setRadio,
  selectIcon,
}: {
  isChecked: boolean;
  setIsChecked?: React.Dispatch<React.SetStateAction<boolean>>;
  name?: string;
  setRadio?: React.Dispatch<React.SetStateAction<string>>;
  selectIcon?: (e: string) => void;
}) {
  return (
    <input
      type="checkbox"
      className="checkbox checkbox-sm rounded-full checkbox ml-auto mr-[4px] checkbox-primary"
      checked={isChecked}
      onChange={(e) => {
        if (setIsChecked) {
          setIsChecked(e.target.checked);
        }
        if (setRadio) {
          setRadio(e.target.name);
        }
        if (selectIcon) {
          selectIcon(e.target.name);
        }
      }}
      name={name}
    />
  );
}

export default Checkbox;
