export default function dateCheck(
  dates: (string | number)[]
): (number | string)[] {
  // 첫번째 날에는 박, 두번째 날에는 이용날짜가 들어갑니다.
  // 이미 정제된 데이터라면 원래 값을 내보낸다.
  if (typeof dates[0] === 'number') {
    return [...dates];
  }
  const date = dates.map((x) => new Date(x).getTime());
  // 몇박 로직
  const night = Math.floor(
    date.sort((a, b) => b - a).reduce((pre, cur) => pre - cur) /
      (1000 * 60 * 60 * 24)
  );
  const useDate = dates
    .map((x) => {
      const date = new Date(x);
      const year = date.getFullYear();
      const day = date.getDate();
      const month = date.getMonth() + 1;
      return `${year}년 ${month}월 ${day}일`;
    })
    .join(' ~ ');
  // (end.getTime() - start.getTime()) / (1000*60*60*24);

  return [night, useDate];
}
