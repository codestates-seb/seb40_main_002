import axios from 'axios';

export const getGhInfo = async (ghId: string | undefined) => {
  try {
    const response = await axios.get(
      `http://3.37.58.81:8080/api/guesthouse/${ghId}`
    );
    return response.data.data;
  } catch (err) {
    console.log('Error >>', err);
  }
};
