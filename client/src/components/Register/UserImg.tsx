import { Dispatch, SetStateAction } from 'react';

interface Props {
  userImg: string;
  setUserImg: Dispatch<SetStateAction<string>>;
}

const UserImg = ({ userImg, setUserImg }: Props) => {
  return (
    <div className="flex flex-col justify-center items-center">
      <img
        className="w-[242px] h-[242px] rounded-full hover:opacity-80 transition-all duration-150 mb-[30px]"
        alt="userPic"
        src={userImg}
      />
      <input type={'file'} accept="image/*" />
    </div>
  );
};

export default UserImg;
