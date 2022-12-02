import Api from '.';

export const getGhData = async (
  guestHouseId: string,
  start: string,
  end: string
) => {
  const res = await Api.get(
    `/api/guesthouse/${guestHouseId}?start=${start}&end=${end}`
  );
  return res.data.data;
};
