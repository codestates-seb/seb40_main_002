import axios from 'axios';
import { Dispatch, SetStateAction } from 'react';
import CommonBtn from './CommonBtn/CommonBtn';

interface Props {
  nickname: string;
  isDup: boolean | string;
  setIsDup: Dispatch<SetStateAction<boolean | string>>;
}

export default function DuplicateBtn({ nickname, setIsDup, isDup }: Props) {
  const formData = new FormData();
  formData.append('memberNickname', nickname);

  // const stringDto = <T,>(formData: FormData, appendName: string, data: T) => {
  //   const stringDto = JSON.stringify(data);
  //   formData.append(
  //     appendName,
  //     new Blob([stringDto], { type: 'application/json' })
  //   );
  // };

  // stringDto(formData, 'memberNickname', nickname);
  const config = {
    method: 'get',
    url: '/api/members/checkname',
    params: {
      memberNickname: nickname,
    },
  };

  const duplicateCheck = () => {
    axios(config).then(function (res) {
      setIsDup(res.data.data);
    });
  };
  return (
    <div className="flex flex-col">
      <CommonBtn
        text="중복 검사"
        btnHandler={duplicateCheck}
        btnSize="w-20 h-8"
      />
    </div>
  );
}
