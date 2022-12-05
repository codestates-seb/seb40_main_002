import axios from 'axios';
import Api from '../api2';

export const getHeartData = async (url: string) => {
  const accessToken = localStorage.getItem('accessToken');

  try {
    const response = await Api.get(`/api/auth/members/heart${url}`, {
      headers: {
        // Authorization: accessToken,
      },
    });
    const data = await response.data;
    return data;
  } catch (err) {
    console.log('Error >>', err);
  }
};
