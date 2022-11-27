import React, { useRef, useState } from 'react';
import { MyPageUser } from '../../../types/user';
import Tag from '../../common/Tag';
import { TagSelectButton } from '../../common/TagSelectModal/TagSelectButton';
import { MdOutlineModeEditOutline } from 'react-icons/md';

function UserInfoEdit({
  handleEdit,
  user,
  setUser,
}: {
  handleEdit: () => void;
  user: MyPageUser;
  setUser: React.Dispatch<React.SetStateAction<MyPageUser>>;
}) {
  const [nickname, setNickname] = useState(user.memberNickname);
  const [tags, setTags] = useState(user.memberTag);
  const handleSubmit = () => {
    const newUser: MyPageUser = {
      memberId: user.memberId,
      memberNickname: nickname,
      memberEmail: user.memberEmail,
      memberPhone: user.memberPhone,
      memberImageUrl: user.memberImageUrl,
      memberTag: tags,
      memberReservation: user.memberReservation,
      memberReview: user.memberReview,
      memberComunity: user.memberComunity,
    };
    setUser(newUser);
    handleEdit();
  };

  const [imgFile, setImgFile] = useState<string>('');
  const imgRef = useRef<HTMLInputElement>(null);
  // 이미지 업로드 input의 onChange
  const saveImgFile = () => {
    if (imgRef.current && imgRef.current.files) {
      const file = imgRef.current.files[0];
      const reader = new FileReader();
      reader.readAsDataURL(file);
      reader.onloadend = () => {
        if (typeof reader.result === 'string') setImgFile(reader.result);
      };
    }
  };
  return (
    <div>
      <div className="flex justify-end">
        <button onClick={handleSubmit} className="text-font-color text-base">
          수정 완료
        </button>
      </div>
      <div className="flex flex-col items-center justify-center">
        <div className="flex flex-col items-center justify-center p-[20px] relative">
          <img
            src={imgFile ? imgFile : user.memberImageUrl}
            className="w-[120px] h-[120px] object-cover rounded-full"
          />
          <button
            className="w-fit m-auto absolute bottom-[24px] right-[24px]"
            onClick={() => {
              imgRef.current?.click();
            }}
          >
            <MdOutlineModeEditOutline className="text-lg text-font-color border border-solid rounded-full border-font-color w-[28px] h-[28px] p-[2px] bg-white" />
          </button>
          <input
            type="file"
            accept="image/*"
            ref={imgRef}
            onChange={saveImgFile}
            className="hidden"
          />
        </div>
        <div>
          <input
            className="w-[100px] border border-border-color rounded-[3px] text-base px-[4px] mx-[4px]"
            value={nickname}
            onChange={(e) => setNickname(e.target.value)}
          />
          <span className="text-base">님</span>
        </div>
        <div className="text-base text-font-color my-[8px]">
          선호하는 숙소 스타일
        </div>
        <div className="flex mb-[12px]">
          {tags.map((el) => (
            <Tag key={el} name={el} />
          ))}
        </div>
        <TagSelectButton setTags={setTags} />
      </div>
    </div>
  );
}

export default UserInfoEdit;
