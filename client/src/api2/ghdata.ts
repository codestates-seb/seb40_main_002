import axios from 'axios';
import Api from '.';

export const getGhData = async (
  guestHouseId: string,
  start: string,
  end: string
) => {
  const res = await axios.get(
    `/api/guesthouse/${guestHouseId}?start=${start}&end=${end}`
  );
  return res.data.data;
};
