import axios from 'axios';

export const getGhInfo = async (ghId: string | undefined) => {
  try {
    const response = await axios.get(`/api/guesthouse/${ghId}`);
    return response.data.data;
  } catch (err) {
    console.log('Error >>', err);
  }
};
