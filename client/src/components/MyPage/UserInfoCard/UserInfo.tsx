import { User1 } from '../../../types/user';
import Tag from '../../common/Tag';

function UserInfo({ user }: { user: User1 }) {
  return (
    <div className="flex flex-col items-center">
      <div className="p-[20px]">
        <img
          src={URL.createObjectURL(user.memberImageFile[0])}
          className="w-[120px] h-[120px] object-cover rounded-full"
        />
      </div>
      <div className="text-base">{`${user.memberNickname} 님`}</div>
      <div className="text-base text-font-color my-[8px]">
        선호하는 숙소 스타일
      </div>
      <div className="flex">
        {user.memberTag.map((el) => (
          <Tag key={el} name={el} />
        ))}
      </div>
    </div>
  );
}

export default UserInfo;
