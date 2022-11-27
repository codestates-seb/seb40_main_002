import RadioBtn from './RadioBtn';
import Input from '../common/Input';
import Birthday from './Birthday';
import { Dispatch, SetStateAction } from 'react';
import TagContainer from '../ghEdit/TagContainer';

interface Props {
  setNickname: Dispatch<SetStateAction<string>>;
  setPhoneNum: Dispatch<SetStateAction<string>>;
  setGuestHouseTag: Dispatch<SetStateAction<string[]>>;
  guestHouseTag: string[];
  form: { year: number; month: string; day: string };
  setForm: Dispatch<
    SetStateAction<{ year: number; month: string; day: string }>
  >;
}

const RightSide = ({
  setNickname,
  setPhoneNum,
  guestHouseTag,
  setGuestHouseTag,
  form,
  setForm,
}: Props) => {
  return (
    <section className="flex flex-col justify-between h-[450px]">
      <div>
        <p className="text-lg"> 게스트하우스 이용객이신가요?</p>
        <RadioBtn comment="예" type="host" />
        <RadioBtn comment="아니요(게스트하우스 호스트입니다)" type="host" />
      </div>
      <div>
        <p className="text-lg"> 닉네임을 입력해주세요.</p>
        <Input funcProp={setNickname} what={'nickname'} />
      </div>
      <div>
        <p className="text-lg"> 생년월일을 입력해 주세요. </p>
        <Birthday form={form} setForm={setForm} />
      </div>
      <div>
        <p className="text-lg"> 외국인이신가요? </p>
        <RadioBtn comment="예" type="foreigner" />
        <RadioBtn comment="아니요" type="foreigner" />
      </div>
      <div>
        <p className="text-lg"> 전화번호를 입력해 주세요.</p>
        <Input funcProp={setPhoneNum} what={'phoneNum'} />
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
