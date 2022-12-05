import axios from 'axios';
import Api from '../api2';

export const getUserInfo = async (token: string | null) => {
  try {
    const response = await Api.get(`/api/auth/members`, {
      headers: { Authorization: `${token}` },
    });
    return response.data.data;
  } catch (err) {
    console.log('Error >>', err);
  }
};
