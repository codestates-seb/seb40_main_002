import axios from 'axios';

export const getReviewData = async (ghId: string | undefined) => {
  try {
    const response = await axios.get(
      `http://3.37.58.81:8080/api/guesthouse/${ghId}/review?page=1&size=10`
    );
    return response.data.data;
  } catch (err) {
    console.log('Error >>', err);
  }
};
