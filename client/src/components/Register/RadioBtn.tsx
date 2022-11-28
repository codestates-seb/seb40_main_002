import { Dispatch, SetStateAction } from 'react';

interface Props {
  comment: string;
  type: string;
  value: string;
  funcProps: Dispatch<SetStateAction<string>>;
}

const RadioBtn = ({ comment, type, value, funcProps }: Props) => {
  return (
    <span className="mr-[60px]">
      <span> {comment} </span>
      <input
        type={'radio'}
        name={type}
        value={value}
        className="appearance-none rounded-[50%] w-[13px] h-[13px] align-baseline border-2 border-#D9D9D9 ease-in duration-150 checked:bg-point-color checked:border-point-color"
        onChange={(e) => {
          funcProps(e.target.value);
        }}
      />
    </span>
  );
};

export default RadioBtn;
