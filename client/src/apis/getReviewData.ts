import axios from 'axios';

export const getReviewData = async (ghId: string | undefined) => {
  try {
    const response = await axios.get(
      `/api/guesthouse/${ghId}/review?page=1&size=10`
    );
    return response.data.data;
  } catch (err) {
    console.log('Error >>', err);
  }
};
