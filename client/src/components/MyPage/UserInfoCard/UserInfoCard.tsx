import React, { useState } from 'react';
import { MyPageUser } from '../../../types/user';
import UserInfo from './UserInfo';
import UserInfoEdit from './UserInfoEdit';

function UserInfoCard({
  user,
  setUser,
}: {
  user: MyPageUser;
  setUser: React.Dispatch<React.SetStateAction<MyPageUser>>;
}) {
  const [isEditing, setIsEditing] = useState(false);
  const handleEdit = () => {
    setIsEditing(!isEditing);
  };
  return (
    <div className="flex flex-col justify-between border border-border-color rounded-[15px] p-[20px] min-w-[240px] h-full">
      <div>
        <div className="flex justify-end">
          {isEditing || (
            <button onClick={handleEdit} className="text-font-color text-base">
              수정하기
            </button>
          )}
        </div>
        {isEditing ? (
          <UserInfoEdit user={user} handleEdit={handleEdit} setUser={setUser} />
        ) : (
          <UserInfo user={user} />
        )}
      </div>
      <div className="flex justify-center">
        <button className="text-font-color text-base">회원 탈퇴하기</button>
      </div>
    </div>
  );
}

export default UserInfoCard;
