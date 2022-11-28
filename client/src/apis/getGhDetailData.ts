import axios from 'axios';

export const getGhDetailData = async (url: string) => {
  try {
    const response = await axios.get(`/api/guesthouse/${url}`);
    return response.data.data;
  } catch (err) {
    console.log('Error >>', err);
  }
};
