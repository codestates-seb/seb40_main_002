import React, { useEffect, useRef, useState } from 'react';
import { User1 } from '../../../types/user';
import Tag from '../../common/Tag';
import { TagSelectButton } from '../../common/TagSelectModal/TagSelectButton';
import { MdOutlineModeEditOutline } from 'react-icons/md';

import { imgDto, stringDto } from '../../../libs/ghEditCreateForm';
import Api from '../../../api';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';

function UserInfoEdit({
  handleEdit,
  user,
  setUser,
}: {
  handleEdit: () => void;
  user: User1;
  setUser: React.Dispatch<React.SetStateAction<User1>>;
}) {
  const navigate = useNavigate();
  const [nickname, setNickname] = useState(user.memberNickname);
  const [tags, setTags] = useState(user.memberTag);
  const [imgFile, setImgFile] = useState<File[]>(user.memberImageFile);

  const handleSubmit = async () => {
    const formData = new FormData();
    if (imgFile) {
      formData.append('memberImageFile', imgFile[0]);
    }

    const settingUser = {
      memberId: user.memberId,
      memberNickname: nickname,
      memberTag: tags,
    };

    try {
      stringDto(formData, 'member-dto', settingUser);
      const sendData = await axios.patch('/api/auth/members', formData, {
        headers: {
          'Content-Type': 'multipart/form-data',
          Authorization: `${localStorage.getItem('accessToken')}`,
        },
      });

      window.location.reload();
    } catch (e) {
      console.log(e);
    }
  };

  const imgRef = useRef<HTMLInputElement>(null);
  // 이미지 업로드 input의 onChange
  const saveImgFile = () => {
    if (imgRef.current && imgRef.current.files) {
      setImgFile([imgRef.current.files[0]]);
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
            src={URL.createObjectURL(imgFile[0])}
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
          {tags && tags.map((el) => <Tag key={el} name={el} />)}
        </div>
        <TagSelectButton setTags={setTags} />
      </div>
    </div>
  );
}

export default UserInfoEdit;
