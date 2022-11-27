import axios from 'axios';
import { Dispatch, SetStateAction, useState } from 'react';
import CommonBtn from './CommonBtn/CommonBtn';

interface Props {
  nickname: string;
  isDup: boolean | string;
  setIsDup: Dispatch<SetStateAction<boolean | string>>;
}

export default function DuplicateBtn({ nickname, setIsDup, isDup }: Props) {
  const formData = new FormData();
  formData.append('memberNickname', nickname);

  const duplicateCheck = () => {
    // console.log(nickname);
    axios
      .post('http://3.57.58.81:8008/api/members/checkname', formData)
      .then((res) => {
        console.log(res);
        setIsDup(Boolean(res));
      });
    // console.log(isDup);
  };
  return (
    <div>
      <CommonBtn
        text="중복 확인"
        btnHandler={duplicateCheck}
        btnSize="w-20 h-8"
      />
      {isDup === false ? <span> 중복된 닉네임입니다.</span> : null}
    </div>
  );
}
