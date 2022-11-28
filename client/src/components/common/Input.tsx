import { Dispatch, SetStateAction } from 'react';

type Props = {
  funcProp: Dispatch<SetStateAction<string>>;
  data: string;
  what: string;
};

const Input = ({ funcProp, data, what }: Props) => {
  const inputHandler = (e: React.ChangeEvent<HTMLInputElement>) => {
    funcProp(e.target.value);
  };
  return (
    <div className="flex flex-col">
      <input
        onChange={inputHandler}
        className="w-[245px] h-[30px] rounded-[3px] border-2 border-border-color focus:border-4  focus:border-point-color ease-in duration-150 outline-none px-2"
      />
      {data.length < 2 && what === 'nickname' ? (
        <span className="text-red-600">
          닉네임은 최소 2글자 이상 입력해야합니다.
        </span>
      ) : null}
    </div>
  );
};

export default Input;
