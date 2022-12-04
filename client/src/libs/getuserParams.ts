import { convertURLtoFile } from './srcToFile';

const socialTypeCheck = (memberId: string) => {
  const socials = ['kakao', 'naver', 'google'];
  const socialsType = socials
    .filter((x) => memberId.includes(x))[0]
    .toUpperCase();
  return socialsType;
};

export function getuserParams(url: URL, paramsname: string[]) {
  const getParams = paramsname.map((x) => url.searchParams.get(x)) as string[];
  const [memberId, memberEmail, memberImgurl] = getParams;

  const memberRegisterKind = socialTypeCheck(memberId);

  return {
    memberId,
    memberEmail,
    memberImgurl,
    memberRegisterKind,
  };
}

export function getPaymentParams(url: URL, paramsname: string[]) {
  const getParams = paramsname.map((x) => url.searchParams.get(x)) as string[];

  return getParams;
}
// const imgUrl = memberImgurl.split('/').slice(3).join('/');
// const memberImage = await convertURLtoFile(imgUrl);
// memberImage: [memberImage],
