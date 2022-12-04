import { useEffect, Dispatch, SetStateAction } from 'react';

type Props = {
  funcProp: Dispatch<SetStateAction<string>>;
  funcProp2: Dispatch<SetStateAction<boolean | string>>;
  data: string;
  what: string;
};

const Input = ({ funcProp, data, what, funcProp2 }: Props) => {
  const inputHandler = (e: React.ChangeEvent<HTMLInputElement>) => {
    funcProp(e.target.value);
    if (what === 'nickname') {
      funcProp2('null');
    }
    if (what === 'phoneNum') {
      if (data.length === 3 || data.length === 8) {
        data = data.replace(/[^0-9]/g, '');
        data = data.replace(/(\d{3})(\d{4})(\d)/, '$1-$2-$3');
        e.target.value = data.slice(0, 13);
      }
    }
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
