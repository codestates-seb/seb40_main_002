import axios from 'axios';

export const getGhDetailData = async (url: string) => {
  try {
    const response = await axios.get(
      `http://3.37.58.81:8080/api/guesthouse/${url}`
    );
    return response.data.data;
  } catch (err) {
    console.log('Error >>', err);
  }
};
