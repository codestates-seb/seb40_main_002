import RadioBtn from './RadioBtn';
import Input from '../common/Input';
import Birthday from './Birthday';
import { Dispatch, SetStateAction } from 'react';
import TagContainer from '../ghEdit/TagContainer';
import DuplicateBtn from '../common/DuplicateBtn';

interface Props {
  nickname: string;
  setNickname: Dispatch<SetStateAction<string>>;
  phoneNum: string;
  setPhoneNum: Dispatch<SetStateAction<string>>;
  setGuestHouseTag: Dispatch<SetStateAction<string[]>>;
  guestHouseTag: string[];
  form: { year: number; month: string; day: string };
  setForm: Dispatch<
    SetStateAction<{ year: number; month: string; day: string }>
  >;
  setIsHost: Dispatch<SetStateAction<string>>;
  setIsLocal: Dispatch<SetStateAction<string>>;
  isDup: boolean | string;
  setIsDup: Dispatch<SetStateAction<boolean | string>>;
}

const RightSide = ({
  nickname,
  setNickname,
  setPhoneNum,
  guestHouseTag,
  setGuestHouseTag,
  form,
  setForm,
  setIsHost,
  setIsLocal,
  isDup,
  setIsDup,
  phoneNum,
}: Props) => {
  return (
    <section className="flex flex-col justify-between h-[450px]">
      <div>
        <p className="text-lg"> 게스트하우스 이용객이신가요?</p>
        <RadioBtn comment="예" type="host" value="USER" funcProps={setIsHost} />
        <RadioBtn
          comment="아니요(게스트하우스 호스트입니다)"
          type="host"
          value="ADMIN"
          funcProps={setIsHost}
        />
      </div>
      <div>
        <p className="text-lg"> 닉네임을 입력해주세요.</p>
        <div className="flex">
          <div className="flex flex-col">
            <Input funcProp={setNickname} data={nickname} what={'nickname'} />
            {isDup === false && nickname.length > 2 ? (
              <span className="text-red-600"> 중복된 닉네임입니다.</span>
            ) : null}
            {isDup === false && nickname.length > 2 ? (
              <span className="text-blue-500"> 사용가능한 닉네임입니다.</span>
            ) : null}
          </div>
          <DuplicateBtn nickname={nickname} isDup={isDup} setIsDup={setIsDup} />
        </div>
      </div>
      <div>
        <p className="text-lg"> 생년월일을 입력해 주세요. </p>
        <Birthday form={form} setForm={setForm} />
      </div>
      <div>
        <p className="text-lg"> 외국인이신가요? </p>
        <RadioBtn
          comment="예"
          type="foreigner"
          value="FOREIGN"
          funcProps={setIsLocal}
        />
        <RadioBtn
          comment="아니요"
          type="foreigner"
          value="LOCAL"
          funcProps={setIsLocal}
        />
      </div>
      <div>
        <p className="text-lg"> 전화번호를 입력해 주세요.</p>
        <Input funcProp={setPhoneNum} data={phoneNum} what={'phoneNum'} />
      </div>
      <div>
        <p className="text-lg">
          선호하는 태그를 선택하고 회원가입을 완료하세요.
        </p>
        <div className="w-full">
          <TagContainer
            guestHouseTag={guestHouseTag}
            setGuestHouseTag={setGuestHouseTag}
          />
        </div>
      </div>
    </section>
  );
};

export default RightSide;
