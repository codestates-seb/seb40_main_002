import { Dispatch, SetStateAction, useRef } from 'react';

interface Props {
  userImg: string;
  setUserImg: Dispatch<SetStateAction<string | File[]>>;
}

const UserImg = ({ userImg, setUserImg }: Props) => {
  const imgRef = useRef<HTMLInputElement>(null);
  const imgRefClick = () => {
    if (imgRef.current) {
      imgRef.current.click();
    }
  };

  const selectImg = (e: React.ChangeEvent<HTMLInputElement>) => {
    const selectedfiles = e.target.files;

    if (selectedfiles) {
      const uploadfiles = selectedfiles[0];
      console.log(uploadfiles);

      const reader = new FileReader();
      reader.readAsDataURL(uploadfiles);
      reader.onload = () => {
        const fileURL = reader.result as string;
        setUserImg(fileURL);
      };
    }
  };

  return (
    <div className="flex flex-col justify-center items-center">
      <img
        className="w-[242px] h-[242px] rounded-full hover:opacity-80 transition-all duration-150 mb-[30px]"
        alt="userPic"
        src={userImg}
        onClick={imgRefClick}
      />
      <input
        type={'file'}
        accept="image/*"
        className="hidden"
        ref={imgRef}
        onChange={selectImg}
      />
    </div>
  );
};

export default UserImg;
