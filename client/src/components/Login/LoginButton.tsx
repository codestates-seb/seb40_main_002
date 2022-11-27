import axios from 'axios';
import { ReactElement } from 'react';
import { FcGoogle } from 'react-icons/fc';
import { RiKakaoTalkFill } from 'react-icons/ri';
import { SiNaver } from 'react-icons/si';

type SocialView = {
  color: string;
  korean: string;
  icon: ReactElement;
};

type Social = {
  naver: SocialView;
  kakao: SocialView;
  google: SocialView;
};

const social: Social = {
  naver: {
    color: 'bg-[#17ce5f]',
    korean: '네이버',
    icon: <SiNaver />,
  },
  kakao: {
    color: 'bg-[#fada0b]',
    korean: '카카오',
    icon: <RiKakaoTalkFill />,
  },
  google: {
    color: 'bg-[#ffffff]',
    korean: '구글',
    icon: <FcGoogle />,
  },
};

function LoginButton({ socialType }: { socialType: keyof Social }) {
  const nowSocial = social[socialType];
  const handleLogin = async () => {
    return window.location.assign(
      `http://3.37.58.81:8080/oauth2/authorization/${socialType}`
    );
  };
  return (
    <button
      onClick={handleLogin}
      className={`${nowSocial['color']} relative flex items-center justify-center rounded-[3px] border-border-color border-[1px] w-full px-[12px] py-[8px] text-base m-[4px]`}
      // href={`http://3.37.58.81:8080/oauth2/authorization/${socialType}`}
    >
      <div className="absolute left-[12px]">{nowSocial['icon']}</div>
      <div>{`${nowSocial['korean']}로 로그인하기`}</div>
    </button>
  );
}

export default LoginButton;
