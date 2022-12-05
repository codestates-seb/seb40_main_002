import axios from 'axios';

export const getHeartData = async (url: string) => {
  const accessToken = localStorage.getItem('accessToken');

  try {
    const response = await axios.get(`/api/auth/members/heart${url}`, {
      headers: {
        Authorization: accessToken,
      },
    });
    const data = await response.data;
    return data;
  } catch (err) {
    console.log('Error >>', err);
  }
};
