import { Dispatch, SetStateAction } from 'react';

type Props = {
  what: string;
  funcProp: Dispatch<SetStateAction<string>>;
};

const Input = ({ funcProp }: Props) => {
  const inputHandler = (e: React.ChangeEvent<HTMLInputElement>) => {
    funcProp(e.target.value);
  };
  return (
    <>
      <input
        onChange={inputHandler}
        className="w-[245px] h-[30px] rounded-[3px] border-2 border-border-color focus:border-4  focus:border-point-color ease-in duration-150 outline-none px-2"
      />
    </>
  );
};

export default Input;
