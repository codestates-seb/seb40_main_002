import { Dispatch, SetStateAction } from 'react';

type Props = {
  what: string;
  setNickname: Dispatch<SetStateAction<string>>;
  setPhoneNum: Dispatch<SetStateAction<string>>;
};

const Input = ({ setNickname, setPhoneNum, what }: Props) => {
  const inputHandler = (e: React.ChangeEvent<HTMLInputElement>) => {
    if (what === 'nickname') {
      setNickname(e.target.value);
      console.log('닉네임');
    } else if (what === 'phoneNum') {
      setPhoneNum(e.target.value);
      console.log('전화번호');
    }
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
