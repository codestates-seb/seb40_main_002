export function getuserParams(url: URL, paramsname: string[]) {
  const getParams = paramsname.map((x) => url.searchParams.get(x));
  const data = {
    memberId: getParams[0],
    memberEmail: getParams[1],
    memberImgUrl: getParams[2],
  };
  return data;
}
