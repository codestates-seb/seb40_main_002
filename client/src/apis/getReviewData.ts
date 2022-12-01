import axios from 'axios';

export const getReviewData = async (url: string) => {
  const accessToken = localStorage.getItem('accessToken');

  try {
    const response = await axios.get(`/api/auth/members/review${url}`, {
      headers: {
        Authorization: accessToken,
      },
    });

    const data = await response.data;
    console.log(data);

    return data;
  } catch (err) {
    console.log('Error >>', err);
  }
};
